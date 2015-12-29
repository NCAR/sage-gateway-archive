package sgf.gateway.model.metadata.factory;

import sgf.gateway.model.metadata.DataFormat;

public interface DataFormatFactory {

    /**
     * Factory method to create a dataFormat and assign all required fields. The object identifier is generated and assigned by the factory.
     *
     * @param name data format name, required
     * @return
     */
    public DataFormat create(String name);

    /**
     * Factory method to create a dataFormat and assign all required and optional fields. The object identifier is generated and assigned by the factory.
     *
     * @param name        data format name, required
     * @param description the description, optional
     * @return
     */
    public DataFormat create(String name, String description);

}
