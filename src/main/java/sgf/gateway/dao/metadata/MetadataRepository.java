package sgf.gateway.dao.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.GatewayImpl;
import sgf.gateway.model.metadata.*;
import sgf.gateway.model.metadata.activities.modeling.ExperimentImpl;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface MetadataRepository {

    Object getObject(Class clazz, UUID id);

    Object getObject(String clazz, UUID id);

    <T> List<T> findObjectsByNamedQuery(String namedQuery);

    void storeDataset(Dataset dataset);

    TimeFrequency getTimeFrequency(Serializable identifier);

    List<TimeFrequency> findTimeFrequencies();

    DataFormat getDataFormat(Serializable identifer);

    List<DataFormat> findDataFormats();

    DataProductType findDataProductTypeByName(String name);

    List<GatewayImpl> findGateways();

    List<ExperimentImpl> findExperiments();

    List<LogicalFile> findLogicalFileById(Collection<UUID> objectIds);

    /**
     * Method to retrieve all datasets to which a user has been explicitly authorized to execute a given operation (i.e., not through the user membership in a
     * group).
     */
    List<Dataset> findDatasetsByUserAndOperation(User user, Operation operation);

    /**
     * Method to retrieve all datasets to which a user is authorized to execute a give operation because of his/her membership in a group.
     */
    List<Dataset> findDatasetsByGroupUserAndOperation(User user, Operation operation);

    MetadataProfile findMetadataProfileByName(String name);

    ScienceKeyword getScienceKeyword(UUID identifer);

    List<ScienceKeyword> getScienceKeywords();

    ScienceKeyword findScienceKeywordByDisplayText(String displayText);

    List<ScienceKeyword> getScienceKeywordTopics();

    List<ScienceKeyword> getUsedScienceKeywordTopics();
}
