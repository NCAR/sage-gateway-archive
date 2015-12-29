package sgf.gateway.service.geocode.ipinfodb;

import sgf.gateway.model.metrics.GeoCode;
import sgf.gateway.service.geocode.ipinfodb.jackson.IpinfodbResponse;

public class IpinfodbGeoCode implements GeoCode {

    private IpinfodbResponse response;

    public IpinfodbGeoCode(IpinfodbResponse response) {
        this.response = response;
    }

    @Override
    public Double getLatitude() {
        return Double.valueOf(response.getLatitude());
    }

    @Override
    public Double getLongitude() {
        return Double.valueOf(response.getLongitude());
    }

    @Override
    public String getCountry() {
        return response.getCountryName();
    }

    @Override
    public String getCountryCode() {
        return response.getCountryCode();
    }

    @Override
    public String getCity() {
        return response.getCityName();
    }

    @Override
    public String getState() {
        return response.getRegionName();
    }
}
