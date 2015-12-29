package sgf.gateway.model.metadata.factory;

import sgf.gateway.model.metadata.PhysicalDomain;

public interface PhysicalDomainFactory {

    /**
     * Factory method to create a PhysicalDomain and assign all required fields. The object identifier is generated and assigned by the factory.
     *
     * @param name data format name, required
     * @return
     */
    public PhysicalDomain create(String name);

}
