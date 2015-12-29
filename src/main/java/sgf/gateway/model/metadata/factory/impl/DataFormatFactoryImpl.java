package sgf.gateway.model.metadata.factory.impl;

import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.DataFormat;
import sgf.gateway.model.metadata.DataFormatImpl;
import sgf.gateway.model.metadata.factory.DataFormatFactory;

public class DataFormatFactoryImpl implements DataFormatFactory {

    /**
     * The new instance identifier strategy.
     */
    private NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    /**
     * @param newInstanceIdentifierStrategy the new instance identifier strategy
     */
    public DataFormatFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {
        super();
        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    /**
     * {@inheritDoc}
     */
    public DataFormat create(String name) {

        return create(name, null);
    }

    /**
     * {@inheritDoc}
     */
    public DataFormat create(String name, String description) {

        Assert.notNull(name, "Data format name cannot be null");

        Assert.hasText(name, "Data format name must contain non-whitespace");

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
        DataFormatImpl dataFormatImpl = new DataFormatImpl(vuId.getIdentifierValue(), null, trimmedName, trimmedDescription);

        return dataFormatImpl;
    }

}
