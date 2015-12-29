package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.GeographicBoundingBox;
import sgf.gateway.search.extract.GeoBoundingBoxTransform;

public class DatasetWesternLonFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetWesternLonFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Double westernLongitude = null;

        if ((null != dataset.getDescriptiveMetadata()) && (null != dataset.getDescriptiveMetadata().getGeographicBoundingBox())) {

            GeographicBoundingBox boundingBox = dataset.getDescriptiveMetadata().getGeographicBoundingBox();

            westernLongitude = GeoBoundingBoxTransform.reduceWesternLongitude(boundingBox.getWestBoundLongitude());

        }

        return westernLongitude;
    }
}