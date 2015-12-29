package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetVersionTransformer;
import thredds.catalog.InvDataset;

import java.util.List;

public class DatasetVersionTransformerAggregator implements ThreddsDatasetVersionTransformer {

    private final List<ThreddsDatasetVersionTransformer> transformers;

    public DatasetVersionTransformerAggregator(final List<ThreddsDatasetVersionTransformer> transformers) {

        this.transformers = transformers;
    }

    @Override
    public void transform(InvDataset invDataset, DatasetVersion datasetVersion) {

        for (ThreddsDatasetVersionTransformer transformer : this.transformers) {

            transformer.transform(invDataset, datasetVersion);
        }
    }

}
