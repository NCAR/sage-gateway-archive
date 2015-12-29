package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.TimeFrequency;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;

import java.util.ArrayList;
import java.util.Collection;

public class DatasetFrequenciesFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetFrequenciesFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();

        DescriptiveMetadata descriptiveMetadata = dataset.getDescriptiveMetadata();
        Collection<TimeFrequency> timeFrequencies = descriptiveMetadata.getTimeFrequencies();

        if (null != timeFrequencies) {

            for (TimeFrequency timeFrequency : timeFrequencies) {

                if (null != timeFrequency.getDescription()) {
                    value.add(timeFrequency.getDescription());
                }
            }
        }

        return value;
    }
}
