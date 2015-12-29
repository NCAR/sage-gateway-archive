package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;

public class DatasetDataCenterFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetDataCenterFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {
        String value = dataset.getDataCenter().getShortName();
        return value;
    }
}
