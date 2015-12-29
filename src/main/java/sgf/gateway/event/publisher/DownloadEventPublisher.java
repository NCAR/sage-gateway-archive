package sgf.gateway.event.publisher;

import sgf.gateway.event.DownloadEvent;

public interface DownloadEventPublisher {

    void publishDownloadEvent(DownloadEvent downloadEvent);
}
