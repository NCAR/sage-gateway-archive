package sgf.gateway.integration.ade;

import org.springframework.context.ApplicationEvent;

public class ADEHarvestEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public ADEHarvestEvent(Object source) {
        super(source);
    }
}
