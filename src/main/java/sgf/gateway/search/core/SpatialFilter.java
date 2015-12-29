package sgf.gateway.search.core;


public class SpatialFilter {

    private final Double westernLongitude;
    private final Double easternLongitude;
    private final Double southernLatitude;
    private final Double northernLatitude;

    public SpatialFilter(Double westernLongitude, Double easternLongitude,
                         Double southernLatitude, Double northernLatitude) {
        super();
        this.westernLongitude = westernLongitude;
        this.easternLongitude = easternLongitude;
        this.southernLatitude = southernLatitude;
        this.northernLatitude = northernLatitude;
    }

    public Double getWesternLongitude() {
        return westernLongitude;
    }

    public Double getEasternLongitude() {
        return easternLongitude;
    }

    public Double getSouthernLatitude() {
        return southernLatitude;
    }

    public Double getNorthernLatitude() {
        return northernLatitude;
    }
}
