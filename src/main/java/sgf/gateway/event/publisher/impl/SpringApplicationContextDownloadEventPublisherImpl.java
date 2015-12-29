package sgf.gateway.event.publisher.impl;

import org.springframework.context.support.ApplicationObjectSupport;
import sgf.gateway.event.DownloadEvent;
import sgf.gateway.event.publisher.DownloadEventPublisher;

public class SpringApplicationContextDownloadEventPublisherImpl extends ApplicationObjectSupport implements DownloadEventPublisher {

    public SpringApplicationContextDownloadEventPublisherImpl() {

    }

    public void publishDownloadEvent(DownloadEvent downloadEvent) {

        this.getApplicationContext().publishEvent(downloadEvent);
    }
}
