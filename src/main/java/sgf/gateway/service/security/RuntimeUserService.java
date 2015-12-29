package sgf.gateway.service.security;

import org.springframework.security.core.Authentication;
import sgf.gateway.model.security.User;

/**
 * Interface defining operations for retrieving and updating the current user from the application context (typically, the web application context). NOTE:
 * currently the interface does NOT specify functionality for setting a new user in the application context, since it is expected that this operation is
 * performed elsewhere in the application.
 */
public interface RuntimeUserService {

    /**
     * Method to retrieve the current user from the application context.
     *
     * @return the user
     */
    User getUser();

    /**
     * Get user given the Authentication Object (e.g. from the Session)
     */
    User getUser(Authentication authentication);

    /**
     * Method to check whether a user has authenticated.
     *
     * @return true, if checks if is user authenticated
     */
    boolean isUserAuthenticated();

}
