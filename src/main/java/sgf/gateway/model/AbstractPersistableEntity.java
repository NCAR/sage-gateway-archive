package sgf.gateway.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.envers.NotAudited;
import org.safehaus.uuid.UUID;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Date;

/**
 * Base class for high level Entities that need to be stored in the database.
 * <p/>
 * This class is intended to provide only the very basic identifier information needed for database persistence. Any other behavior should be in the more
 * specific layers of the model. Those interfaces should define the model behavior, not persistence details.
 * <p/>
 * Subclasses should only be objects that have their own discrete life cycles. Child objects that have their life cycles directly tied to their parents should
 * not be subclasses of this class.
 * <p/>
 * <b>The current implementation is directly coupled to Hibernate mapping policies</b> Subclasses have to full fill some contract obligations for this class to
 * work properly:
 * <ul>
 * <li>UUID Identifier that is mapped with field access.</li>
 * <li>Integer version that is mapped with field access.</li>
 * <li><b>The version field must have NULL mapped as the unsaved-value.</b></li>
 * </ul>
 * <p/>
 * TODO - Issues that need to be reviewed:
 * <ul>
 * <li>Move Identifier construction outside of the class, would allow to migrate the new instance policy to the ORM layer.</li>
 * <li>Need to get clients calling isPersisted rather than scattering the policy logic all over the application.</li>
 * <li>Need to do something with the time stamps. Move them into the classes that really have them, make sure all subclasses really map them?</li>
 * <li>Need to review equals and hash code, should they include version?</li>
 * </ul>
 */
public abstract class AbstractPersistableEntity implements PersistableEntity {

    private static final Log LOG = LogFactory.getLog(AbstractPersistableEntity.class);

    /**
     * The version, provides optimistic locking support.
     * <p/>
     * Null should be the unsaved version, Does not risk collisions with scripts and database default values. Essentially built in constant that can be guarded
     * easily in the DB.
     * <p/>
     * FIXME - This is an implementation detail, need to get this extracted to some other policy somewhere...
     */
    @NotAudited
    public Integer version;

    /**
     * The published.
     */
    @NotAudited
    protected Date dateCreated;

    /**
     * The last update.
     */
    @NotAudited
    protected Date dateUpdated;

    /**
     * The identifier, currently UUID type 4.
     */
    private UUID identifier;

    /**
     * Instantiates a new persistent object without generating a new ID.
     */
    protected AbstractPersistableEntity() {
        super();
    }

    public AbstractPersistableEntity(Serializable identifier, Serializable version) {
        super();

        Assert.notNull(identifier, "Identifier is a required constructor argument");
        this.identifier = (UUID) identifier;

        Assert.notNull(identifier, "Version is a required constructor argument");
        // Hack until we can get all the fixed
        if (version instanceof Integer) {
            this.version = (Integer) version;
        } else if (version instanceof Long) {
            this.version = ((Long) version).intValue();
        }
    }

    /**
     * Instantiates a new persistent object, with the option to generate a new identifier.
     *
     * @param newInstance the create identifier
     */
    public AbstractPersistableEntity(boolean newInstance) {
        super();
        if (newInstance) {
            generateIdentifier();
        }
    }

    /**
     * @param identifier
     * @deprecated - Should not be calling this constructor, it breaks the hibernate new instance policy. The policy define null version as a new instance. If
     * you are passing in an ID is should also have the corresponding version that matches the database as well. Should be calling
     * sgf.gateway.model.AbstractPersistableEntity.AbstractPersistentObject(UUID, Integer) instead.
     * If a hibernate session is flushed with one of these
     * instance referenced it may crash, it will try to do an insert with a value that already exists.
     */
    @Deprecated
    public AbstractPersistableEntity(UUID identifier) {
        super();
        this.identifier = identifier;
        this.version = 1;
    }

    public AbstractPersistableEntity(UUID identifier, Integer version) {
        super();

        Assert.notNull(identifier, "Identifier is a required constructor argument");
        this.identifier = identifier;

        Assert.notNull(identifier, "Version is a required constructor argument");
        this.version = version;
    }

    /**
     * Generate a new identifier, also resets the version field so the object should represent a 'new' instance.
     */
    // FIXME - Move this to a factory somewhere...
    public final void generateIdentifier() {
        this.identifier = org.safehaus.uuid.UUIDGenerator.getInstance().generateRandomBasedUUID();
        this.version = null;
        LOG.trace("Genereated new Identifier for " + this.getClass().getSimpleName() + ", UUID: " + identifier + ", Version: " + version);
    }

    /**
     * The timestamp of when the object was created. Read Only
     *
     * @return
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * The timestamp of when the object was last updated. Read Only
     *
     * @return
     */
    public Date getDateUpdated() {
        return dateUpdated;
    }


    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UUID noArg) {
        throw new UnsupportedOperationException("Identifier is immutable");
    }

    // FIXME - Need to review, I think this should also include the version field as well.
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getIdentifier()).toHashCode();
    }

    // FIXME - Need to review, I think this should also include the version field as well.
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof AbstractPersistableEntity)) {
            return false;
        }
        AbstractPersistableEntity castOther = (AbstractPersistableEntity) other;

        boolean equal = new EqualsBuilder().append(this.getIdentifier(), castOther.getIdentifier()).isEquals();
        return equal;
    }

    // FIXME - Need to review, I think this should also include the version field as well.
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", getIdentifier()).toString();
    }

    public String getJavaType() {
        return null;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

}
