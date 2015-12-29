package sgf.gateway.dao.security.impl.hibernate;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.security.AuthorizationTokenRepository;
import sgf.gateway.model.security.AuthorizationToken;

import java.io.Serializable;

public class AuthorizationTokenRepositoryImpl extends AbstractRepositoryImpl<AuthorizationToken, Serializable> implements AuthorizationTokenRepository {

    @Override
    protected Class<AuthorizationToken> getEntityClass() {
        return AuthorizationToken.class;
    }

    /**
     * {@inheritDoc}
     */
    public void storeAuthorizationToken(AuthorizationToken authzToken) {
        add(authzToken);
    }

    /**
     * {@inheritDoc}
     */
    public AuthorizationToken getAuthorizationToken(UUID token) {
        return get(token);
    }
}
