package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.publishing.thredds.transform.ThreddsDescriptiveMetadataTransformer;
import thredds.catalog.InvDataset;
import thredds.catalog.ThreddsMetadata;

public class DatasetMetadataGeospatialCoverageTransformer implements ThreddsDescriptiveMetadataTransformer {

    private static final Double UNDEFINED_DOUBLE_VALUE = Double.NaN;

    @Override
    public void transform(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata) {

        if (invDataset.getGeospatialCoverage() != null) {

            GeographicPoint latitudeBounds = getLatitudeBounds(invDataset.getGeospatialCoverage());

            GeographicPoint longitudeBounds = getLongitudeBounds(invDataset.getGeospatialCoverage());

            descriptiveMetadata.setGeographicBoundingBox(longitudeBounds.getStart(), longitudeBounds.getEnd(), latitudeBounds.getStart(), latitudeBounds.getEnd());
        }
    }

    protected GeographicPoint getLatitudeBounds(ThreddsMetadata.GeospatialCoverage geospatialCoverage) {

        GeographicPoint latitudeBounds = processLatitudeCoordinateAxis(geospatialCoverage);
        return latitudeBounds;
    }

    protected GeographicPoint getLongitudeBounds(ThreddsMetadata.GeospatialCoverage geospatialCoverage) {

        GeographicPoint longitudeBounds = processLongitudeCoordinateAxis(geospatialCoverage);
        return longitudeBounds;
    }


    protected GeographicPoint processLatitudeCoordinateAxis(ThreddsMetadata.GeospatialCoverage geospatialCoverage) {

//		Double step = UNDEFINED_DOUBLE_VALUE;
        Double size = UNDEFINED_DOUBLE_VALUE;
        Double start = UNDEFINED_DOUBLE_VALUE;
        Double end;

        if (geospatialCoverage.getLatStart() != Double.NaN) {

            start = geospatialCoverage.getLatStart();
        }

        if (geospatialCoverage.getLatExtent() != Double.NaN) {

            size = geospatialCoverage.getLatExtent();
        }

//		if (geospatialCoverage.getLatResolution() != Double.NaN) {
//			
//			step = geospatialCoverage.getLatResolution();
//		}

        end = calculateEnd(start, size);

        GeographicPoint result = new GeographicPoint();

        result.setStart(start);
        result.setEnd(end);

        return result;
    }

    protected GeographicPoint processLongitudeCoordinateAxis(ThreddsMetadata.GeospatialCoverage geospatialCoverage) {

//		Double step = UNDEFINED_DOUBLE_VALUE;
        Double size = UNDEFINED_DOUBLE_VALUE;
        Double start = UNDEFINED_DOUBLE_VALUE;
        Double end;

        if (geospatialCoverage.getLonStart() != Double.NaN) {

            start = geospatialCoverage.getLonStart();
        }

        if (geospatialCoverage.getLonExtent() != Double.NaN) {

            size = geospatialCoverage.getLonExtent();
        }

//		if (geospatialCoverage.getLonResolution() != Double.NaN) {
//			
//			step = geospatialCoverage.getLonResolution();
//		}

        end = calculateEnd(start, size);

        GeographicPoint result = new GeographicPoint();

        result.setStart(start);
        result.setEnd(end);

        return result;
    }

    protected Double calculateEnd(Double start, Double size) {

        Double end = UNDEFINED_DOUBLE_VALUE;

        if ((start != null) && (size != null) && (!start.equals(UNDEFINED_DOUBLE_VALUE)) && (!size.equals(UNDEFINED_DOUBLE_VALUE))) {

            end = start + size;
        }

        return end;
    }

    protected class GeographicPoint {

        private double start;
        private double end;

        public double getStart() {
            return start;
        }

        public void setStart(double start) {
            this.start = start;
        }

        public double getEnd() {
            return end;
        }

        public void setEnd(double end) {
            this.end = end;
        }
    }
}
