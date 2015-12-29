package sgf.gateway.service.security.impl.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.safehaus.uuid.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.security.AuthorizationTokenRepository;
import sgf.gateway.model.security.AuthorizationToken;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AuthorizationTokenService;

import java.net.URI;
import java.util.Calendar;

/**
 * The Class AuthorizationTokenServiceImpl.
 */
public class AuthorizationTokenServiceImpl implements AuthorizationTokenService {

    private static final Log LOG = LogFactory.getLog(AuthorizationTokenServiceImpl.class);

    /**
     * The authorization token dao.
     */
    private final AuthorizationTokenRepository authorizationTokenDao;

    /**
     * The life time units.
     */
    private final int lifeTimeUnits = Calendar.HOUR;

    /**
     * The lifetime.
     */
    private Integer lifetime = 4380; // 6 Months by default

    /**
     * Instantiates a new authorization token service implementation.
     *
     * @param authorizationTokenDao the authorization token dao
     * @param defaultLifetime       the default lifetime
     */
    public AuthorizationTokenServiceImpl(AuthorizationTokenRepository authorizationTokenDao, int defaultLifetime) {

        super();
        this.authorizationTokenDao = authorizationTokenDao;
        this.lifetime = defaultLifetime;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AuthorizationToken generateAuthorizationToken(URI uri, User user) {

        URI pathURI = this.getPath(uri);

        AuthorizationToken authzToken = new AuthorizationToken(pathURI, user, AuthorizationTokenServiceImpl.this.lifeTimeUnits, AuthorizationTokenServiceImpl.this.lifetime);

        AuthorizationTokenServiceImpl.this.authorizationTokenDao.storeAuthorizationToken(authzToken);

        return authzToken;
    }

    protected URI getPath(URI uri) {

        String path = uri.getPath();

        URI pathURI = URI.create(path);

        return pathURI;
    }

    public AuthorizationToken validateToken(UUID tokenUUID, URI uri) throws AuthorizationTokenInvalidException, AuthorizationTokenExpiredException, AuthorizationTokenNotFoundException {

        AuthorizationToken authorizationToken = this.authorizationTokenDao.getAuthorizationToken(tokenUUID);

        URI pathURI = this.getPath(uri);

        if (LOG.isTraceEnabled()) {
            LOG.trace("Retrieved authorization token: " + authorizationToken);
        }

        // Verify the token was found
        if (authorizationToken == null) {
            throw new AuthorizationTokenNotFoundException(tokenUUID);
        }

        // Verify the token found was for the specified URI
        if (!authorizationToken.getUri().equals(pathURI)) {
            throw new AuthorizationTokenInvalidException(tokenUUID, uri);
        }

        // Make sure it isn't expired.
        if (authorizationToken.isExpired()) {
            throw new AuthorizationTokenExpiredException(authorizationToken);
        }

        return authorizationToken;
    }
}
