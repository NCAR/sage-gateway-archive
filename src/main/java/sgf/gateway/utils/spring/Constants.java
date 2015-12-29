package sgf.gateway.utils.spring;

/**
 * Interface containing constants for Spring MVC applications.
 */
public interface Constants {

    // error codes
    /**
     * The Constant ERROR_REQUIRED.
     */
    public static final String ERROR_REQUIRED = "error.required";

    /**
     * The Constant ERROR_INVALID.
     */
    public static final String ERROR_INVALID = "error.invalid";

    public static final String DEFAULT_PROFILE_NAME = "default";

    public static final String CADIS_PROFILE_NAME = "CADIS";

    /**
     * Temporary constants to fill out DatasetFactory.createDataset call.
     */
    public static final String TEMP_VERSION = "Temporary Version";
    public static final String TEMP_COMMENT = "Temporary Comment";

}
