package sgf.gateway.service.whois;

import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

public class WhoIsQuartzJob {

    private boolean enabled;
    private WhoIsService service;
    private ExceptionHandlingService exceptionHandlingService;

    public WhoIsQuartzJob(boolean enabled, WhoIsService service, ExceptionHandlingService exceptionHandlingService) {

        this.enabled = enabled;
        this.service = service;
        this.exceptionHandlingService = exceptionHandlingService;
    }

    public void execute() {

        if (this.enabled) {

            try {

                service.updateWhoIsOfLatestFileDownloads();

            } catch (Exception e) {

                this.exceptionHandlingService.handleUnexpectedException(new UnhandledException(e));
            }
        }
    }
}
