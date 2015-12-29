package sgf.gateway.web.filters.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.safehaus.uuid.UUID;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.web.HttpHeaderConstants;
import sgf.gateway.web.HttpSessionConstants;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * The Class UnhandledExceptionFilter.
 */
public class UnhandledExceptionFilter implements Filter {

    /**
     * The Constant LOG.
     */
    private static final Log LOG = LogFactory.getLog(UnhandledExceptionFilter.class);

    /**
     * The error response.
     */
    private final ErrorResponse errorResponse;

    /**
     * The exception handling service.
     */
    private final ExceptionHandlingService exceptionHandlingService;

    /**
     * Instantiates a new unhandled exception filter.
     *
     * @param errorResponse            the error response
     * @param exceptionHandlingService the exception handling service
     */
    public UnhandledExceptionFilter(ErrorResponse errorResponse, ExceptionHandlingService exceptionHandlingService) {

        super();

        this.errorResponse = errorResponse;
        this.exceptionHandlingService = exceptionHandlingService;
    }

    /**
     * {@inheritDoc}
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        try {

            filterChain.doFilter(request, response);

        } catch (Throwable exception) {

            this.handleException(request, response, exception);
        }
    }

    /**
     * Handle exception.
     *
     * @param request   the request
     * @param response  the response
     * @param throwable the throwable
     */
    protected void handleException(ServletRequest request, ServletResponse response, Throwable throwable) {

        try {

            this.doHandleException(request, response, throwable);

        } catch (Exception handlingException) {

            LOG.error("This exception that was not handled by the UnhandledExceptionFilter", throwable);
            LOG.error("This exception occurred reporting an unhandled exception, please see the 'cause by' exception above", handlingException);
        }
    }

    /**
     * Do handle exception.
     *
     * @param request   the request
     * @param response  the response
     * @param throwable the throwable
     * @throws Exception the exception
     */
    protected void doHandleException(ServletRequest request, ServletResponse response, Throwable throwable) throws Exception {

        this.errorResponse.send(request, response);

        this.reportException(request, response, throwable);

    }

    /**
     * Report exception.
     *
     * @param request   the request
     * @param response  the response
     * @param throwable the throwable
     */
    protected void reportException(ServletRequest request, ServletResponse response, Throwable throwable) {

        UnhandledException unhandledException = this.setupExceptionDetails((HttpServletRequest) request, throwable);

        this.exceptionHandlingService.handleUnexpectedException(unhandledException);
    }

    /**
     * Setup exception details.
     *
     * @param request   the request
     * @param throwable the throwable
     * @return the unhandled exception
     */
    protected UnhandledException setupExceptionDetails(HttpServletRequest request, Throwable throwable) {

        UnhandledException unhandledException = new UnhandledException(throwable);

        String eskeSessionId = this.getESKESessionId(request);
        unhandledException.put("Session Id", eskeSessionId);
        unhandledException.put("Remote Address", request.getRemoteAddr());
        unhandledException.put("User Agent", request.getHeader(HttpHeaderConstants.USER_AGENT));
        unhandledException.put("Server Name", request.getServerName());
        unhandledException.put("Server Port", "" + request.getServerPort());
        unhandledException.put("Method", request.getMethod());
        unhandledException.put("URL", request.getRequestURI());
        unhandledException.put("Referer", request.getHeader(HttpHeaderConstants.REFERRER));

        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length != 0) {

            for (Cookie cookie : cookies) {

                unhandledException.put(cookie.getName(), cookie.getValue());
            }
        }

        unhandledException.put("Query String", request.getQueryString());

        Enumeration parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {

            String parameterName = (String) parameterNames.nextElement();

            String parameterValue = request.getParameter(parameterName);

            if (parameterName.equals("j_password") || parameterName.equals("password") || parameterName.equals("confirmationPassword") || parameterName.equals("oldPassword") || parameterName.equals("confirmNewPassword")) {

                parameterValue = "********";
            }

            unhandledException.put(parameterName, "'" + parameterValue + "'");
        }

        return unhandledException;
    }

    protected String getESKESessionId(HttpServletRequest request) {

        HttpSession httpSession = request.getSession();

        UUID eskeSessionId = (UUID) httpSession.getAttribute(HttpSessionConstants.ESKE_SESSIONID);

        return eskeSessionId.toString();
    }

    /**
     * {@inheritDoc}
     */
    public void destroy() {
        // Nothing to do
    }

    /**
     * {@inheritDoc}
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing to do.
    }

}
