package sgf.gateway.model.metadata.inventory.builder;

import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.Variable;

public interface VariableBuilder {

    Variable build(DatasetVersion datasetVersion, String name, String description, String units);

    Variable build(LogicalFile logicalFile, String name, String description, String units);

    void setUnitBuilder(UnitBuilder unitBuilder);
}
