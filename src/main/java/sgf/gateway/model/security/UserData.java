package sgf.gateway.model.security;

import sgf.gateway.model.AbstractPersistableEntity;

/**
 * Class that holds additional user information, as requested to enable specific group memberships.
 */
public class UserData extends AbstractPersistableEntity {

    /**
     * Not nullable persistent field.
     */
    private String value;

    /**
     * Not nullable persistent field.
     */
    private GroupData groupData;

    /**
     * Not nullable persistent field.
     */
    private User user;

    /**
     * Constructor to instantiate a new instance with all required fields.
     *
     * @param value     the value
     * @param groupData the group data
     * @param user      the user
     */
    public UserData(String value, GroupData groupData, User user) {

        // generates UUID
        super(true);
        this.value = value;
        this.groupData = groupData;
        this.user = user;
    }

    /**
     * No arguments constructor for Hibernate.
     */
    public UserData() {

        super();
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {

        return this.value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(String value) {

        this.value = value;
    }

    /**
     * Gets the group data.
     *
     * @return the group data
     */
    public GroupData getGroupData() {

        return this.groupData;
    }

    /**
     * Sets the group data.
     *
     * @param groupData the new group data
     */
    public void setGroupData(GroupData groupData) {

        this.groupData = groupData;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {

        return this.user;
    }

    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {

        this.user = user;
    }

}
