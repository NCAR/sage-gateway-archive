package sgf.gateway.service.security;

import org.safehaus.uuid.UUID;

public interface ChangeEmailRequest {

    UUID getUserId();

    String getEmail();
}
