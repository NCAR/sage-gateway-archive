package sgf.gateway.model.metrics;

public interface GeoCode {

    public Double getLatitude();

    public Double getLongitude();

    public String getCountry();

    public String getCountryCode();

    public String getCity();

    public String getState();
}
