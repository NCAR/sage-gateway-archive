package sgf.gateway.service.security;

import sgf.gateway.model.metadata.Dataset;

/**
 * Utility interface that is simply used to trigger access control enforcement via AOP interception.
 */
public interface AuthorizationUtils {

    void authorizeForRead(Dataset dataset);

    void authorizeForWrite(Dataset dataset);
}
