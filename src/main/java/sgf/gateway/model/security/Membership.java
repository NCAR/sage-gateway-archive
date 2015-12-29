package sgf.gateway.model.security;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.AbstractPersistableEntity;

/**
 * Object holding user-group membership information. Specifically, each Membership is a statement that assigns a specific Role to a User in a Group.
 */
public class Membership extends AbstractPersistableEntity {

    /**
     * persistent field.
     */
    private User user;

    /**
     * persistent field.
     */
    private Group group;

    /**
     * persistent field.
     */
    private Role role;

    /**
     * persistent field.
     */
    private Status status;

    /**
     * Constructor to instantiate a new instance with all required fields.
     *
     * @param user   the user
     * @param group  the group
     * @param role   the role
     * @param status the status
     */
    public Membership(User user, Group group, Role role, Status status) {

        // generates UUID
        super(true);
        this.user = user;
        this.group = group;
        this.role = role;
        this.status = status;
    }

    /**
     * No arguments constructor for Hibernate.
     */
    public Membership() {

        super();
    }

    public Membership(UUID identifier, int version) {

        super(identifier, version);
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

    /**
     * Gets the group.
     *
     * @return the group
     */
    public Group getGroup() {

        return this.group;
    }

    /**
     * Sets the group.
     *
     * @param group the new group
     */
    public void setGroup(Group group) {

        this.group = group;
    }

    /**
     * Gets the role.
     *
     * @return the role
     */
    public Role getRole() {

        return this.role;
    }

    /**
     * Sets the role.
     *
     * @param role the new role
     */
    public void setRole(Role role) {

        this.role = role;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public Status getStatus() {

        return this.status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(Status status) {

        this.status = status;
    }
}
