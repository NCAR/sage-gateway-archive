package sgf.gateway.publishing.thredds.transform;

import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import thredds.catalog.InvDataset;

public interface ThreddsDescriptiveMetadataTransformer {

    void transform(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata);

}
