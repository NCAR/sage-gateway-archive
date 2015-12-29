package sgf.gateway.service.metadata;

public class ContactNotFoundException extends ObjectNotFoundException {

    private static final long serialVersionUID = 1L;

    public ContactNotFoundException(final String identifier) {

        super(identifier);
    }
}
