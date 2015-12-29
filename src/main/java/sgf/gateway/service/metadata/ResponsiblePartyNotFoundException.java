package sgf.gateway.service.metadata;

public class ResponsiblePartyNotFoundException extends ObjectNotFoundException {

    private static final long serialVersionUID = 1L;

    public ResponsiblePartyNotFoundException(final String identifier) {

        super(identifier);
    }
}
