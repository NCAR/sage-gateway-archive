package sgf.gateway.model.metadata.inventory.factory.impl;

import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.inventory.LogicalFileImpl;
import sgf.gateway.model.metadata.inventory.StandardName;
import sgf.gateway.model.metadata.inventory.StandardNameImpl;
import sgf.gateway.model.metadata.inventory.StandardNameType;
import sgf.gateway.model.metadata.inventory.factory.StandardNameFactory;

public class StandardNameFactoryImpl implements StandardNameFactory {

    /**
     * The new instance identifier strategy.
     */
    private NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    /**
     * Instantiates a new logical file factory impl.
     *
     * @param newInstanceIdentifierStrategy the new instance identifier strategy
     */
    public StandardNameFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {

        super();
        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    public StandardName create(String name, StandardNameType type) {

        Assert.notNull(name, "name cannot be null");
        Assert.notNull(type, "type cannot be null");

        // TODO - Remove the cast once we can get the identifiers cleaned up.
        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(LogicalFileImpl.class);

        // Temporary Cast
        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        // TODO - Remove the cast once we can get the identifiers cleaned up.
        StandardNameImpl standardNameImpl = new StandardNameImpl(vuId.getIdentifierValue(), null, name, type);

        return standardNameImpl;
    }

}
