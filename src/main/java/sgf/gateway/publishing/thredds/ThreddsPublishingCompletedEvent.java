package sgf.gateway.publishing.thredds;

import org.springframework.context.ApplicationEvent;

public class ThreddsPublishingCompletedEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private final ThreddsPublishingResult threddsPublishingResult;

    public ThreddsPublishingCompletedEvent(Object source, ThreddsPublishingResult threddsPublishingResult) {
        super(source);
        this.threddsPublishingResult = threddsPublishingResult;
    }

    public ThreddsPublishingResult getThreddsPublishingResult() {
        return threddsPublishingResult;
    }

}
