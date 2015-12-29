package sgf.gateway.service.workspace;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.dataaccess.FileAccessPointImpl;
import sgf.gateway.model.security.User;
import sgf.gateway.model.workspace.DataTransferItem;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.model.workspace.DataTransferRequestStatus;

import java.util.Collection;
import java.util.List;

/**
 * Interface that defines the DataTransferService API. Provides methods for creating data transfer requests, getting requests from persistence, monitoring
 * status and operating upon requests and request items.
 */
public interface DataTransferService {

    /**
     * Returns a list of File Access Points for a request.
     */
    List<FileAccessPointImpl> getFileAccessPointList(Collection<UUID> objectIds);

    /**
     * Returns a list of requests for a user.
     *
     * @param user
     * @return the requests
     */
    List<DataTransferRequest> getRequests(User user);

    /**
     * Retrieves a transfer request
     *
     * @param user
     * @param requestId
     * @return request
     */
    DataTransferRequest getRequest(User user, UUID requestId);

    /**
     * Retrieves all transfer requests matching a given status.
     *
     * @param status the status of the entire transfer request.
     * @return all transfer requests per gateway matching the given status.
     */
    List<DataTransferRequest> getAllRequestsByStatus(DataTransferRequestStatus status);

    /**
     * Creates requests for the list of files for this user.
     *
     * @param user  the user making the request
     * @param files list of file access points to be submitted in the request
     * @return the requests generated
     */
    List<DataTransferRequest> submit(User user, List<FileAccessPointImpl> files);

    /**
     * Cancel request, abort transferring and cleanup resources
     *
     * @param user
     * @param id
     */
    void cancelRequest(User user, UUID id);


    /**
     * Saves request to database
     *
     * @param request
     * @param user
     */
    void store(User user, DataTransferRequest request);

    /**
     * Saves request item to database
     *
     * @param user
     * @param item
     */
    void store(User user, DataTransferItem item);

    /**
     * Runs the command in a transaction
     *
     * @param command
     */
    void executeCommand(Runnable command);

    /**
     * Creates a temporary file access point for this item and capability
     *
     * @param transferItem   the transfer item
     * @param capabilityName the data access capability name
     * @param path           the path to the resource
     */
    void createFileAccessPoint(DataTransferItem transferItem, String capabilityName, String path);

    void sendRequestNotification(DataTransferRequest request);
}
