package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import thredds.catalog.InvDataset;

public class DatasetActivityTransformer implements ThreddsDatasetTransformer {

    private final ThreddsDatasetTransformer transformer;

    public DatasetActivityTransformer(ThreddsDatasetTransformer transformer) {
        super();
        this.transformer = transformer;
    }

    @Override
    public void transform(InvDataset invDataset, Dataset dataset) {
        dataset.removeAllActivities();
        this.transformer.transform(invDataset, dataset);
    }
}
