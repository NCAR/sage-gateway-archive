package sgf.gateway.service.geocode.geobytes;

import sgf.gateway.model.metrics.GeoCode;
import sgf.gateway.service.geocode.geobytes.jackson.Geobytes;
import sgf.gateway.service.geocode.geobytes.jackson.GeobytesResponse;

public class GeobytesGeoCode implements GeoCode {

    private Geobytes geobytes;

    public GeobytesGeoCode(GeobytesResponse response) {
        this.geobytes = response.getGeobytes();
    }

    @Override
    public Double getLatitude() {
        return geobytes.getLatitude();
    }

    @Override
    public Double getLongitude() {
        return geobytes.getLongitude();
    }

    @Override
    public String getCountry() {
        return geobytes.getCountry();
    }

    @Override
    public String getCountryCode() {
        return geobytes.getIso2(); // two character iso country code!!
    }

    @Override
    public String getCity() {
        return geobytes.getCity();
    }

    @Override
    public String getState() {
        return geobytes.getRegion();
    }
}
