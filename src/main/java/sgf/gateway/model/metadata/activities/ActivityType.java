package sgf.gateway.model.metadata.activities;

public enum ActivityType {

    /**
     * The ENSEMBLE.
     */
    ENSEMBLE("Ensemble"),

    /**
     * The EXPERIMENT.
     */
    EXPERIMENT("Experiment"),

    /**
     * The OBSERVATION.
     */
    OBSERVATION("Observation"),

    /**
     * The CAMPAIGN.
     */
    CAMPAIGN("AON Project"),

    PROJECT("Project");

    /**
     * The description.
     */
    private String description;

    /**
     * Instantiates a new activity type.
     *
     * @param description the description
     */
    private ActivityType(String description) {
        this.description = description;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

}
