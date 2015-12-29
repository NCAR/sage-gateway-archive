package sgf.gateway.service.security.impl.spring;

import org.safehaus.uuid.UUID;
import org.springframework.security.access.AccessDeniedException;

/**
 * The Class InvalidAuthorizationTokenException, thrown when an token was not found.
 */
public class AuthorizationTokenNotFoundException extends AccessDeniedException {

    /**
     * Instantiates a new authorization token invalid exception.
     *
     * @param token the token
     */
    public AuthorizationTokenNotFoundException(UUID token) {

        super(buildExceptionMessage(token));
    }

    /**
     * Builds the exception message.
     *
     * @param token the token
     * @return the string
     */
    protected static String buildExceptionMessage(UUID token) {

        StringBuilder msg = new StringBuilder();

        msg.append("Token was not found, value: ");
        msg.append(token);

        return msg.toString();
    }

}
