package sgf.gateway.model.metadata;

import org.safehaus.uuid.UUID;

/**
 * TODO: this class needs to be refactored to have a TAXONOMY discrimintaor, and to inherit from AbstractPersistableEntity.
 */
public enum ResolutionType {

    UNKNOWN("Unknown"), LOW("Low"), MEDIUM("Medium"), MEDIUM_HIGH("Medium-High"), HIGH("High"), ULTRA_HIGH("Ultra-High");

    /**
     * The name.
     */
    private String name;

    /**
     * Instantiates a new resolution type.
     *
     * @param name the name
     */
    private ResolutionType(String name) {

        this.name = name;
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

    public UUID getIdentifier() {
        throw new RuntimeException("UUID not available for objects of type ResolutionType");
    }
}
