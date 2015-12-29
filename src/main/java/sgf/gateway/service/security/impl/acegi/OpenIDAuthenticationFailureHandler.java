package sgf.gateway.service.security.impl.acegi;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import sgf.gateway.service.security.impl.acegi.handler.OpenidAuthenticationExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenIDAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final OpenidAuthenticationExceptionHandler exceptionHandler;

    public OpenIDAuthenticationFailureHandler(OpenidAuthenticationExceptionHandler exceptionHandler) {

        this.exceptionHandler = exceptionHandler;
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // TODO catch all the different types of exceptions that can occur and give a reasonable message for the different types of exceptions.
        // TODO catch self signed certificate exception if possible.
        // TODO catch incorrect openids: nthan.hook.myopenid.com

        boolean handled = this.exceptionHandler.handleException(request, response, exception);

        if (!handled) {

            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
