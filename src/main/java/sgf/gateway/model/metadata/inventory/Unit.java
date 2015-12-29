package sgf.gateway.model.metadata.inventory;

public interface Unit {

    /**
     * Gets the symbol.
     *
     * @return the symbol
     */
    String getSymbol();

    /**
     * Sets the symbol.
     *
     * @param symbol the new symbol
     */
    void setSymbol(String symbol);

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
     * Gets the quantity.
     *
     * @return the quantity
     */
    String getQuantity();

    /**
     * Sets the quantity.
     *
     * @param quantity the new quantity
     */
    void setQuantity(String quantity);

}
