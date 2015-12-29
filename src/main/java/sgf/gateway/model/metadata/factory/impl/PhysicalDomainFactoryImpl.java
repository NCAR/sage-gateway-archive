package sgf.gateway.model.metadata.factory.impl;

import org.safehaus.uuid.UUID;
import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.PhysicalDomain;
import sgf.gateway.model.metadata.factory.PhysicalDomainFactory;

public class PhysicalDomainFactoryImpl implements PhysicalDomainFactory {

    /**
     * The new instance identifier strategy.
     */
    private NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    /**
     * @param newInstanceIdentifierStrategy the new instance identifier strategy
     */
    public PhysicalDomainFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {
        super();
        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    /**
     * {@inheritDoc}
     */
    public PhysicalDomain create(String name) {

        Assert.notNull(name, "Physical domain name cannot be null");
        Assert.hasText(name, "Physical domain name must contain non-whitespace");

        String trimmedName = name.trim();

        // TODO - Remove the cast once we can get the identifiers cleaned up.
        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(PhysicalDomain.class);

        // Temporary Cast
        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        // TODO - Remove the cast once we can get the identifiers cleaned up.
        PhysicalDomain physicalDomain = new PhysicalDomain((UUID) vuId.getIdentifierValue(), null, trimmedName);

        return physicalDomain;
    }

}
