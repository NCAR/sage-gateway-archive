package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.DataProductType;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;

public class DatasetDataProductTypeFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetDataProductTypeFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        String value = null;

        DescriptiveMetadata descriptiveMetadata = dataset.getDescriptiveMetadata();
        DataProductType dataProductType = descriptiveMetadata.getDataProductType();

        if (null != dataProductType) {
            value = dataProductType.getName();
        }

        return value;
    }
}
