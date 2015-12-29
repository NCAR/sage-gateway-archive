package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.DatasetProgress;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.publishing.thredds.transform.ThreddsDescriptiveMetadataTransformer;
import sgf.gateway.service.publishing.api.PublishingException;
import thredds.catalog.InvDataset;

public class DatasetProgressTransformer implements ThreddsDescriptiveMetadataTransformer {

    public static final String DOCUMENTATION_TYPE = "processing_level";

    @Override
    public void transform(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata) {

        String stateString = null;

        if ((invDataset.getDocumentation() != null) && (invDataset.getDocumentation().size() > 0)) {

            stateString = invDataset.getDocumentation(DOCUMENTATION_TYPE);
        }

        if ((stateString != null) && (!stateString.equals(""))) {

            try {

                String adjustedStateString = stateString.replace(' ', '_').toUpperCase();

                DatasetProgress state = DatasetProgress.valueOf(adjustedStateString);

                descriptiveMetadata.setDatasetProgress(state);

            } catch (final IllegalArgumentException illegalArgExcept) {

                throw new PublishingException(stateString + " is an unknown dataset progress.", illegalArgExcept);
            }
        }
    }
}
