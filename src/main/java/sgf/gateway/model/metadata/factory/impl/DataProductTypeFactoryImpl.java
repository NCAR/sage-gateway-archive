package sgf.gateway.model.metadata.factory.impl;

import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.DataFormatImpl;
import sgf.gateway.model.metadata.DataProductType;
import sgf.gateway.model.metadata.DataProductTypeImpl;
import sgf.gateway.model.metadata.factory.DataProductTypeFactory;

public class DataProductTypeFactoryImpl implements DataProductTypeFactory {

    /**
     * The new instance identifier strategy.
     */
    private NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    /**
     * @param newInstanceIdentifierStrategy the new instance identifier strategy
     */
    public DataProductTypeFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {
        super();
        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    /**
     * {@inheritDoc}
     */
    public DataProductType create(String name) {

        return create(name, null);
    }

    /**
     * {@inheritDoc}
     */
    public DataProductType create(String name, String description) {

        Assert.notNull(name, "Data product type name cannot be null");

        Assert.hasText(name, "Data product type name must contain non-whitespace");

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
        DataProductTypeImpl dataProductType = new DataProductTypeImpl(vuId.getIdentifierValue(), null, trimmedName, trimmedDescription);

        return dataProductType;
    }

}
