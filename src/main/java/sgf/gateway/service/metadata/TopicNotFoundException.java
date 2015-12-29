package sgf.gateway.service.metadata;

public class TopicNotFoundException extends ObjectNotFoundException {

    private static final long serialVersionUID = 1L;

    public TopicNotFoundException(final String identifier) {

        super(identifier);
    }
}
