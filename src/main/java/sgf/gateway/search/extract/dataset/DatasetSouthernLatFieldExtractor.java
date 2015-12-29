package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.GeographicBoundingBox;

public class DatasetSouthernLatFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetSouthernLatFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Double southernLatitude = null;

        if ((null != dataset.getDescriptiveMetadata()) && (null != dataset.getDescriptiveMetadata().getGeographicBoundingBox())) {

            GeographicBoundingBox boundingBox = dataset.getDescriptiveMetadata().getGeographicBoundingBox();

            southernLatitude = boundingBox.getSouthBoundLatitude();

        }

        return southernLatitude;
    }
}