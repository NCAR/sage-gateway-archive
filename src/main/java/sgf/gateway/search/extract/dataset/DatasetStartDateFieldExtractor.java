package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.TimePeriod;

import java.util.Date;

public class DatasetStartDateFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetStartDateFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Date beginDate = null;

        TimePeriod timePeriod = dataset.getDescriptiveMetadata().getTimePeriod();

        if (null != timePeriod) {

            beginDate = timePeriod.getBegin();
        }

        return beginDate;
    }
}