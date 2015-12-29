package sgf.gateway.web.exception.resolvers;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingExceptionResolver extends SimpleMappingExceptionResolver {

    private ExceptionHandlingService exceptionHandlingService;

    public LoggingExceptionResolver(ExceptionHandlingService exceptionHandlingService) {

        super();
        this.exceptionHandlingService = exceptionHandlingService;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ModelAndView result = super.doResolveException(request, response, handler, ex);

        if (null != result) {
            this.exceptionHandlingService.handleUnexpectedException(new UnhandledException(buildLogMessage(ex, request), ex));
        }

        return result;
    }

}

