package sgf.gateway.service.security.impl.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.impl.acegi.AcegiUserDetails;

public class ReloadUserPerRequestHttpSessionSecurityContextRepository extends HttpSessionSecurityContextRepository {

    private UserRepository userRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ReloadUserPerRequestHttpSessionSecurityContextRepository.class);

    public ReloadUserPerRequestHttpSessionSecurityContextRepository(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {

        SecurityContext context = super.loadContext(requestResponseHolder);

        this.logContext(context);

        Authentication authentication = context.getAuthentication();

        if (authentication instanceof UsernamePasswordAuthenticationToken) {

            UserDetails userDetails = this.createNewUserDetailsFromPrincipal(authentication.getPrincipal());

            // Create a new Authentication object, Authentications are immutable.
            UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());

            context.setAuthentication(newAuthentication);

        } else if (authentication instanceof OpenIDAuthenticationToken) {

            UserDetails userDetails = this.createNewUserDetailsFromPrincipal(authentication.getPrincipal());

            OpenIDAuthenticationToken openidAuthenticationToken = (OpenIDAuthenticationToken) authentication;

            // Create a new Authentication object, Authentications are immutable.
            OpenIDAuthenticationToken newAuthentication = new OpenIDAuthenticationToken(userDetails, userDetails.getAuthorities(), openidAuthenticationToken.getIdentityUrl(), openidAuthenticationToken.getAttributes());

            context.setAuthentication(newAuthentication);
        }

        return context;
    }

    private void logContext(SecurityContext context) {

        LOG.debug("context: " + context);

        Authentication authentication = context.getAuthentication();

        LOG.debug("authorization: " + authentication);

        if (authentication instanceof OpenIDAuthenticationToken || authentication instanceof UsernamePasswordAuthenticationToken) {

            AcegiUserDetails userDetails = (AcegiUserDetails) authentication.getPrincipal();

            LOG.debug("user details: " + userDetails);
            LOG.debug("user uuid: " + userDetails.getUserIdentifier());
        }
    }

    private UserDetails createNewUserDetailsFromPrincipal(Object principal) {

        AcegiUserDetails userDetails = (AcegiUserDetails) principal;

        User user = this.userRepository.getUser(userDetails.getUserIdentifier());

        userDetails = new AcegiUserDetails(user);

        return userDetails;
    }
}
