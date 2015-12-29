package sgf.gateway.model.metadata.inventory;

import sgf.gateway.model.AbstractPersistableEntity;

import java.io.Serializable;

public class StandardNameImpl extends AbstractPersistableEntity implements StandardName {

    /**
     * The name.
     */
    private String name;

    /**
     * The description.
     */
    private String description;

    /**
     * The units.
     */
    private Unit units;

    /**
     * The type.
     */
    private StandardNameType type;

    /**
     * The common.
     */
    private boolean common;

    /**
     * Instantiates a new standard name.
     */
    public StandardNameImpl() {

        super();
    }

    /**
     * Required arguments constructor.
     *
     * @param name the name
     * @param type the type
     */
    public StandardNameImpl(Serializable identifier, Serializable version, String name, StandardNameType type) {

        super(identifier, version);
        this.name = name;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    public StandardNameType getType() {

        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    public void setType(StandardNameType type) {

        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
    @Override
    public String getDescription() {

        return this.description;
    }

    /**
     * {@inheritDoc}
     */
    public void setDescription(String description) {

        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    public Unit getUnits() {

        return this.units;
    }

    /**
     * {@inheritDoc}
     */
    public void setUnits(Unit units) {

        this.units = units;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isCommon() {

        return this.common;
    }

    /**
     * {@inheritDoc}
     */
    public void setCommon(boolean common) {

        this.common = common;
    }
}
