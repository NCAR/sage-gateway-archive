package sgf.gateway.model.metadata.activities.modeling;

import sgf.gateway.model.PersistableEntity;

public interface Forcing extends PersistableEntity {

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    void setName(String name);

    /**
     * Gets the description.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Sets the desctiption.
     *
     * @param description the new desctiption
     */
    void setDescription(String description);

    /**
     * Gets the value.
     *
     * @return the value
     */
    String getValue();

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    void setValue(String value);

    /**
     * Gets the type.
     *
     * @return the type
     */
    ForcingType getType();
}
