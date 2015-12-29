package sgf.gateway.service.whois.impl;

import sgf.gateway.dao.metrics.MetricsDownloadDAO;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.whois.RemoteWhoIsFacade;

public class ExceptionHandlingWhoIsServiceImpl extends WhoIsServiceImpl {

    private ExceptionHandlingService exceptionHandlingService;


    public ExceptionHandlingWhoIsServiceImpl(MetricsDownloadDAO dao, RemoteWhoIsFacade facade, ExceptionHandlingService exceptionHandlingService) {

        super(dao, facade);
        this.exceptionHandlingService = exceptionHandlingService;
    }

    public String getNameByIpAddress(String ipAddress) {

        String name = null;

        try {

            name = super.getNameByIpAddress(ipAddress);

        } catch (Exception e) {

            UnhandledException unhandledException = new UnhandledException(e);
            unhandledException.put("IP Address", ipAddress);

            this.exceptionHandlingService.handleUnexpectedException(unhandledException);
        }

        return name;
    }
}
