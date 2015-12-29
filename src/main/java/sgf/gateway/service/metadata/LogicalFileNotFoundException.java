package sgf.gateway.service.metadata;

public class LogicalFileNotFoundException extends ObjectNotFoundException {

    public LogicalFileNotFoundException(final Object identifier) {

        super(identifier);
    }

    public LogicalFileNotFoundException(final String identifier) {

        super(identifier);
    }
}