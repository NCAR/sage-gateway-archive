package sgf.gateway.model.metadata.inventory.factory;

import sgf.gateway.model.metadata.inventory.Unit;

public interface UnitFactory {

    Unit create(String symbol);
}
