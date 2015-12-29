package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import thredds.catalog.InvDataset;

public class DatasetTitleTransformer implements ThreddsDatasetTransformer {

    @Override
    public void transform(InvDataset invDataset, Dataset dataset) {

        String name = invDataset.getName();

        dataset.setTitle(name);
    }
}
