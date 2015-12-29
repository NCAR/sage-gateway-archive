package sgf.gateway.service.publishing.api;

public class DatasetAlreadyExistsException extends PublishingException {

    private static final long serialVersionUID = 1L;

    public DatasetAlreadyExistsException() {

        super();
    }

    public DatasetAlreadyExistsException(final String s, final Throwable throwable) {

        super(s, throwable);
    }

    public DatasetAlreadyExistsException(final String s) {

        super(s);
    }

    public DatasetAlreadyExistsException(final Throwable throwable) {

        super(throwable);
    }

}
