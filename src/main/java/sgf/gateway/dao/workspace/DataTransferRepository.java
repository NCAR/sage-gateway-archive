package sgf.gateway.dao.workspace;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.Repository;
import sgf.gateway.model.security.User;
import sgf.gateway.model.workspace.DataTransferItem;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.model.workspace.DataTransferRequestStatus;

import java.io.Serializable;
import java.util.List;

public interface DataTransferRepository extends Repository<DataTransferRequest, Serializable> {

    /**
     * Returns a list of requests for this user and gateway ordered by startTime descending
     *
     * @param user the user
     * @return list of requests
     */
    List<DataTransferRequest> findDataTransferRequests(User user);

    /**
     * Returns a list of all requests for this gateway by status.
     *
     * @param status the status
     * @return list of requests
     */
    List<DataTransferRequest> findAllDataTransferRequestsByStatus(DataTransferRequestStatus status);

    /**
     * Stores request to database.
     *
     * @param request the request
     */
    void storeDataTransferRequest(DataTransferRequest request);

    /**
     * Get request by id.
     *
     * @param id the id
     * @return the transfer request
     */
    DataTransferRequest getDataTransferRequest(UUID id);

    DataTransferItem getDataTransferItem(UUID identifier);

    /**
     * Stores item to database.
     *
     * @param item the item
     */
    void storeDataTransferItem(DataTransferItem item);

    Integer getMaxRequestNumber(User user);
}
