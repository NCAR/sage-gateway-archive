package sgf.gateway.model.metadata.factory.impl;

import org.safehaus.uuid.UUID;
import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.DataFormatImpl;
import sgf.gateway.model.metadata.TimeFrequency;
import sgf.gateway.model.metadata.factory.TimeFrequencyFactory;

public class TimeFrequencyFactoryImpl implements TimeFrequencyFactory {

    /**
     * The new instance identifier strategy.
     */
    private NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    /**
     * @param newInstanceIdentifierStrategy the new instance identifier strategy
     */
    public TimeFrequencyFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {
        super();
        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    /**
     * {@inheritDoc}
     */
    public TimeFrequency create(String name) {

        return create(name, null);
    }

    /**
     * {@inheritDoc}
     */
    public TimeFrequency create(String name, String description) {

        Assert.notNull(name, "Time frequency name cannot be null");

        Assert.hasText(name, "Time frequency name must contain non-whitespace");

        String trimmedName = name.trim();

        String trimmedDescription = null;

        if (null != description) {

            trimmedDescription = description.trim();
        }

        // TODO - Remove the cast once we can get the identifiers cleaned up.
        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(DataFormatImpl.class);

        // Temporary Cast
        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        // TODO - Remove the cast once we can get the identifiers cleaned up.
        TimeFrequency timeFrequencyImpl = new TimeFrequency((UUID) vuId.getIdentifierValue(), null, trimmedName, trimmedDescription);

        return timeFrequencyImpl;
    }

}
