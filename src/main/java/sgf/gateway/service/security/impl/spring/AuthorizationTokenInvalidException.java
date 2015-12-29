package sgf.gateway.service.security.impl.spring;

import org.safehaus.uuid.UUID;
import org.springframework.security.access.AccessDeniedException;

import java.net.URI;

/**
 * The Class InvalidAuthorizationTokenException, thrown when an token was found but did not match the URI it was created for..
 */
public class AuthorizationTokenInvalidException extends AccessDeniedException {

    /**
     * Instantiates a new authorization token invalid exception.
     *
     * @param token the token
     * @param uri   the uri
     */
    public AuthorizationTokenInvalidException(UUID token, URI uri) {

        super(buildExceptionMessage(token, uri));
    }

    /**
     * Builds the exception message.
     *
     * @param token the token
     * @param uri   the uri
     * @return the string
     */
    protected static String buildExceptionMessage(UUID token, URI uri) {

        StringBuilder msg = new StringBuilder();

        msg.append("Token was not found, value: ");
        msg.append(token);
        msg.append(" for URI: ");
        msg.append(uri.toString());

        return msg.toString();
    }

}
