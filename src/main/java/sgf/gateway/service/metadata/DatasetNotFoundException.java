package sgf.gateway.service.metadata;

public class DatasetNotFoundException extends ObjectNotFoundException {

    private static final long serialVersionUID = 1L;

    public DatasetNotFoundException(final Object identifier) {

        super(identifier);
    }

    public DatasetNotFoundException(final String identifier) {

        super(identifier);
    }
}
