package sgf.gateway.service.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MetadataService {

    Dataset storeDataset(Dataset dataset);

    void deleteDataset(Dataset dataset);

    List<LogicalFile> findLogicalFileById(Collection<UUID> objectIds);

    /**
     * Method to retrieve ALL datasets to which a given user is authorized to executed a given operation, either because
     * of an explicitly assigned permission, or because of the user's membership in a group.
     */
    Set<Dataset> findDatasetsByOperation(User user, Operation operation);
}
