package sgf.gateway.service.metadata;

public class SimulationNotFoundException extends ObjectNotFoundException {

    private static final long serialVersionUID = 1L;

    public SimulationNotFoundException(final String identifier) {

        super(identifier);
    }
}