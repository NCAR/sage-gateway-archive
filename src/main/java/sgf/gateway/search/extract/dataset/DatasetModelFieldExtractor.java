package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;

public class DatasetModelFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetModelFieldExtractor() {
        super();
    }


    @Override
    protected Object getValue(Dataset dataset) {

        String value = dataset.getDescriptiveMetadata().getModelName();

        return value;
    }
}
