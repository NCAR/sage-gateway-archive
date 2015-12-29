package sgf.gateway.model.metadata;

/**
 * The class State represents a controlled vocabulary to express the working/processing state of a dataset.
 */
public enum DatasetProgress {

    /**
     * The NONE.
     */
    NONE("Not Assigned", "Dataset state not assigned."),

    /**
     * The PLANNED.
     */
    PLANNED("Planned", "Dataset in planning stage."),

    /**
     * The I n_ work.
     */
    IN_WORK("In Work", "Dataset actively being collected/analyzed."),

    /**
     * The COMPLETE.
     */
    COMPLETE("Completed", "Dataset fully collected/analyzed.");

    /**
     * The name.
     */
    private String name;

    /**
     * The description.
     */
    private String description;

    /**
     * Instantiates a new state.
     *
     * @param name        the name
     * @param description the description
     */
    private DatasetProgress(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {

        return this.name;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {

        return this.description;
    }

}
