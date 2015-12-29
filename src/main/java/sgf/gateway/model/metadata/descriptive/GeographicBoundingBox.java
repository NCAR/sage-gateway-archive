package sgf.gateway.model.metadata.descriptive;

public interface GeographicBoundingBox {

    Double getWestBoundLongitude();

    void setWestBoundLongitude(Double westBoundLongitude);

    Double getEastBoundLongitude();

    void setEastBoundLongitude(Double eastBoundLongitude);

    Double getSouthBoundLatitude();

    void setSouthBoundLatitude(Double southBoundLatitude);

    Double getNorthBoundLatitude();

    void setNorthBoundLatitude(Double northBoundLatitude);

}