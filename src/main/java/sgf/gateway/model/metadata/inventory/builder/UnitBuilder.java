package sgf.gateway.model.metadata.inventory.builder;

import sgf.gateway.model.metadata.inventory.Unit;
import sgf.gateway.model.metadata.inventory.factory.UnitFactory;

public interface UnitBuilder {

    /**
     * Looks up existing unit by symbol and creates a new unit if not found.
     *
     * @param symbol the symbol string for this unit.
     * @return existing or new unit instance
     */
    Unit build(String symbol);
}
