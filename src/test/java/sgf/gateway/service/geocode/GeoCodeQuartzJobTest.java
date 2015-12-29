package sgf.gateway.service.geocode;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GeoCodeQuartzJobTest {

    private GeoCodeServiceSpy geoCodeServiceSpy;

    @Before
    public void setup() {

        this.geoCodeServiceSpy = new GeoCodeServiceSpy();
    }


    @Test
    public void jobEnabledAndServiceCalled() {

        GeoCodeQuartzJob job = new GeoCodeQuartzJob(true, this.geoCodeServiceSpy, null);

        job.execute();

        assertThat(true, is(geoCodeServiceSpy.getUpdatedCalled()));
    }

    @Test
    public void jobNotEnabledAndServiceNotCalled() {

        GeoCodeQuartzJob job = new GeoCodeQuartzJob(false, this.geoCodeServiceSpy, null);

        job.execute();

        assertThat(false, is(geoCodeServiceSpy.getUpdatedCalled()));
    }

    public class GeoCodeServiceSpy implements GeoCodeService {

        private boolean updatedCalled = false;

        public void updateGeoCodeOfLatestFileDownloads() {

            this.updatedCalled = true;
        }

        public boolean getUpdatedCalled() {

            return this.updatedCalled;
        }
    }

    @Test
    public void exceptionServiceCalledOnException() {

        ExceptionHandlingServiceSpy exceptionHandlingServiceSpy = new ExceptionHandlingServiceSpy();

        GeoCodeQuartzJob job = new GeoCodeQuartzJob(true, new GeoCodeServiceExceptionThrowingStub(), exceptionHandlingServiceSpy);

        job.execute();

        assertThat(true, is(exceptionHandlingServiceSpy.getHandleUnexpectedExceptionCalled()));
    }

    public class ExceptionHandlingServiceSpy implements ExceptionHandlingService {

        private boolean handleUnexpectedExceptionCalled = false;

        public void handledException(UnhandledException exception) {

        }

        public void handleUnexpectedException(UnhandledException exception) {

            this.handleUnexpectedExceptionCalled = true;
        }

        public boolean getHandleUnexpectedExceptionCalled() {

            return this.handleUnexpectedExceptionCalled;
        }
    }

    public class GeoCodeServiceExceptionThrowingStub implements GeoCodeService {

        public void updateGeoCodeOfLatestFileDownloads() {

            throw new GeoCodeQuartzJobTextException();
        }
    }

    public class GeoCodeQuartzJobTextException extends RuntimeException {

    }

}
