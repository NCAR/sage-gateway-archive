package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.GeographicBoundingBox;

public class DatasetNorthernLatFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetNorthernLatFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Double northernLatitude = null;

        if ((null != dataset.getDescriptiveMetadata()) && (null != dataset.getDescriptiveMetadata().getGeographicBoundingBox())) {

            GeographicBoundingBox boundingBox = dataset.getDescriptiveMetadata().getGeographicBoundingBox();

            northernLatitude = boundingBox.getNorthBoundLatitude();

        }

        return northernLatitude;
    }
}