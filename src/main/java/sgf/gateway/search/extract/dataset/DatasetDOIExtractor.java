package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;

public class DatasetDOIExtractor extends DatasetAbstractFieldExtractor {

    public DatasetDOIExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {
        String value = dataset.getDOI();
        return value;
    }
}
