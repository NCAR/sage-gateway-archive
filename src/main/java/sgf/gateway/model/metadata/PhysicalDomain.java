package sgf.gateway.model.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.AbstractPersistableEntity;

/**
 * The PhysicalDomain class is intended to represent a controlled vocabulary for the physical domain of a geophysical dataset, used both in describing a
 * dataset, and for search and discovery purposes. Example values include "Ocean", "land", "River", etc. This class is NOT an enumeration to allow for the
 * possibility of instantiating and persisting new instances dynamically. Note that many Dataset instances may reference the same PhysicalDomain instance.
 */
public class PhysicalDomain extends AbstractPersistableEntity {

    private String name;

    /**
     * Instantiates a new physical domain.
     */
    public PhysicalDomain() {

        super(false); // do not generate UUID
    }

    /**
     * Full arguments constructor
     *
     * @param identifier the identifier
     * @param version    the version
     * @param name       the name
     */
    public PhysicalDomain(UUID identifier, Integer version, String name) {

        super(identifier, version);
        setName(name);
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {

        return this.name;
    }

    public String getDescription() {
        return null;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {

        this.name = name;
    }
}
