package sgf.gateway.service.security;

import sgf.gateway.model.security.User;

/**
 * Simple class holding the result of an authentication operation. Specifically: a) a boolean value stating wether the authentication was succesfull or not b) a
 * User object which -if not null- contains detailed user information
 */
public class AuthenticationResult {

    // default authentication result
    /**
     * The authenticated.
     */
    private boolean authenticated = false; // true for succesfull
    // authentication, false otherwise
    /**
     * The user.
     */
    private User user = null;

    /**
     * Instantiates a new authentication result.
     *
     * @param authenticated the authenticated
     * @param user          the user
     */
    public AuthenticationResult(boolean authenticated, User user) {

        this.authenticated = authenticated;
        this.user = user;
    }

    /**
     * Checks if is authenticated.
     *
     * @return true, if is authenticated
     */
    public boolean isAuthenticated() {

        return this.authenticated;
    }

    /**
     * Sets the authenticated.
     *
     * @param authenticated the new authenticated
     */
    public void setAuthenticated(boolean authenticated) {

        this.authenticated = authenticated;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {

        return this.user;
    }

    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {

        this.user = user;
    }

}
