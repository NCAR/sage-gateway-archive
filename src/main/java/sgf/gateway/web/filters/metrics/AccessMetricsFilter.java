package sgf.gateway.web.filters.metrics;

import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.web.HttpSessionConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessMetricsFilter implements Filter {

    private final RuntimeUserService runtimeUserService;

    public AccessMetricsFilter(RuntimeUserService runtimeUserService) {
        super();
        this.runtimeUserService = runtimeUserService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        this.addAccessAttributesToRequest((HttpServletRequest) request);

        chain.doFilter(request, response);
    }

    private User getUser() {
        User user = this.runtimeUserService.getUser();
        return user;
    }

    private void addAccessAttributesToRequest(HttpServletRequest request) {
        this.addUserBasedAttributesToRequest(request);
        this.addSessionIdAttributeToRequest(request);
    }

    private void addUserBasedAttributesToRequest(HttpServletRequest request) {

        User user = this.getUser();

        String username = null;
        String openid = null;
        String userid = null;

        if (user != null) {
            username = user.getUserName();
            openid = user.getOpenid();
            userid = user.getIdentifier().toString();
        }

        request.setAttribute("access_metrics_username", username);
        request.setAttribute("access_metrics_openid", openid);
        request.setAttribute("access_metrics_userid", userid);
    }

    private void addSessionIdAttributeToRequest(HttpServletRequest request) {

        // When the ExtendedAccessLogValve logs a value from the request it will print a dash in the event the value is null
        // whereas from the session an empty string is printed wrecking the column count in the access metrics log files and
        // complicating parsing otherwise we could not do this and just have x-S(eske_sessionid) in the valve configuration.

        HttpSession httpSession = request.getSession();
        String eskeSessionId = httpSession.getAttribute(HttpSessionConstants.ESKE_SESSIONID).toString();

        request.setAttribute("access_metrics_sessionid", eskeSessionId);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
