package sgf.gateway.search.core;

public class SearchQuerySyntaxError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SearchQuerySyntaxError() {
        super();
    }

    public SearchQuerySyntaxError(final String message) {
        super(message);
    }
}
