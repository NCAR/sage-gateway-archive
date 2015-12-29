package sgf.gateway.model.security;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.AbstractPersistableEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Superclass of entities (User and Group) that can be assigned a Permission.
 */
public abstract class Principal extends AbstractPersistableEntity {

    /**
     * The authorization statements on this entity.
     */
    private Set<Permission> permissions = new HashSet<Permission>();

    /**
     * Creation date for this entity, fixed when the object is instantiated.
     */
    private Date dateCreated = new Date();

    /**
     * No arguments constructor for Hibernate.
     */
    protected Principal() {
    }

    public Principal(boolean newInstance) {

        super(newInstance);
    }

    /**
     * Constructor to match an already existing instance that is persisted in the database.
     *
     * @param identifier the identifier
     * @param version    the version
     */
    public Principal(UUID identifier, Integer version) {

        super(identifier, version);
    }

    public Principal(Serializable identifier, Serializable version) {

        super(identifier, version);
    }

    /**
     * Gets the permissions.
     *
     * @return the permissions
     */
    public Set<Permission> getPermissions() {

        return this.permissions;
    }

    /**
     * Sets the permissions.
     *
     * @param permissions the new permissions
     */
    public void setPermissions(Set<Permission> permissions) {

        this.permissions = permissions;
    }

    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    public abstract String getName();
}
