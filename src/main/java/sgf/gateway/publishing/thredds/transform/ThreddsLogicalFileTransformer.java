package sgf.gateway.publishing.thredds.transform;

import sgf.gateway.model.metadata.inventory.LogicalFile;
import thredds.catalog.InvDataset;

public interface ThreddsLogicalFileTransformer {

    void transform(InvDataset invDatasetFile, LogicalFile logicalFile);
}
