package sgf.gateway.model.metadata;

import org.safehaus.uuid.UUID;

public interface DataProductType {

    UUID getIdentifier();

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName();

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name);

    /**
     * Gets the description.
     *
     * @return the description
     */

    public String getDescription();

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description);

}
