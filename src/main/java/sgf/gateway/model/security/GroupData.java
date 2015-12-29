package sgf.gateway.model.security;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.AbstractPersistableEntity;

/**
 * Object representing a group registration field. Currently supported registration field types include a short text, a longer text, a mandatory or optional
 * mailing list, and a group license. Note that registration fields can be shared among multiple groups (so that users don't have to insert the same information
 * twice), and that for each group a given registration field may be required or not.
 */
public class GroupData extends AbstractPersistableEntity {

    /**
     * Unique registration field name.
     */
    private String name;

    /**
     * Registration field description.
     */
    private String description;

    /**
     * Optional value, referencing a mailing list address or a license location.
     */
    private String value;

    /**
     * The registration field type.
     */
    private GroupDataType type;

    /**
     * Constructor to instantiate a new object with all required fields.
     *
     * @param name        the name
     * @param description the description
     * @param type        the type
     */
    public GroupData(String name, String description, GroupDataType type) {

        // generate UUID
        super(true);
        this.name = name;
        this.description = description;
        this.type = type;
    }

    /**
     * No arguments constructor for Hibernate.
     */
    public GroupData() {

        super();
    }

    /**
     * Constructor to match an already existing instance that is persisted in the database.
     *
     * @param identifier the identifier
     * @param version    the version
     */
    public GroupData(UUID identifier, Integer version) {

        super(identifier, version);
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public GroupDataType getType() {

        return this.type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(GroupDataType type) {

        this.type = type;
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

    public String getValue() {

        return this.value;
    }

    public void setValue(String value) {

        this.value = value;
    }

}
