package sgf.gateway.service.messaging.impl.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.service.messaging.ExceptionHandler;
import sgf.gateway.service.messaging.UnhandledException;

/**
 * Log exceptions to the logging mechanism.
 */
public class LoggingExceptionHandler implements ExceptionHandler {

    private static final Log EXCEPTION_LOG = LogFactory.getLog("exception-log");
    private static final Log APPLICATION_LOG = LogFactory.getLog("");

    public void handleException(UnhandledException exception) {

        EXCEPTION_LOG.error("An exception occurred.", exception);
        APPLICATION_LOG.error("An exception occurred.", exception);

    }

}
