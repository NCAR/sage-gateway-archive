package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.publishing.thredds.transform.ThreddsDescriptiveMetadataTransformer;
import thredds.catalog.InvDataset;

public class DatasetLanguageTransformer implements ThreddsDescriptiveMetadataTransformer {

    public static final String DOCUMENTATION_TYPE = "language";

    @Override
    public void transform(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata) {

        if ((invDataset.getDocumentation() != null) && (invDataset.getDocumentation().size() > 0)) {

            String doc = invDataset.getDocumentation(DOCUMENTATION_TYPE);

            descriptiveMetadata.setLanguage(doc);
        }
    }

}
