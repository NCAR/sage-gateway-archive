package sgf.gateway.model.security;

/**
 * Enumeration representing the possible values of a user's membership status.
 */
public enum Status {

    UNKNOWN(0L, "unknown", "Unknown"), REQUESTED(1L, "requested", "Waiting Email Confirmation"), PENDING(2L, "pending", "Pending Approval"), VALID(3L, "valid",
            "Valid"), INVALID(4L, "invalid", "Invalid");

    /**
     * The id.
     */
    private long id;

    /**
     * The name.
     */
    private String name;

    /**
     * The description.
     */
    private String description;

    /**
     * Instantiates a new status.
     *
     * @param id          the id
     * @param name        the name
     * @param description the description
     */
    private Status(long id, String name, String description) {

        this.id = id;
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

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {

        return this.id;
    }

    public static Status getById(int id) {
        for (Status status : Status.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return null;
    }
}
