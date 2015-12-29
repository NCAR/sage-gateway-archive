package sgf.gateway.service.security.impl.spring;

/**
 * The Class AuthorizationException.
 */
public class AuthorizationException extends RuntimeException {

    /**
     * Instantiates a new authorization exception.
     *
     * @param message the message
     */
    public AuthorizationException(String message) {

        super(message);
    }

}
