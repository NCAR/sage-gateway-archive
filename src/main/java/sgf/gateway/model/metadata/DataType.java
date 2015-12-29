package sgf.gateway.model.metadata;

/**
 * The class DataType represents a controlled vocabulary that corresponds to the THREDDS dataType tag and the CADIS profile's "Spatial Type".
 */
public enum DataType {

    /**
     * The UNKNOWN.
     */
    UNKNOWN("Unknown"),
    /**
     * The GRID.
     */
    GRID("Grid"),
    /**
     * The IMAGE.
     */
    IMAGE("Image"),
    /**
     * The POINT.
     */
    POINT("Point"),
    /**
     * The RADIAL.
     */
    RADIAL("Radial"),
    /**
     * The STATION.
     */
    STATION("Station"),
    /**
     * The SWATH.
     */
    SWATH("Swath"),
    /**
     * The TRAJECTORY.
     */
    TRAJECTORY("Trajectory"),
    /**
     * The MULTIPLE.
     */
    MULTIPLE("Multiple"),
    /**
     * TRANSECT type
     */
    TRANSECT("Transect"),
    /**
     * Grids and Vectors Type
     */
    GRIDS_AND_VECTORS("Grids and Vectors"),
    /**
     * Grids and Vectors Type
     */
    VERTICAL_PROFILE("Vertical Profile");

    /**
     * The long name.
     */
    private String longName;

    /**
     * Instantiates a new data type.
     *
     * @param longName the long name
     */
    private DataType(String longName) {

        this.longName = longName;
    }

    /**
     * Gets the long name.
     *
     * @return the long name
     */
    public String getLongName() {

        return this.longName;
    }
}
