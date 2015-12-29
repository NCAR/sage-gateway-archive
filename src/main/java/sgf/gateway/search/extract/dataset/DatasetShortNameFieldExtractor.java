package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;

public class DatasetShortNameFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetShortNameFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {
        String value = dataset.getShortName();
        return value;
    }
}
