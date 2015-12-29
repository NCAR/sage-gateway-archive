package sgf.gateway.event.mediator;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import sgf.gateway.event.DownloadEvent;
import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.model.metrics.UserAgent;
import sgf.gateway.model.metrics.factory.FileDownloadFactory;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metrics.MetricsDownloadService;
import sgf.gateway.service.metrics.UserAgentService;

public class DownloadMetricsMediator implements ApplicationListener {

    private final MetricsDownloadService metricsDownloadService;

    private final UserAgentService userAgentService;

    private final FileDownloadFactory metricsFactory;

    public DownloadMetricsMediator(MetricsDownloadService metricsDownloadService, UserAgentService userAgentService, FileDownloadFactory metricsFactory) {

        this.metricsDownloadService = metricsDownloadService;
        this.userAgentService = userAgentService;
        this.metricsFactory = metricsFactory;
    }

    /**
     * {@inheritDoc}
     */
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof DownloadEvent) {

            DownloadEvent downloadEvent = (DownloadEvent) event;

            UserAgent userAgent = this.userAgentService.getUserAgent(downloadEvent.getUserAgent());
            User user = downloadEvent.getUser();

            FileDownload fileDownload = this.metricsFactory.createFileDownload(user, downloadEvent.getLogicalFile(), userAgent, downloadEvent.getStartDate(), downloadEvent.getEndDate(), downloadEvent.getDuration(), downloadEvent.getCompleted(), downloadEvent.getRemoteAddress(), downloadEvent.getBytesSent(), downloadEvent.getRequestURI());

            this.metricsDownloadService.storeFileDownload(fileDownload);
        }
    }
}
