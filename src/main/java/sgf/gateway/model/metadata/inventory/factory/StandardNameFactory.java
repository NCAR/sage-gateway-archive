package sgf.gateway.model.metadata.inventory.factory;

import sgf.gateway.model.metadata.inventory.StandardName;
import sgf.gateway.model.metadata.inventory.StandardNameType;

public interface StandardNameFactory {

    StandardName create(String name, StandardNameType type);
}
