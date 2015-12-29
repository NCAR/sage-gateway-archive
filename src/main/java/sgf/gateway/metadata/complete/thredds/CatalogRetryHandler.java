package sgf.gateway.metadata.complete.thredds;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpMethod;

import java.io.IOException;
import java.io.InterruptedIOException;

public class CatalogRetryHandler extends DefaultHttpMethodRetryHandler {

    public CatalogRetryHandler() {
        super();
    }

    public CatalogRetryHandler(int retryCount, boolean requestSentRetryEnabled) {
        super(retryCount, requestSentRetryEnabled);
    }

    @Override
    public boolean retryMethod(HttpMethod method, IOException exception,
                               int executionCount) {

        if (executionCount > this.getRetryCount()) {
            // Do not retry if over max retry count
            return false;
        }

        if (exception instanceof InterruptedIOException) {
            // Timeout
            return true;
        }

        return super.retryMethod(method, exception, executionCount);
    }

}
