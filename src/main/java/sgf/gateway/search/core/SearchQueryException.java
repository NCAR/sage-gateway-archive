package sgf.gateway.search.core;

public class SearchQueryException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SearchQueryException() {
        super();
    }

    public SearchQueryException(final String message) {
        super(message);
    }

    public SearchQueryException(final Throwable cause) {
        super(cause);
    }

    public SearchQueryException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
