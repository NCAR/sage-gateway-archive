package sgf.gateway.service.security;

import org.safehaus.uuid.UUID;

import java.util.Collection;

public interface ExistingGroupDataRequest {

    Collection<UUID> getExistingGroupDataIdentifiers();
}
