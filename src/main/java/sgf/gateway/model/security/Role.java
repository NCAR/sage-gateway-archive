package sgf.gateway.model.security;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.*;

/**
 * Class representing the possible roles of a user within a group.
 */
public class Role implements Serializable {

    /**
     * The NONE.
     */
    public static final Role NONE = new Role(0, "none", "None");

    /**
     * The DEFAULT.
     */
    public static final Role DEFAULT = new Role(1, "default", "Standard");

    /**
     * The PUBLISHER.
     */
    public static final Role PUBLISHER = new Role(2, "publisher", "Data Publisher");

    /**
     * The ADMIN.
     */
    public static final Role ADMIN = new Role(3, "admin", "Group Administrator");

    /**
     * The SUPER.
     */
    public static final Role SUPER = new Role(4, "super", "Super User");

    public static final Role CONTACT = new Role(5, "contact", "Data/Metadata Contact");

    private static final long serialVersionUID = 1L;

    /**
     * The id.
     */
    private Integer id;

    /**
     * The name.
     */
    private String name;

    /**
     * The description.
     */
    private String description;

    /**
     * NOTE: statically assign Operations to built-in Roles. IMPORTANT: must match database setup.
     */
    static {
        Role.DEFAULT.getOperations().add(Operation.READ);

        Role.PUBLISHER.getOperations().add(Operation.READ);
        Role.PUBLISHER.getOperations().add(Operation.WRITE);

        Role.ADMIN.getOperations().add(Operation.READ);
        Role.ADMIN.getOperations().add(Operation.WRITE);
        Role.ADMIN.getOperations().add(Operation.EXECUTE);

        Role.SUPER.getOperations().add(Operation.READ);
        Role.SUPER.getOperations().add(Operation.WRITE);
        Role.SUPER.getOperations().add(Operation.EXECUTE);
    }

    /**
     * The Operations enabled by this Role.
     */
    private Set<Operation> operations = new HashSet<>();

    /**
     * No-argument constructor for Hibernate.
     */
    public Role() {

    }

    /**
     * Constructor to instantiate a new Role with all required fields.
     *
     * @param id          the id
     * @param name        the name
     * @param description the description
     */
    public Role(Integer id, String name, String description) {

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
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Gets the role.
     *
     * @param name the name
     * @return the role
     */
    public static Role getRole(String name) {

        if (name.equals(Role.DEFAULT.getName())) {
            return Role.DEFAULT;
        } else if (name.equals(Role.PUBLISHER.getName())) {
            return Role.PUBLISHER;
        } else if (name.equals(Role.ADMIN.getName())) {
            return Role.ADMIN;
        } else if (name.equals(Role.SUPER.getName())) {
            return Role.SUPER;
        } else if (name.equals(Role.CONTACT.getName())) {
            return Role.CONTACT;
        } else {
            return Role.NONE;
        }
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {

        return this.id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Integer id) {

        this.id = id;
    }

    /**
     * Utility method to convert a list of Role names into a Set of Roles.
     *
     * @param roleNames the role names
     * @return the roles
     */
    public static Set<Role> getRoles(List<String> roleNames) {

        Set<Role> roles = new HashSet<>();

        for (String roleName : roleNames) {
            roles.add(Role.getRole(roleName));
        }
        return roles;
    }

    /**
     * Utility method to convert a collection of Roles into a list of role names.
     *
     * @param roles the roles
     * @return the role names
     */
    public static List<String> getRoleNames(Collection<Role> roles) {

        List<String> roleNames = new ArrayList<>();

        for (Role role : roles) {
            roleNames.add(role.getName());
        }
        return roleNames;
    }

    /**
     * Gets the operations.
     *
     * @return the operations
     */
    public Set<Operation> getOperations() {

        return this.operations;
    }

    /**
     * Sets the operations.
     *
     * @param operations the new operations
     */
    public void setOperations(Set<Operation> operations) {

        this.operations = operations;
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
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {

        if (!(other instanceof Role)) {

            return false;
        }

        Role castOther = (Role) other;

        boolean equals = new EqualsBuilder().append(getId(), castOther.getId()).isEquals();
        return equals;
    }

}
