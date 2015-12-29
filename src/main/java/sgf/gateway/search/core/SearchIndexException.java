package sgf.gateway.search.core;

/**
 * This exception class is thrown by the Search subsystem when a problem is encountered.
 */
public class SearchIndexException extends RuntimeException {

    /**
     * Instantiates a new exception.
     *
     * @param message the message
     */
    public SearchIndexException(String message) {

        super("Search indexing exception:\n" + message);
    }

    /**
     * Instantiates a new exception.
     *
     * @param cause the cause
     */
    public SearchIndexException(Throwable cause) {

        super(cause);
    }
}
