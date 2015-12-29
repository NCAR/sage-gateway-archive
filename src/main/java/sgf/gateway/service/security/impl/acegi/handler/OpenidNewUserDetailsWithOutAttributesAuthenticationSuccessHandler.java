package sgf.gateway.service.security.impl.acegi.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.GuestUser;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.impl.acegi.AcegiUserDetails;
import sgf.gateway.service.security.impl.acegi.NewUserOpenIDAuthenticationToken;
import sgf.gateway.service.security.impl.acegi.OpenidNewUserDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenidNewUserDetailsWithOutAttributesAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public OpenidNewUserDetailsWithOutAttributesAuthenticationSuccessHandler(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        if (authentication.getPrincipal() instanceof OpenidNewUserDetails && authentication instanceof OpenIDAuthenticationToken && ((OpenIDAuthenticationToken) authentication).getStatus().equals(OpenIDAuthenticationStatus.SUCCESS)) {

            OpenIDAuthenticationToken openidAuthenticationToken = (OpenIDAuthenticationToken) authentication;

            User user = this.userRepository.findUserByUserName(GuestUser.GUEST_USERNAME);

            AcegiUserDetails acegiUserDetails = new AcegiUserDetails(user);

            SecurityContext securityContext = SecurityContextHolder.getContext();

            OpenIDAuthenticationToken successOpenidAuthenticationToken = new NewUserOpenIDAuthenticationToken(acegiUserDetails, acegiUserDetails.getAuthorities(),
                    openidAuthenticationToken.getIdentityUrl(), openidAuthenticationToken.getAttributes());

            securityContext.setAuthentication(successOpenidAuthenticationToken);

            DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            redirectStrategy.setContextRelative(false);

            redirectStrategy.sendRedirect(request, response, "/openid/registration.html");
        }
    }
}
