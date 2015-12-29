package sgf.gateway.model.security;

import org.safehaus.uuid.UUID;

import java.io.Serializable;
import java.util.*;

public class Group extends Principal implements Serializable {

    /**
     * Constant matching the name of the System group "Nobody".
     */
    public static final String GROUP_NOBODY = "Nobody";

    /**
     * Constant matching the name of the System group "Guest".
     */
    public static final String GROUP_GUEST = "Guest";

    /**
     * Constant matching the name of the System group "User".
     */
    public static final String GROUP_DEFAULT = "User";

    /**
     * Constant matching the name of the System group "Root".
     */
    public static final String GROUP_ROOT = "Root";

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Not-nullable persistent field.
     */
    private String name;

    /**
     * Not-nullable persistent field.
     */
    private String description;

    /**
     * Group approval type.
     */
    private boolean automaticApproval = false; // default

    /**
     * Group visibility.
     */
    private boolean visible = true; // default

    /**
     * Persistent field.
     */
    private Map<GroupData, Boolean> groupData = new HashMap<GroupData, Boolean>();

    /**
     * Persistent field.
     */
    private Set<Membership> memberships = new HashSet<Membership>();

    /**
     * Group default roles for newly enrolled users.
     */
    private Set<Role> defaultRoles = new HashSet<Role>();

    /**
     * No argument constructor for Hibernate.
     */
    // FIXME should this be protected scope?
    // Referenced directly by the ManageGroupUserCommand class.
    public Group() {

        super();
    }

    /**
     * Minimal constructor with all required fields.
     *
     * @param name        the name
     * @param description the description
     */
    public Group(String name, String description) {

        super(true);

        this.name = name;
        this.description = description;
    }

    /**
     * Convenience constructor with all fields, optional and required.
     *
     * @param name              the name
     * @param description       the description
     * @param visible           the visible
     * @param automaticApproval the automatic approval
     */
    public Group(String name, String description, boolean visible, boolean automaticApproval) {

        super(true);

        this.name = name;
        this.description = description;
        this.visible = visible;
        this.automaticApproval = automaticApproval;
    }

    /**
     * Constructor to match a pre-existing persisted instance.
     *
     * @param identifier the identifier
     */
    public Group(UUID identifier, int version) {

        super(identifier, version);
    }

    /**
     * Gets the default roles.
     *
     * @return the default roles
     */
    public Set<Role> getDefaultRoles() {

        return this.defaultRoles;
    }

    /**
     * Sets the default roles.
     *
     * @param defaultRoles the new default roles
     */
    public void setDefaultRoles(Set<Role> defaultRoles) {

        this.defaultRoles = defaultRoles;
    }

    /**
     * Method to return the users in this group that have a given (valid) role.
     *
     * @param role the role
     * @return the users in role
     */
    public Set<User> getUsersInRole(Role role) {

        final Set<User> users = new HashSet<>();
        for (final Membership membership : this.memberships) {
            if (membership.getRole().equals(role) && (membership.getStatus() == Status.VALID)) {
                users.add(membership.getUser());
            }
        }
        return users;
    }

    /**
     * Convenience method to return all (valid) users.
     *
     * @return the users
     */
    public Set<User> getUsers() {

        return getUsersInRole(Role.DEFAULT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Group: id=").append(getIdentifier()).append(" name=").append(getName()).append(" description=").append(getDescription()).append(" visible=")
                .append(isVisible()).append(" automatic approval=").append(isAutomaticApproval());
        return sb.toString();
    }

    /**
     * Convenience method to return the group administrators.
     *
     * @return the administrators
     */
    public Set<User> getAdministrators() {

        return getUsersInRole(Role.ADMIN);
    }

    /**
     * Convenience method to flag the "system" groups.
     *
     * @return true, if checks if is system
     */
    public boolean isSystem() {

        return (this.name.equals(GROUP_DEFAULT) || this.name.equals(GROUP_GUEST) ||
                this.name.equals(GROUP_ROOT) || this.name.equals(GROUP_NOBODY));
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {

        this.name = name;
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
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {

        this.description = description;
    }

    /**
     * Gets the group data.
     *
     * @return the group data
     */
    public Map<GroupData, Boolean> getGroupData() {
        return this.groupData;
    }

    public Collection<GroupData> getRegistrationFields() {

        return this.groupData.keySet();
    }

    /**
     * Sets the group data.
     *
     * @param groupData the new group data
     */
    public void setGroupData(Map<GroupData, Boolean> groupData) {

        this.groupData = groupData;
    }

    /**
     * Gets the memberships.
     *
     * @return the memberships
     */
    public Set<Membership> getMemberships() {

        return this.memberships;
    }

    /**
     * Sets the memberships.
     *
     * @param memberships the new memberships
     */
    public void setMemberships(Set<Membership> memberships) {

        this.memberships = memberships;
    }

    /**
     * Checks if is automatic approval.
     *
     * @return true, if is automatic approval
     */
    public boolean isAutomaticApproval() {

        return this.automaticApproval;
    }

    /**
     * Sets the automatic approval.
     *
     * @param automaticApproval the new automatic approval
     */
    public void setAutomaticApproval(boolean automaticApproval) {

        this.automaticApproval = automaticApproval;
    }

    /**
     * Checks if is visible.
     *
     * @return true, if is visible
     */
    public boolean isVisible() {

        return this.visible;
    }

    /**
     * Sets the visible.
     *
     * @param visible the new visible
     */
    public void setVisible(boolean visible) {

        this.visible = visible;
    }

    public Map<GroupData, Boolean> getGroupData(GroupDataType type) {

        Map<GroupData, Boolean> map = new HashMap<>();
        for (GroupData gd : this.groupData.keySet()) {
            if (gd.getType() == type) {
                map.put(gd, this.groupData.get(gd));
            }
        }
        return map;

    }

    /**
     * Method to return a partial GroupData map filtered by GroupData type and required attribute.
     *
     * @param type
     * @return
     */
    public List<GroupData> getGroupData(GroupDataType type, boolean required) {

        List<GroupData> list = new ArrayList<>();
        for (GroupData gd : this.groupData.keySet()) {
            if ((gd.getType() == type) && (this.groupData.get(gd).booleanValue() == required)) {
                list.add(gd);
            }
        }
        return list;

    }
}
