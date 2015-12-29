package sgf.gateway.service.security.impl.acegi.handler;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.WebAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class InvalidOpenidHandler implements OpenidAuthenticationExceptionHandler, MessageSourceAware {

    private final String authenticationFailureUrl;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public InvalidOpenidHandler(String authenticationFailureUrl) {

        this.authenticationFailureUrl = authenticationFailureUrl;
    }

    @Override
    public boolean handleException(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

        boolean handled = false;

        if (exception instanceof AuthenticationServiceException && exception.getMessage().startsWith("Unable to process claimed identity '")) {

            // Code example taken from org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
            HttpSession httpSession = request.getSession();

            BadCredentialsException badCredentialsException = new BadCredentialsException(messages.getMessage("InvalidOpenidHandler.badCredentials", "Bad credentials"));

            httpSession.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, badCredentialsException);

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

    @Override
    public void setMessageSource(MessageSource messageSource) {

        this.messages = new MessageSourceAccessor(messageSource);
    }
}
