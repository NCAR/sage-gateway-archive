package sgf.gateway.service.security.impl.acegi.handler;

import org.apache.commons.validator.EmailValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.util.StringUtils;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;
import sgf.gateway.security.authentication.BadCredentialsWithOpenIDGuessesException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvalidOpenidAsEmailHandler implements OpenidAuthenticationExceptionHandler, MessageSourceAware {

    private final UserRepository userRepository;

    private final String authenticationFailureUrl;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public InvalidOpenidAsEmailHandler(final UserRepository userRepository, final String authenticationFailureUrl) {

        this.userRepository = userRepository;
        this.authenticationFailureUrl = authenticationFailureUrl;
    }

    @Override
    public boolean handleException(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

        boolean handled = false;

        String openidIdentifier = request.getParameter("openid_identifier");

        if (StringUtils.hasText(openidIdentifier)) {

            boolean isEmailAddress = EmailValidator.getInstance().isValid(openidIdentifier);

            if (isEmailAddress) {

                List<User> users = userRepository.findUsersByEmail(openidIdentifier);

                HttpSession httpSession = request.getSession();

                if (!users.isEmpty()) {

                    BadCredentialsWithOpenIDGuessesException badCredentialsException = new BadCredentialsWithOpenIDGuessesException(messages.getMessage("InvalidOpenidAsEmailHandler.badCredentials", "Bad credentials"));

                    List<String> openidGuesses = new ArrayList<String>();

                    for (User user : users) {

                        String openid = user.getOpenid();

                        openidGuesses.add(openid);
                    }

                    badCredentialsException.setOpenidGuesses(openidGuesses);

                    httpSession.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, badCredentialsException);

                } else {

                    BadCredentialsException badCredentialsException = new BadCredentialsException(messages.getMessage("InvalidOpenidAsEmailHandler.badCredentials", "Bad credentials"));

                    httpSession.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, badCredentialsException);
                }

                DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                //redirectStrategy.setContextRelative(true);

                try {

                    redirectStrategy.sendRedirect(request, response, this.authenticationFailureUrl);

                } catch (IOException e) {

                    throw new RuntimeException(e);
                }

                handled = true;
            }
        }

        return handled;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {

        this.messages = new MessageSourceAccessor(messageSource);
    }
}