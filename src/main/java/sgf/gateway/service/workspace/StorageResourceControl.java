package sgf.gateway.service.workspace;

import sgf.gateway.model.workspace.DataTransferRequest;


public interface StorageResourceControl {

    /**
     * Sends request and underlying items to storage resource system
     *
     * @param request
     * @return true if request successfully made
     */
    boolean submit(DataTransferRequest request);

    /**
     * Updates existing/submitted request and underlying items from storage resource system
     *
     * @param request
     * @return true if request successfully made
     */
    boolean update(DataTransferRequest request);

    /**
     * Releases existing/submitted request resources (typically cached files) from storage resource system
     *
     * @param request
     * @return true if request successfully made
     */
    boolean release(DataTransferRequest request);

    /**
     * Cancels existing/submitted request from storage resource system
     *
     * @param request
     * @return true if request successfully made
     */
    boolean cancel(DataTransferRequest request);

    /**
     * Adds the specified storage event listener to receive storage events from this control
     *
     * @param listener the storage event listener
     */
    void addStorageEventListener(StorageEventListener listener);

    /**
     * Removes the specified storage event listener to receive storage events from this control
     *
     * @param listener the storage event listener
     */
    void removeStorageEventListener(StorageEventListener listener);
}
