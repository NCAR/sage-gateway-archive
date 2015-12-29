package sgf.gateway.service.geocode.impl;

import sgf.gateway.dao.metrics.MetricsDownloadDAO;
import sgf.gateway.model.metrics.GeoCode;
import sgf.gateway.service.geocode.RemoteGeoCodeFacade;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

public class ExceptionHandlingGeoCodeService extends GeoCodeServiceImpl {

    private ExceptionHandlingService exceptionService;

    public ExceptionHandlingGeoCodeService(MetricsDownloadDAO dao, RemoteGeoCodeFacade facade,
                                           ExceptionHandlingService exceptionService) {
        super(dao, facade);
        this.exceptionService = exceptionService;
    }

    @Override
    protected GeoCode getGeoCode(String latestIpAddress) {

        try {
            return super.getGeoCode(latestIpAddress);
        } catch (Exception e) {
            handleException(latestIpAddress, e);
            return new GeoCodeNullAttributes();
        }
    }

    private void handleException(String latestIpAddress, Exception e) {

        UnhandledException unhandledException = new UnhandledException(e.getMessage(), e);
        unhandledException.put("ipAddress", latestIpAddress);

        exceptionService.handleUnexpectedException(unhandledException);
    }

    private class GeoCodeNullAttributes implements GeoCode {

        @Override
        public Double getLatitude() {
            return null;
        }

        @Override
        public Double getLongitude() {
            return null;
        }

        @Override
        public String getCountry() {
            return null;
        }

        @Override
        public String getCountryCode() {
            return null;
        }

        @Override
        public String getCity() {
            return null;
        }

        @Override
        public String getState() {
            return null;
        }
    }
}
