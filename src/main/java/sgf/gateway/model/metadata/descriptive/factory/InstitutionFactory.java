package sgf.gateway.model.metadata.descriptive.factory;

import sgf.gateway.model.metadata.descriptive.Institution;

public interface InstitutionFactory {

    /**
     * Factory method to create an Institution and assign all required fields. The object identifier is generated and assigned by the factory.
     *
     * @param name Institution name, required
     * @return the Institution
     */
    public Institution create(String name);

}
