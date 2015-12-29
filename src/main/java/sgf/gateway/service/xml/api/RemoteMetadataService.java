package sgf.gateway.service.xml.api;

/**
 * Interface that specifies the API that remote clients can use to query the metadata content of the relational database. The remote API is a subset of the full
 * metadata access functionality available to local clients. All methods return XML serialized as a String.
 */
public interface RemoteMetadataService {

    /**
     * Method to lookup a Dataset hierarchy given its persistent identifier.
     *
     * @param identifier : the persistent identifier of type ID.
     * @return : the Dataset hierarchy serialized as ESG XML.
     * @throws Exception
     */
    String getDatasetHierarchy(String identifier) throws Exception;

    /**
     * Method to lookup a Dataset detailed metadata given its persistent identifier.
     *
     * @param identifier : the persistent identifier of type ID.
     * @return : the Dataset metadata serialized as ESG XML.
     * @throws Exception
     */
    String getDatasetMetadata(String identifier) throws Exception;

    /**
     * Method to return the complete files content of a Dataset, given its persistent identifier.
     *
     * @param identifier : the persistent identifier of type ID.
     * @return : the Dataset files content serialized as XML.
     * @throws Exception
     */
    String getDatasetFiles(String identifier) throws Exception;

    /**
     * Method to return a list of Experiments.
     *
     * @return : a list of Experiments serialized as XML.
     * @throws Exception
     */
    String listExperiments() throws Exception;
}
