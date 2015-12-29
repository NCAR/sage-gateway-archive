package sgf.gateway.service.security;

import org.safehaus.uuid.UUID;

import java.util.Map;

public interface GroupRegistrationRequest {

    UUID getUserIdentifier();

    UUID getGroupIdentifier();

    Map<UUID, String> getGroupData();
}
