package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import thredds.catalog.InvDataset;

public class DatasetDescriptionTransformer implements ThreddsDatasetTransformer {

    private static final String DESCRIPTION_KEY = "summary";

    @Override
    public void transform(InvDataset invDataset, Dataset dataset) {

        String description = getDescription(invDataset);

        dataset.setDescription(description);
    }

    protected String getDescription(InvDataset invDataset) {

        String description = null;

        if (invDataset.getDocumentation() != null && (invDataset.getDocumentation().size() > 0)) {

            description = invDataset.getDocumentation(DESCRIPTION_KEY);
        }

        return description;
    }
}
