package sgf.gateway.publishing.thredds.transform;

import sgf.gateway.model.metadata.DatasetVersion;
import thredds.catalog.InvDataset;

public interface ThreddsDatasetVersionTransformer {

    void transform(InvDataset invDataset, DatasetVersion datasetVersion);

}
