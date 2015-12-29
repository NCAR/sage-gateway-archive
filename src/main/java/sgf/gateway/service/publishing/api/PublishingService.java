package sgf.gateway.service.publishing.api;

import sgf.gateway.model.security.User;

/**
 * The PublishingService is the interface for getting data into the gateway domain model.
 */

public interface PublishingService {

    /**
     * Completely deletes the dataset identified by persistent identifier as well as all contained child datasets <b>USE
     * WITH CAUTION!!!!</b> This will permanently delete all strongly associated records from the system, including all
     * metrics records.
     *
     * @param identifier persistent identifier string to be deleted
     */
    void deleteDataset(User initiator, String identifier);

    /**
     * Retracts the dataset identified by persistent identifier as well as all contained child datasets
     *
     * @param identifier persistent identifier string to be deleted
     */
    void retractDataset(User initiator, String identifier);

}
