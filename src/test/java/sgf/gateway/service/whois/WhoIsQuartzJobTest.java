package sgf.gateway.service.whois;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WhoIsQuartzJobTest {

    private WhoIsServiceSpy whoIsServiceSpy;

    @Before
    public void setup() {

        this.whoIsServiceSpy = new WhoIsServiceSpy();
    }


    @Test
    public void jobEnabledAndServiceCalled() {

        WhoIsQuartzJob job = new WhoIsQuartzJob(true, this.whoIsServiceSpy, null);

        job.execute();

        assertThat(true, is(whoIsServiceSpy.getUpdatedCalled()));
    }

    @Test
    public void jobNotEnabledAndServiceNotCalled() {

        WhoIsQuartzJob job = new WhoIsQuartzJob(false, this.whoIsServiceSpy, null);

        job.execute();

        assertThat(false, is(whoIsServiceSpy.getUpdatedCalled()));
    }

    public class WhoIsServiceSpy implements WhoIsService {

        private boolean updatedCalled = false;

        public void updateWhoIsOfLatestFileDownloads() {

            this.updatedCalled = true;
        }

        public boolean getUpdatedCalled() {

            return this.updatedCalled;
        }
    }

    @Test
    public void exceptionServiceCalledOnException() {

        ExceptionHandlingServiceSpy exceptionHandlingServiceSpy = new ExceptionHandlingServiceSpy();

        WhoIsQuartzJob job = new WhoIsQuartzJob(true, new WhoIsServiceExceptionThrowingStub(), exceptionHandlingServiceSpy);

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

    public class WhoIsServiceExceptionThrowingStub implements WhoIsService {

        public void updateWhoIsOfLatestFileDownloads() {

            throw new WhoIsQuartzJobTextException();
        }
    }

    public class WhoIsQuartzJobTextException extends RuntimeException {

    }
}
