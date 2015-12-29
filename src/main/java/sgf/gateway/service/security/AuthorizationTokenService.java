package sgf.gateway.service.security;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.security.AuthorizationToken;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.impl.spring.AuthorizationTokenExpiredException;
import sgf.gateway.service.security.impl.spring.AuthorizationTokenInvalidException;
import sgf.gateway.service.security.impl.spring.AuthorizationTokenNotFoundException;

import java.net.URI;

/**
 * The Interface AuthorizationTokenService.
 */
public interface AuthorizationTokenService {

    AuthorizationToken generateAuthorizationToken(URI uri, User user);

    AuthorizationToken validateToken(UUID tokenUUID, URI uri)
            throws AuthorizationTokenInvalidException, AuthorizationTokenExpiredException, AuthorizationTokenNotFoundException;

}
