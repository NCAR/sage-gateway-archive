package sgf.gateway.model.metadata.factory;

import sgf.gateway.model.metadata.DataProductType;

public interface DataProductTypeFactory {

    /**
     * Factory method to create a DataProductType and assign all required fields. The object identifier is generated and assigned by the factory.
     *
     * @param name data format name, required
     * @return
     */
    public DataProductType create(String name);

    /**
     * Factory method to create a DataProductType and assign all required and optional fields. The object identifier is generated and assigned by the factory.
     *
     * @param name        data format name, required
     * @param description the description, optional
     * @return
     */
    public DataProductType create(String name, String description);

}
