package sgf.gateway.service.publishing.api;

/**
 * Remote publishing service API. This class is wrapper API to the publication API to enable remote invocation (for example via web services).
 */
public interface RemotePublishingService {

    /**
     * Publish a dataset hierarchy contained in a THREDDS catalog(s). The string returned by this method is the Dataset's short name.
     * The new dataset hierarchy is always created in the pre-published state.
     * Method is ASYNCHRONOUS (does not block).
     * <p/>
     * NOTE: currently works for publishing datasets contained in a single catalog (i.e. no recursion). It will throw an exception if the dataset already
     * exists.
     *
     * @param persistentIdentifier unique identifier of the parent dataset.
     * @param catalogURI           THREDDS catalog URI string
     * @param recurseLevel         The number of catalog references to be traversed. To traverse all references, use the value -1.
     * @param initialState         The initial state for the new datasets, either "Pre-Published" or "Published".
     * @return An operation handle that can be used in subsequent status and cancellation calls.
     * @throws DatasetDoesntExistException   if the parent dataset does not exist
     * @throws DatasetAlreadyExistsException if the catalog top-level dataset, or any of its nested datasets, exist already
     */
    String createDataset(String persistentIdentifier, String catalogURI, Integer recurseLevel, String initialState) throws DatasetDoesntExistException, DatasetAlreadyExistsException;

    /**
     * Transition an existing dataset to the retracted state. The dataset must currently be in the published state - any other state will cause an exception to
     * be thrown. Note that the state change will propagate down to lower levels. Method is SYNCHRONOUS (blocks until execution ends).
     * <p/>
     * NOTE: currently works
     *
     * @param persistentIdentifier The Dataset Version Identifier
     * @param changeMessage        the change message
     * @throws InvalidDatasetStateTransitionException if an invalid state transition is attempted anywhere down the dataset hierarchy
     * @throws DatasetDoesntExistException
     */
    void retractDataset(String persistentIdentifier, String changeMessage) throws InvalidDatasetStateTransitionException, DatasetDoesntExistException;

    /**
     * Delete a dataset hierarchy - possible only if the dataset is in the pre-published state. Method is SYNCHRONOUS (blocks until execution ends).
     * <p/>
     * NOTE: currently works, but only in permanent mode
     *
     * @param persistentIdentifier The Dataset Version Identifier.
     * @param permanent            flag if the dataset should be permanently deleted.
     * @param changeMessage        the change message
     * @throws InvalidDatasetStateTransitionException if the dataset is in any state other than pre-published
     * @throws DatasetDoesntExistException
     */
    void deleteDataset(String persistentIdentifier, boolean permanent, String changeMessage) throws InvalidDatasetStateTransitionException, DatasetDoesntExistException;

    /**
     * Returns the current global status of a publishing operation, typically for invocation by a client which is waiting for the publishing operation to
     * complete.
     * <p/>
     * NOTE: currently works.
     *
     * @param operationHandle an operation handle returned from a previous asynchronous publishing operation.
     * @return The operation status: "SUCCESSFUL", "UNSUCCESSFUL", "PROCESSING", "UNKNOWN".
     */
    String getPublishingStatus(String operationHandle);

    /**
     * Returns the current detailed status of a publishing operation as an XML document containing the status of each publishing task, typically for invocation
     * by a client after the whole publishing operation has completed.
     * <p/>
     * NOTE: currently works, but the detailed message is a stub.
     *
     * @param operationHandle an operation handle returned from a previous asynchronous publishing operation.
     * @return The detailed operation status as a serialized XML document.
     */
    String getPublishingResult(String operationHandle);

}
