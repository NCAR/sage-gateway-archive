package sgf.gateway.model.metadata;

import sgf.gateway.model.AbstractPersistableEntity;

import java.io.Serializable;

/**
 * The DataProductType class is intended to represent a controlled vocabulary for the product type of a geo-physical dataset, used both in describing a dataset,
 * and for search and discovery purposes. Example values include "History Files", "Single Variable Time Series", etc. This class is NOT an enumeration to allow
 * for the possibility of instantiating and persisting new instances dynamically. Note that many Dataset instances may reference the same DataProductType
 * instance.
 */
public class DataProductTypeImpl extends AbstractPersistableEntity implements DataProductType {

    /**
     * The name.
     */
    private String name;

    /**
     * The description.
     */
    private String description;

    /**
     * Default constructor, instantiation of objects from the persistence mechanism.
     */
    protected DataProductTypeImpl() {

        super(false);
    }

    /**
     * Full arguments constructor.
     *
     * @param identifier  the identifier
     * @param version     the version
     * @param name        the name
     * @param description the description
     */
    public DataProductTypeImpl(Serializable identifier, Integer version, String name, String description) {

        super(identifier, version);
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
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
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName() + ": id=" + getIdentifier() + " name=" + getName() + " description=" + getDescription());
        return sb.toString();
    }
}
