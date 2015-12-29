package sgf.gateway.service.geocode;

import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

public class GeoCodeQuartzJob {

    private boolean enabled;
    private GeoCodeService service;
    private ExceptionHandlingService exceptionHandlingService;

    public GeoCodeQuartzJob(boolean enabled, GeoCodeService service, ExceptionHandlingService exceptionHandlingService) {
        this.enabled = enabled;
        this.service = service;
        this.exceptionHandlingService = exceptionHandlingService;
    }

    public void execute() {

        if (this.enabled) {

            try {

                service.updateGeoCodeOfLatestFileDownloads();

            } catch (Exception e) {

                this.exceptionHandlingService.handleUnexpectedException(new UnhandledException(e));
            }
        }
    }
}
