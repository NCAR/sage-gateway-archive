package sgf.gateway.service.publishing.api;

public class InvalidDatasetStateTransitionException extends PublishingException {

    private static final long serialVersionUID = 1L;

    public InvalidDatasetStateTransitionException() {

        super();
    }

    public InvalidDatasetStateTransitionException(final String s, final Throwable throwable) {

        super(s, throwable);
    }

    public InvalidDatasetStateTransitionException(final String s) {

        super(s);
    }

    public InvalidDatasetStateTransitionException(final Throwable throwable) {

        super(throwable);
    }

}
