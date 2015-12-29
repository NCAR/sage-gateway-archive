package sgf.gateway.service.workspace;


public interface StorageEventListener {

    /**
     * Invoked when a storage request is completed
     *
     * @param event
     */
    void requestCompleted(StorageEvent event);

    /**
     * Invoked when a storage request item is made available
     *
     * @param event
     */
    void itemExposed(StorageEvent event);

    /**
     * Invoked when a storage request item is expired and no longer available.
     *
     * @param event
     */
    void itemExpired(StorageEvent event);
}
