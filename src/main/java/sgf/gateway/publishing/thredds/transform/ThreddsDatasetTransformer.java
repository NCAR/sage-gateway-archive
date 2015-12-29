package sgf.gateway.publishing.thredds.transform;

import sgf.gateway.model.metadata.Dataset;
import thredds.catalog.InvDataset;

public interface ThreddsDatasetTransformer {

    void transform(InvDataset invDataset, Dataset dataset);

}
