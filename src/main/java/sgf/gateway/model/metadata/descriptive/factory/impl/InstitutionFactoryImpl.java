package sgf.gateway.model.metadata.descriptive.factory.impl;

import org.safehaus.uuid.UUID;
import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.descriptive.Institution;
import sgf.gateway.model.metadata.descriptive.factory.InstitutionFactory;

public class InstitutionFactoryImpl implements InstitutionFactory {

    /**
     * the new instance identifier strategy.
     */
    private NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    public InstitutionFactoryImpl(final NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {
        super();
        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    /**
     * {@inheritDoc}
     */
    public Institution create(String name) {
        Assert.notNull(name, "Institution name cannot be null");
        Assert.hasText(name, "Institution name must contain non-whitespace");

        String trimmedName = name.trim();

        // TODO - Remove the cast once we can get the identifiers cleaned up.
        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(Institution.class);

        // Temporary Cast
        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        Institution institution = new Institution((UUID) vuId.getIdentifierValue(), null, trimmedName);

        return institution;
    }
}
