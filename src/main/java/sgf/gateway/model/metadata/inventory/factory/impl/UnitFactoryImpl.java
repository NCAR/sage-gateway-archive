package sgf.gateway.model.metadata.inventory.factory.impl;

import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.inventory.LogicalFileImpl;
import sgf.gateway.model.metadata.inventory.Unit;
import sgf.gateway.model.metadata.inventory.UnitImpl;
import sgf.gateway.model.metadata.inventory.factory.UnitFactory;

public class UnitFactoryImpl implements UnitFactory {

    /**
     * The new instance identifier strategy.
     */
    private NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    /**
     * Instantiates a new logical file factory impl.
     *
     * @param newInstanceIdentifierStrategy the new instance identifier strategy
     */
    public UnitFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {

        super();
        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    /**
     * Instantiates a new Unit based on symbol. Trims symbol white space.
     *
     * @param symbol the unit symbol string
     */
    public Unit create(String symbol) {

        Assert.notNull(symbol, "Unit symbol cannot be null");

        Assert.hasText(symbol, "Unit symbol must contain non-whitespace");

        String trimmedSymbol = symbol.trim();

        // TODO - Remove the cast once we can get the identifiers cleaned up.
        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(LogicalFileImpl.class);

        // Temporary Cast
        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        // TODO - Remove the cast once we can get the identifiers cleaned up.
        UnitImpl unitImpl = new UnitImpl(vuId.getIdentifierValue(), null, trimmedSymbol);

        return unitImpl;
    }

}
