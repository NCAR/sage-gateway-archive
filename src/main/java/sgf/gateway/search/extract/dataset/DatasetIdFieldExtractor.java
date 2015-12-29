package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;

public class DatasetIdFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetIdFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {
        String value = dataset.getIdentifier().toString();
        return value;
    }
}
