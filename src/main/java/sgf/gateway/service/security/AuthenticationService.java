package sgf.gateway.service.security;

/**
 * Interface defining authentication API.
 * <p/>
 * Note that for performance issues (query the database only once), the result of an authentication operation contains both the true/false result, and possibly
 * also the full user details object.
 */
public interface AuthenticationService {

    /**
     * Method to authenticate a user by OpenID and password.
     *
     * @param openid   : the user openid identity, that uniquely identifies the user across all gateways
     * @param password : the user's (clear text) password.
     * @return the authentication result
     * @throws Exception the exception
     */
    AuthenticationResult authenticateByOpenid(String openid, String password) throws Exception;

    /**
     * Method to authenticate a user by Username and password.
     *
     * @param username : the user's username, which is unique for any given gateway
     * @param password : the user's (clear text) password.
     * @return
     * @throws Exception
     */
    AuthenticationResult authenticateByUsername(String username, String password) throws Exception;

}
