package sgf.gateway.model.metadata.inventory.factory;

import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.inventory.StandardName;
import sgf.gateway.model.metadata.inventory.Variable;

import java.util.Set;

public interface VariableFactory {

    Variable create(DatasetVersion datasetVersion, String name, Set<StandardName> standardNames);

    Variable create(DatasetVersion datasetVersion, String name, StandardName standardName);

}
