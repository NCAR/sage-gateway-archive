package sgf.gateway.service.messaging.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

import java.util.Date;

/**
 * The Class ErrorReportingServiceImpl.
 */
public class ExceptionHandlingServiceImpl implements ExceptionHandlingService {

    /**
     * Logger for this class.
     */
    private static final Log LOG = LogFactory.getLog(ExceptionHandlingServiceImpl.class);

    private ExceptionHandlingStrategy exceptionHandlingStrategy;

    public ExceptionHandlingServiceImpl(ExceptionHandlingStrategy exceptionHandlingStrategy) {
        super();
        this.exceptionHandlingStrategy = exceptionHandlingStrategy;
    }

    /**
     * {@inheritDoc}
     */
    public void handledException(UnhandledException exception) {

        try {

            this.doHandleException(exception);

        } catch (Exception handlingException) {

            LOG.error("An exception occurred inside an ExceptionHandler.", handlingException);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void handleUnexpectedException(UnhandledException exception) {

        try {

            this.doHandleException(exception);

        } catch (Exception handlingException) {

            LOG.error("An exception occurred inside an ExceptionHandler.", handlingException);
        }
    }

    void doHandleException(UnhandledException exception) {

        exception.put("Time", (new Date()).toString());
        this.exceptionHandlingStrategy.handleException(exception);
    }

}
