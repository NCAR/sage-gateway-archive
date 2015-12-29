package sgf.gateway.service.publishing.api;

public class DatasetDoesntExistException extends PublishingException {

    private static final long serialVersionUID = 1L;

    public DatasetDoesntExistException() {

        super();
    }

    public DatasetDoesntExistException(final String s, final Throwable throwable) {

        super(s, throwable);
    }

    public DatasetDoesntExistException(final String s) {

        super(s);
    }

    public DatasetDoesntExistException(final Throwable throwable) {

        super(throwable);
    }

}
