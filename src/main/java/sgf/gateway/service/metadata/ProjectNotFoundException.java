package sgf.gateway.service.metadata;

public class ProjectNotFoundException extends ObjectNotFoundException {

    private static final long serialVersionUID = 1L;

    public ProjectNotFoundException(Object identifier) {

        super(identifier);
    }

    public ProjectNotFoundException(String identifier) {

        super(identifier);
    }
}
