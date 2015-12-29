package sgf.gateway.model.metadata;

public enum CadisResolutionType {

    RESOLUTION_UNKNOWN("Unknown"),
    RESOLUTION_POINT("Point"),
    RESOLUTION_LESS_1M("Less than 1 Meter"),
    RESOLUTION_1M_30M("1 Meter to 30 Meters"),
    RESOLUTION_30M_100M("30 Meters to 100 Meters"),
    RESOLUTION_100M_250M("100 Meters to 250 Meters"),
    RESOLUTION_250M_500M("250 Meters to 500 Meters"),
    RESOLUTION_500M_1KM("500 Meters to 1 Kilometer"),
    RESOLUTION_1KM("1 Kilometer"),
    RESOLUTION_1KM_10KM("1 Kilometer to 10 Kilometers"),
    RESOLUTION_10KM_50KM("10 Kilometers to 50 Kilometers"),
    RESOLUTION_50KM_100KM("50 Kilometers to 100 Kilometers"),
    RESOLUTION_100KM_250KM("100 Kilometers to 250 Kilometers"),
    RESOLUTION_250KM_500KM("250 Kilometers to 500 Kilometers"),
    RESOLUTION_500KM_1000KM("500 Kilometers to 1000 Kilometers"),
    RESOLUTION_GREATER_1000KM("Greater than 1000 Kilometers");

    private final String longName;

    private CadisResolutionType(String longName) {
        this.longName = longName;
    }

    public String getLongName() {
        return this.longName;
    }
}
