package sgf.gateway.model.metadata.inventory;

import sgf.gateway.model.AbstractPersistableEntity;

import java.io.Serializable;

public class UnitImpl extends AbstractPersistableEntity implements Unit {

    /**
     * The symbol.
     */
    private String symbol;

    /**
     * The name.
     */
    private String name;

    /**
     * The quantity.
     */
    private String quantity;

    /**
     * Default constructor, instantiation of objects from the persistence mechanism.
     */
    protected UnitImpl() {

    }

    /**
     * General constructor for creating new instances.
     *
     * @param identifier the identifier
     * @param version    the version
     * @param symbol     the symbol
     */
    public UnitImpl(Serializable identifier, Serializable version, String symbol) {

        super(identifier, version);
        this.symbol = symbol;
    }

    /**
     * {@inheritDoc}
     */
    public String getSymbol() {

        return this.symbol;
    }

    /**
     * {@inheritDoc}
     */
    public void setSymbol(String symbol) {

        this.symbol = symbol;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {

        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public String getQuantity() {

        return this.quantity;
    }

    /**
     * {@inheritDoc}
     */
    public void setQuantity(String quantity) {

        this.quantity = quantity;
    }
}
