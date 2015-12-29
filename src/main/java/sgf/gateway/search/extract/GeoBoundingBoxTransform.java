package sgf.gateway.search.extract;

public class GeoBoundingBoxTransform {

    public static Boolean boundingBoxStraddlesDateline(Double westernLongitude, Double easternLongitude) {

        Boolean straddles = false;

        Double reducedWesternLongitude = reduceWesternLongitude(westernLongitude);
        Double reducedEasternLongitude = reduceEasternLongitude(easternLongitude);

        if (reducedWesternLongitude > reducedEasternLongitude) {
            straddles = true;
        }

        return straddles;
    }

    public static Double reduceWesternLongitude(Double westernLongitude) {

        // -180 >= reduced westernLongitude of bounding box < 180

        Double reducedWesternLongitude = reduce(westernLongitude);

        if (reducedWesternLongitude == 180) {
            reducedWesternLongitude = -reducedWesternLongitude;
        }

        return reducedWesternLongitude;
    }

    public static Double reduceEasternLongitude(Double easternLongitude) {

        // -180 > reduced easternLongitude of bounding box <= 180

        Double reducedEasternLongitude = reduce(easternLongitude);

        if (reducedEasternLongitude == -180) {
            reducedEasternLongitude = -reducedEasternLongitude;
        }

        return reducedEasternLongitude;
    }

    public static Double extendEasterly(Double longitude) {
        Double extendedLongitude = longitude + 360;
        return extendedLongitude;
    }

    public static Double extendWesterly(Double longitude) {
        Double extendedLongitude = longitude - 360;
        return extendedLongitude;
    }

    private static Double reduce(Double longitude) {

        Double reducedLongitude = new Double(longitude);

        while (Math.abs(reducedLongitude) > 180) {
            if (reducedLongitude > 0) {
                reducedLongitude -= 360;
            } else {
                reducedLongitude += 360;
            }
        }

        return reducedLongitude;
    }
}
