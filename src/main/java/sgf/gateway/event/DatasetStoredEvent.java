package sgf.gateway.event;

import org.springframework.context.ApplicationEvent;

public class DatasetStoredEvent extends ApplicationEvent {

    private String shortName;

    public DatasetStoredEvent(Object source, String shortName) {
        super(source);
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

}
