package sgf.gateway.model.metadata.inventory;

public interface StandardName {

    /**
     * Gets the type.
     *
     * @return the type
     */
    public StandardNameType getType();

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(StandardNameType type);

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

    /**
     * Gets the units.
     *
     * @return the units
     */
    public Unit getUnits();

    /**
     * Sets the units.
     *
     * @param units the new units
     */
    public void setUnits(Unit units);

    /**
     * Checks if is common.
     *
     * @return true, if is common
     */
    public boolean isCommon();

    /**
     * Sets the common.
     *
     * @param common the new common
     */
    public void setCommon(boolean common);

}
