package sgf.gateway.model.metadata.inventory;

import org.safehaus.uuid.UUID;

import java.util.Collection;

/**
 * The Class Variable represents a physical variable (sometimes also called "parameter") that is part of a Dataset. Variable instances are "owned" by a Dataset,
 * i.e. they are meant to be local to a Dataset and not shared among datasets. A Variable may have one or more StandardNames, which reference the physical
 * quantity the Variable is meant to represent.
 */
public interface Variable {

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Sets the name.
     *
     * @param name the new name, must not be null or whitespace.
     */
    void setName(String name);

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

    /**
     * Return the Unit value assigned to the variable.
     *
     * @return the Unit value, maybe null.
     */
    Unit getUnits();

    /**
     * Sets the units.
     *
     * @param units the new units
     */
    void setUnits(Unit units);

    /**
     * Gets the standard names.
     *
     * @return the standard names
     */
    Collection<StandardName> getStandardNames();

    /**
     * Adds a standard name to the variable.
     *
     * @param standardName to add.
     */
    void addStandardName(StandardName standardName);

    /*
     * FIXME - Temporary, should be removed when we clean up controller tests to use mocks.
     */
    UUID getIdentifier();

}
