package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.publishing.thredds.transform.ThreddsDescriptiveMetadataTransformer;
import thredds.catalog.InvDataset;

import java.util.List;

public class DescriptiveMetadataTransformerAggregator implements ThreddsDescriptiveMetadataTransformer {

    private final List<ThreddsDescriptiveMetadataTransformer> transformers;

    public DescriptiveMetadataTransformerAggregator(final List<ThreddsDescriptiveMetadataTransformer> transformers) {

        this.transformers = transformers;
    }

    @Override
    public void transform(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata) {

        for (ThreddsDescriptiveMetadataTransformer transformer : this.transformers) {

            transformer.transform(invDataset, descriptiveMetadata);
        }
    }

}
