package sgf.gateway.event;

import org.springframework.context.ApplicationEvent;

public class DatasetDeletedEvent extends ApplicationEvent {

    private String shortName;

    public DatasetDeletedEvent(Object source, String shortName) {
        super(source);
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
