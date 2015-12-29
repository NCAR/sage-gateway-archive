package sgf.gateway.model.metadata.factory;

import sgf.gateway.model.metadata.Location;
import sgf.gateway.model.metadata.Taxonomy;

public interface LocationFactory {

    /**
     * Factory method to create a location and assign all required fields. The object identifier is generated and assigned by the factory.
     *
     * @param name location name, required
     * @param type location type, required
     * @return A location instance
     */
    public Location create(String name, Taxonomy type);

    /**
     * Factory method to create a location and assign all required and optional fields. The object identifier is generated and assigned by the factory.
     *
     * @param name        location name, required
     * @param type        location type, required
     * @param description the description, optional
     * @return A location instance
     */
    public Location create(String name, Taxonomy type, String description);
}
