package sgf.gateway.model.metadata;

/**
 * The Location class is intended to represent a controlled vocabulary for the physical location of a geophysical dataset, expressed as a simple string for
 * search and discovery purposes, as well as for documentation. Example values could include "The North Pole", "Denver", "North America". This class is NOT an
 * enumeration since instances must be created and persisted dynamically.
 */
public interface Location {

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
     * Gets the type.
     *
     * @return the type
     */
    Taxonomy getType();

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    void setType(Taxonomy type);

    /**
     * Gets the description.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    void setDescription(String description);
}
