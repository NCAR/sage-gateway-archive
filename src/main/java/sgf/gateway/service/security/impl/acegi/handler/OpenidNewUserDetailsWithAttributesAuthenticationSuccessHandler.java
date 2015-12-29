package sgf.gateway.service.security.impl.acegi.handler;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import sgf.gateway.model.security.*;
import sgf.gateway.model.security.factory.UserFactory;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.impl.acegi.AcegiUserDetails;
import sgf.gateway.service.security.impl.acegi.OpenidConsumerAttributeExchangeFacade;
import sgf.gateway.service.security.impl.acegi.OpenidNewUserDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OpenidNewUserDetailsWithAttributesAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final UserFactory userFactory;
    private final AccountService accountService;

    public OpenidNewUserDetailsWithAttributesAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler, UserFactory userFactory, AccountService accountService) {

        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.userFactory = userFactory;
        this.accountService = accountService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        if (authentication.getPrincipal() instanceof OpenidNewUserDetails && authentication instanceof OpenIDAuthenticationToken && ((OpenIDAuthenticationToken) authentication).getStatus().equals(OpenIDAuthenticationStatus.SUCCESS)) {

            boolean handled = false;

            try {
                OpenIDAuthenticationToken openidAuthenticationToken = (OpenIDAuthenticationToken) authentication;

                User user = this.createUser(openidAuthenticationToken);

                this.validateEmail(user.getEmail());

                Group defaultGroup = accountService.getGroup(Group.GROUP_DEFAULT);
                user.getMemberships().add(new Membership(user, defaultGroup, Role.DEFAULT, Status.VALID));

                this.accountService.storeUser(user);

                AcegiUserDetails acegiUserDetails = new AcegiUserDetails(user);

                openidAuthenticationToken.setDetails(acegiUserDetails);

                OpenIDAuthenticationToken successOpenidAuthenticationToken = new OpenIDAuthenticationToken(acegiUserDetails, acegiUserDetails.getAuthorities(), openidAuthenticationToken.getIdentityUrl(), openidAuthenticationToken.getAttributes());

                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(successOpenidAuthenticationToken);

                handled = true;

                super.onAuthenticationSuccess(request, response, authentication);
            } catch (IllegalArgumentException e) {

            }

            if (!handled) {

                this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }

    private User createUser(OpenIDAuthenticationToken openidAuthenticationToken) {

        List<OpenIDAttribute> attributes = openidAuthenticationToken.getAttributes();

        OpenidConsumerAttributeExchangeFacade facade = new OpenidConsumerAttributeExchangeFacade(attributes);

        String openid = openidAuthenticationToken.getIdentityUrl();
        String firstName = facade.getFirstName();
        String lastName = facade.getLastName();
        String email = facade.getEmail();

        User user = this.userFactory.createRemoteUser(openid, firstName, lastName, email);

        return user;
    }

    private void validateEmail(String email) {

        if (!EmailValidator.getInstance().isValid(email)) {

            throw new IllegalArgumentException("Invalid email address");
        }
    }
}
