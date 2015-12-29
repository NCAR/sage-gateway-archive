package sgf.gateway.service.security.impl.acegi.handler;

import com.kennardconsulting.core.net.UrlEncodedQueryString;
import org.safehaus.uuid.UUID;
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

public class InvalidOpenidAsConfirmationLinkHandler implements OpenidAuthenticationExceptionHandler, MessageSourceAware {

    private final UserRepository userRepository;

    private final String authenticationFailureUrl;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public InvalidOpenidAsConfirmationLinkHandler(final UserRepository userRepository, final String authenticationFailureUrl) {

        this.userRepository = userRepository;
        this.authenticationFailureUrl = authenticationFailureUrl;
    }


    @Override
    public boolean handleException(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

        boolean handled = false;

        String openidIdentifier = request.getParameter("openid_identifier");

        if (StringUtils.hasText(openidIdentifier)) {

            boolean isConfirmationLink = this.isConfirmationLink(openidIdentifier);

            if (isConfirmationLink) {

                String identifier = this.getIdentifier(openidIdentifier);

                UUID uuid = UUID.valueOf(identifier);

                HttpSession httpSession = request.getSession();

                try {

                    User user = userRepository.getUser(uuid);

                    BadCredentialsWithOpenIDGuessesException badCredentialsException = new BadCredentialsWithOpenIDGuessesException(messages.getMessage("InvalidOpenidAsConfirmationLinkHandler.badCredentials", "Bad credentials"));

                    String openid = user.getOpenid();

                    List<String> openidGuesses = new ArrayList<String>();

                    openidGuesses.add(openid);

                    badCredentialsException.setOpenidGuesses(openidGuesses);

                    httpSession.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, badCredentialsException);

                } catch (Exception e) {

                    BadCredentialsException badCredentialsException = new BadCredentialsException(messages.getMessage("InvalidOpenidAsConfirmationLinkHandler.badCredentials", "Bad credentials"));

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

    public String getIdentifier(String confirmationLink) {

        int questionMarkIndex = confirmationLink.indexOf("?");

        String queryString = confirmationLink.substring(questionMarkIndex + 1, confirmationLink.length());

        UrlEncodedQueryString urlEncodedQueryString = UrlEncodedQueryString.parse(queryString);

        String identifier = urlEncodedQueryString.get("identifier");

        identifier = identifier.trim();

        return identifier;
    }

    public boolean isConfirmationLink(String confirmationLinkTest) {

        boolean value = false;

        if (confirmationLinkTest.contains("/ac/guest/secure/confirmAccount.htm")) {

            value = true;
        }

        return value;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {

        this.messages = new MessageSourceAccessor(messageSource);
    }
}
