package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetVersionTransformer;
import thredds.catalog.InvDataset;

public class DatasetVersionPublishedStateTransformer implements ThreddsDatasetVersionTransformer {

    @Override
    public void transform(InvDataset invDataset, DatasetVersion datasetVersion) {

        datasetVersion.publish();
    }
}
