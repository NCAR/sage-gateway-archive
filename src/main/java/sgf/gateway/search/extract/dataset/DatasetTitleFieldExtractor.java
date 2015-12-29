package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;

public class DatasetTitleFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetTitleFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {
        String value = dataset.getTitle();
        return value;
    }
}
