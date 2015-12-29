package sgf.gateway.model.metadata.factory;

import sgf.gateway.model.metadata.TimeFrequency;

public interface TimeFrequencyFactory {

    /**
     * Factory method to create a timeFrequency and assign all required fields. The object identifier is generated and assigned by the factory.
     *
     * @param name data format name, required
     * @return
     */
    public TimeFrequency create(String name);

    /**
     * Factory method to create a timeFrequency and assign all required and optional fields. The object identifier is generated and assigned by the factory.
     *
     * @param name        data format name, required
     * @param description the description, optional
     * @return
     */
    public TimeFrequency create(String name, String description);

}
