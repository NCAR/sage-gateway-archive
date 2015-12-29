package sgf.gateway.service.geocode.impl;

import org.junit.Test;
import sgf.gateway.model.metrics.GeoCode;
import sgf.gateway.service.geocode.RemoteGeoCodeFacade;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExceptionHandlingGeoCodeServiceTest {

    @Test
    public void ifFacadeThrowsException_UpdateWithGeoCodeWithNullAttributes() {

        RemoteGeoCodeFacadeExceptionStub facade = new RemoteGeoCodeFacadeExceptionStub();
        MetricsDownloadDAOSpy dao = new UnfinishedMetricsDownloadDAOSpy();
        ExceptionHandlingServiceSpy exceptionService = new ExceptionHandlingServiceSpy();

        ExceptionHandlingGeoCodeService service = new ExceptionHandlingGeoCodeService(dao, facade, exceptionService);

        service.updateGeoCodeOfLatestFileDownloads();

        assertThat(dao.getIpAddressArgument(), is("128.117.11.135"));
        assertThat(dao.getGeoCodeArgument().getCountry(), nullValue());
        assertThat(dao.getGeoCodeArgument().getCity(), nullValue());
        assertThat(dao.getGeoCodeArgument().getCountryCode(), nullValue());
        assertThat(dao.getGeoCodeArgument().getLatitude(), nullValue());
        assertThat(dao.getGeoCodeArgument().getLongitude(), nullValue());
        assertThat(dao.getGeoCodeArgument().getState(), nullValue());
    }

    @Test
    public void ifFacadeThrowsException_ReportUnhandledException() {

        RemoteGeoCodeFacadeExceptionStub facade = new RemoteGeoCodeFacadeExceptionStub();
        MetricsDownloadDAOSpy dao = new UnfinishedMetricsDownloadDAOSpy();
        ExceptionHandlingServiceSpy exceptionService = new ExceptionHandlingServiceSpy();

        ExceptionHandlingGeoCodeService service = new ExceptionHandlingGeoCodeService(dao, facade, exceptionService);

        service.updateGeoCodeOfLatestFileDownloads();

        assertThat(exceptionService.isUnexpectedExceptionHandled(), is(true));
        assertThat(exceptionService.getUnhandledException().get("ipAddress"), is("128.117.11.135"));
    }
}

class ExceptionHandlingServiceSpy implements ExceptionHandlingService {

    private UnhandledException exception;
    private Boolean handled = false;

    public UnhandledException getUnhandledException() {
        return exception;
    }

    public Boolean isUnexpectedExceptionHandled() {
        return handled;
    }

    @Override
    public void handledException(UnhandledException exception) {

    }

    @Override
    public void handleUnexpectedException(UnhandledException exception) {
        this.exception = exception;
        this.handled = true;
    }
}

class RemoteGeoCodeFacadeExceptionStub implements RemoteGeoCodeFacade {

    @Override
    public GeoCode getForIpAddress(String ipAddress) {
        throw new RuntimeException();
    }
}
