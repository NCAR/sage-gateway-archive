package sgf.gateway.service.security.impl.acegi;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import sgf.gateway.model.security.GuestUser;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;

/**
 * Spring Security specific implementation of {@link RuntimeUserService}.
 */

public class AcegiRuntimeUserServiceImpl implements RuntimeUserService {

    /**
     * Acegi specific implementation that retrieves the current user from the Authentication object contained in the Thread-local SecurityContext.
     *
     * @return the user
     */
    public User getUser() {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();

        User result = null;

        if (null != authentication) {
            result = getUser(authentication);
        }

        return result;
    }

    /**
     * Method that extracts the User object from the Spring Security Authentication object stored in the security context. Logic must be applied to
     * differentiate based on the specific {@link Authentication} types, since Spring Security does not have a consistent way to store user personal
     * information.
     *
     * @param authentication
     * @return
     */
    public User getUser(Authentication authentication) {

        User user = null;

        if (authentication instanceof AnonymousAuthenticationToken) {

            user = GuestUser.getInstance();

        } else {

            Object principal = authentication.getPrincipal();

            if (principal instanceof AcegiUserDetails) {

                AcegiUserDetails acegiUserDetails = (AcegiUserDetails) principal;

                user = acegiUserDetails.getUser();
            }
        }

        return user;
    }

    /**
     * Acegi specific implementation that checks whether the Authentication object in the SecurtyContext is of type AnonymousAuthenticationToken.
     *
     * @return true, if checks if is user authenticated
     */
    public boolean isUserAuthenticated() {

        boolean authenticated = false;
        SecurityContext secCtx = SecurityContextHolder.getContext();
        Authentication auth = secCtx.getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {

            authenticated = true;
        }

        return authenticated;
    }
}
