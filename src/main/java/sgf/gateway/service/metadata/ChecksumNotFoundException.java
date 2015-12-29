package sgf.gateway.service.metadata;

public class ChecksumNotFoundException extends ObjectNotFoundException {

    public ChecksumNotFoundException(final Object identifier) {

        super(identifier);
    }

    public ChecksumNotFoundException(final String identifier) {

        super(identifier);
    }
}
