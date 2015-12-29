package sgf.gateway.service.security.impl.spring;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.Status;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AuthenticationResult;
import sgf.gateway.service.security.AuthenticationService;
import sgf.gateway.service.security.CryptoService;

/**
 * Implementation of {@link AuthenticationService} that validates the openid/username/password combination, and additionally checks that the user has a VALID
 * status. Note that -if available- the User object is always returned as part of the returned result, even if the authentication failed, because it could be
 * inspected to provide better error messaging. (for example, the User object can be inspected to detect a pending account).
 * <p/>
 * Note that the Gateway property is required only when operating the service in single-gateway mode (i.e. when authenticating with username and password
 * without the openid).
 */

public class AuthenticationServiceImpl implements AuthenticationService {

    private final transient CryptoService cryptoService;

    private final UserRepository userDao;

    /**
     * Instantiates a new authentication service implementation.
     *
     * @param userDao             the user dao
     * @param cryptoService       the crypto service
     */

    public AuthenticationServiceImpl(UserRepository userDao, CryptoService cryptoService) {

        this.userDao = userDao;
        this.cryptoService = cryptoService;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AuthenticationResult authenticateByOpenid(String openid, String password) throws Exception {

        // set default authentication result
        final AuthenticationResult result = new AuthenticationResult(false, null);

        if (StringUtils.hasText(openid) && StringUtils.hasText(password)) {
            final User user = AuthenticationServiceImpl.this.userDao.findUserByOpenid(openid);
            if (user != null) {
                // note: always return User object, even if the authentication failed
                result.setUser(user);

                // user has correct password AND has valid status (was approved, never revoked)
                if (AuthenticationServiceImpl.this.cryptoService.validate(password, user.getPassword()) && (user.getStatus() == Status.VALID)) {
                    result.setAuthenticated(true);
                } // valid openid, password

            } // user found in system
        } // openid, password not null

        return result;

    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AuthenticationResult authenticateByUsername(String username, String password) throws Exception {

        // set default authentication result
        final AuthenticationResult result = new AuthenticationResult(false, null);

        if ((username != null) && (password != null)) {
            final User user = AuthenticationServiceImpl.this.userDao.findUserByUserName(username);
            if (user != null) {
                // note: always return User object, even if the authentication failed
                result.setUser(user);

                // user has correct password
                if ((user.getPassword() != null) && AuthenticationServiceImpl.this.cryptoService.validate(password, user.getPassword())
                        // user has valid status (was approved, never revoked)
                        && (user.getStatus() == Status.VALID)) {

                    result.setAuthenticated(true);

                } // valid username, password combination
            } // user found in system
        } // username, password not null

        return result;

    }

}
