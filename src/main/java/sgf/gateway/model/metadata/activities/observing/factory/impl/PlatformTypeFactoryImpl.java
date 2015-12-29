package sgf.gateway.model.metadata.activities.observing.factory.impl;

import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.activities.observing.PlatformType;
import sgf.gateway.model.metadata.activities.observing.PlatformTypeImpl;
import sgf.gateway.model.metadata.activities.observing.factory.PlatformTypeFactory;

public class PlatformTypeFactoryImpl implements PlatformTypeFactory {

    /**
     * The new instance identifier strategy.
     */
    private final NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    /**
     * Instantiates a new campaign factory impl.
     *
     * @param newInstanceIdentifierStrategy the new instance identifier strategy
     */
    public PlatformTypeFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {

        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    /**
     * Creates the campaign factory.
     *
     * @param shortName the short name
     * @return the campaign impl
     */
    public PlatformType create(String shortName) {

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(PlatformTypeImpl.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        PlatformType platformType = new PlatformTypeImpl(vuId.getIdentifierValue(), null, shortName);

        return platformType;
    }
}
