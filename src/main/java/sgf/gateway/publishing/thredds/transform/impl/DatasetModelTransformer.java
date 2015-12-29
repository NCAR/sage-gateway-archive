package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.publishing.thredds.transform.ThreddsDescriptiveMetadataTransformer;
import thredds.catalog.InvDataset;

public class DatasetModelTransformer implements ThreddsDescriptiveMetadataTransformer {

    public void transform(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata) {
        String modelName = invDataset.findProperty("model");
        descriptiveMetadata.setModelName(modelName);
    }
}
