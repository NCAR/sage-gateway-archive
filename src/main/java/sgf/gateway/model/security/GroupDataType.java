package sgf.gateway.model.security;

/**
 * Enumeration holding all possible GroupData types, i.e. the types of fields that a user is requested to supply when they apply for group membership.
 */
public enum GroupDataType {

    SHORT_TEXT(0L, "Short Text", "Short textual input field"), LONG_TEXT(1L, "Long Text", "Long textual input field"),
    MAILING_LIST(2L, "Mailing List", "Group mailing list"), LICENSE(3L, "License", "Group license agreement");

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
     * Instantiates a new group data type.
     *
     * @param id          the id
     * @param name        the name
     * @param description the description
     */
    private GroupDataType(long id, String name, String description) {

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

    /**
     * Gets the group data type.
     *
     * @param name the name
     * @return the group data type
     */
    public static GroupDataType getGroupDataType(String name) {

        for (GroupDataType gdt : GroupDataType.values()) {
            if (gdt.getName().equals(name)) {
                return gdt;
            }
        }
        return null;
    }

}
