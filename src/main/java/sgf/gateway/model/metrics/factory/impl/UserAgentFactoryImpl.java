package sgf.gateway.model.metrics.factory.impl;

import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metrics.UserAgent;
import sgf.gateway.model.metrics.factory.UserAgentFactory;

public class UserAgentFactoryImpl implements UserAgentFactory {

    private final NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    public UserAgentFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {

        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    public UserAgent createUserAgent(String name) {

        // TODO: Check to verify name isn't null.

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(UserAgent.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        UserAgent userAgent = new UserAgent(vuId.getIdentifierValue(), vuId.getVersion(), name);

        return userAgent;
    }
}
