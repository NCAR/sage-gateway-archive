package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import thredds.catalog.InvDataset;

import java.util.List;

public class DatasetTransformerAggregator implements ThreddsDatasetTransformer {

    private final List<ThreddsDatasetTransformer> transformers;

    public DatasetTransformerAggregator(List<ThreddsDatasetTransformer> transformers) {

        this.transformers = transformers;
    }

    @Override
    public void transform(InvDataset invDataset, Dataset dataset) {

        for (ThreddsDatasetTransformer transformer : transformers) {

            transformer.transform(invDataset, dataset);
        }
    }

}
