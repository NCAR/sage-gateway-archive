package sgf.gateway.dao.security.impl.hibernate;

/**
 * Base class for custom User exceptions.
 */
public class UserException extends RuntimeException {

    /**
     * Instantiates a new User exception with a message.
     *
     * @param message the message
     */
    public UserException(String message) {

        super(message);
    }

}
