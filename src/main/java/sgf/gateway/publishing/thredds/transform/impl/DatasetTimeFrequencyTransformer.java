package sgf.gateway.publishing.thredds.transform.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.model.metadata.TimeFrequency;
import sgf.gateway.model.metadata.builder.TimeFrequencyBuilder;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.publishing.thredds.transform.ThreddsDescriptiveMetadataTransformer;
import thredds.catalog.InvDataset;

public class DatasetTimeFrequencyTransformer implements ThreddsDescriptiveMetadataTransformer {

    private TimeFrequencyBuilder timeFrequencyBuilder;

    public DatasetTimeFrequencyTransformer(TimeFrequencyBuilder timeFrequencyBuilder) {
        this.timeFrequencyBuilder = timeFrequencyBuilder;
    }

    @Override
    public void transform(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata) {

        descriptiveMetadata.removeAllTimeFrequencies();

        TimeFrequency timeFrequency = findOrCreateTimeFrequency(invDataset, descriptiveMetadata);

        if (timeFrequency != null) {

            descriptiveMetadata.addTimeFrequency(timeFrequency);
        }
    }

    protected TimeFrequency findOrCreateTimeFrequency(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata) {

        TimeFrequency timeFrequencyResult = null;

        String timeFrequencyString = this.getTimeFrequencyString(invDataset);

        if (StringUtils.hasText(timeFrequencyString)) {

            timeFrequencyResult = timeFrequencyBuilder.build(timeFrequencyString);
        }

        return timeFrequencyResult;
    }

    private String getTimeFrequencyString(InvDataset invDataset) {

        String timeFrequencyString = invDataset.findProperty("time_frequency");

        if (timeFrequencyString == null) {

            timeFrequencyString = invDataset.getDocumentation("frequency");
        }

        if (StringUtils.hasText(timeFrequencyString)) {

            timeFrequencyString = timeFrequencyString.trim();
        }

        return timeFrequencyString;
    }
}
