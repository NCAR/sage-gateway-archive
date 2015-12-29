package sgf.gateway.service.security.impl.spring;

import org.springframework.security.access.AccessDeniedException;
import sgf.gateway.model.security.AuthorizationToken;

import java.util.Calendar;

/**
 * The Class ExpiredAuthorizationTokenException, thrown when an expired token was retrieved.
 */
public class AuthorizationTokenExpiredException extends AccessDeniedException {

    /**
     * Instantiates a new expired authorization token exception.
     *
     * @param tokenRef the token ref
     */
    public AuthorizationTokenExpiredException( AuthorizationToken tokenRef) {

        super(buildExceptionMessage(tokenRef));
    }

    /**
     * Builds the exception message.
     *
     * @param tokenRef the token ref
     * @return the string
     */
    protected static String buildExceptionMessage(AuthorizationToken tokenRef) {

        StringBuilder msg = new StringBuilder();

        msg.append("Token retrieved at: ");
        msg.append(Calendar.getInstance().getTime());
        msg.append(" expired at: ");
        msg.append(tokenRef.getExpires());

        return msg.toString();
    }

}
