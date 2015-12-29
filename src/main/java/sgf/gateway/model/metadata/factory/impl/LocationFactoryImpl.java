package sgf.gateway.model.metadata.factory.impl;

import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.Location;
import sgf.gateway.model.metadata.LocationImpl;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.factory.LocationFactory;

public class LocationFactoryImpl implements LocationFactory {

    /**
     * The new instance identifier strategy.
     */
    private final NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    /**
     * Instantiates a new location factory impl.
     *
     * @param newInstanceIdentifierStrategy the new instance identifier strategy
     */
    public LocationFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {

        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    /**
     * {@inheritDoc}
     */
    public Location create(String name, Taxonomy type) {

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(LocationImpl.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        Location location = new LocationImpl(vuId.getIdentifierValue(), null, name, type);

        return location;
    }

    /**
     * {@inheritDoc}
     */
    public Location create(String name, Taxonomy type, String description) {

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(LocationImpl.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        Location location = new LocationImpl(vuId.getIdentifierValue(), null, name, type);
        location.setDescription(description);

        return location;
    }

}
