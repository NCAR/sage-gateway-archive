package sgf.gateway.dao.security;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.Repository;
import sgf.gateway.model.security.AuthorizationToken;

import java.io.Serializable;

public interface AuthorizationTokenRepository extends Repository<AuthorizationToken, Serializable> {

    /**
     * Store authorization token.
     *
     * @param token the token
     */
    void storeAuthorizationToken(AuthorizationToken token);

    /**
     * Gets the authorization token.
     *
     * @param token the token
     * @return the authorization token
     */
    AuthorizationToken getAuthorizationToken(UUID token);

}
