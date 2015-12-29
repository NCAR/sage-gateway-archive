package sgf.gateway.service.security.impl.acegi.handler;

import org.openid4java.consumer.validation.UnsupportedIDPException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.WebAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UnsupportedIDPExceptionHandler implements OpenidAuthenticationExceptionHandler {

    private final String authenticationFailureUrl;

    public UnsupportedIDPExceptionHandler(String authenticationFailureUrl) {

        this.authenticationFailureUrl = authenticationFailureUrl;
    }

    @Override
    public boolean handleException(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

        boolean handled = false;

        if (exception instanceof UnsupportedIDPException) {

            // Code example taken from org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
            HttpSession httpSession = request.getSession();

            httpSession.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);

            DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            //redirectStrategy.setContextRelative(true);

            try {

                redirectStrategy.sendRedirect(request, response, this.authenticationFailureUrl);

            } catch (IOException e) {

                throw new RuntimeException(e);
            }

            handled = true;
        }

        return handled;
    }
}
