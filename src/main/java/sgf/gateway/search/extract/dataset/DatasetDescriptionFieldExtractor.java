package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;

public class DatasetDescriptionFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetDescriptionFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {
        String value = dataset.getDescription();
        return value;
    }
}
