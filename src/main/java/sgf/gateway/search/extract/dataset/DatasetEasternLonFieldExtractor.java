package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.GeographicBoundingBox;
import sgf.gateway.search.extract.GeoBoundingBoxTransform;

public class DatasetEasternLonFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetEasternLonFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Double easternLongitude = null;

        if ((null != dataset.getDescriptiveMetadata()) && (null != dataset.getDescriptiveMetadata().getGeographicBoundingBox())) {

            GeographicBoundingBox boundingBox = dataset.getDescriptiveMetadata().getGeographicBoundingBox();

            Double westernLongitude = GeoBoundingBoxTransform.reduceWesternLongitude(boundingBox.getWestBoundLongitude());
            easternLongitude = GeoBoundingBoxTransform.reduceEasternLongitude(boundingBox.getEastBoundLongitude());

            if (GeoBoundingBoxTransform.boundingBoxStraddlesDateline(westernLongitude, easternLongitude)) {
                easternLongitude = GeoBoundingBoxTransform.extendEasterly(easternLongitude);
            }
        }

        return easternLongitude;
    }
}