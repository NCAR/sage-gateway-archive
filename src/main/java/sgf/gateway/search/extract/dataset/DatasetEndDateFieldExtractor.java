package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.TimePeriod;

import java.util.Date;

public class DatasetEndDateFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetEndDateFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Date endDate = null;

        TimePeriod timePeriod = dataset.getDescriptiveMetadata().getTimePeriod();

        if (null != timePeriod) {

            endDate = timePeriod.getEnd();
        }

        return endDate;
    }
}