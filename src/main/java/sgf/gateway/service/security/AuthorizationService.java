package sgf.gateway.service.security;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;

/**
 * Interface that defines the authorization API.
 */
public interface AuthorizationService {

    /**
     * Method to check authorization for a user to execute an operation on a dataset.
     *
     * @param user
     * @param dataset
     * @param operation
     * @return true for positive authorization, false otherwise
     */
    boolean authorize(User user, Dataset dataset, Operation operation);

}
