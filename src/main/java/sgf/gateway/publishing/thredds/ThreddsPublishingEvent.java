package sgf.gateway.publishing.thredds;

import org.springframework.context.ApplicationEvent;

public class ThreddsPublishingEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private final ThreddsDatasetDetails details;

    public ThreddsPublishingEvent(Object source, ThreddsDatasetDetails details) {
        super(source);
        this.details = details;
    }

    public ThreddsDatasetDetails getThreddsDatasetDetails() {
        return details;
    }

}
