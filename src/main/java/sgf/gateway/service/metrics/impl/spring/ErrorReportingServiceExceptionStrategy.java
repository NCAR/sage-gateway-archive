package sgf.gateway.service.metrics.impl.spring;

import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.metrics.ExceptionStrategy;
import sgf.gateway.service.metrics.MetricsException;

/**
 * The Class ErrorReportingServiceExceptionStrategy.
 *
 * @author nhook
 */
public class ErrorReportingServiceExceptionStrategy implements ExceptionStrategy {

    /** The Constant logger. */
    // private static final Log LOGGER = LogFactory.getLog(ErrorReportingServiceExceptionStrategy.class);

    /**
     * The rethrow exception.
     */
    private boolean rethrowException = false;

    /**
     * The error reporting service.
     */
    private ExceptionHandlingService errorReportingService;

    /**
     * Instantiates a new error reporting service exception strategy.
     *
     * @param errorReportingService the error reporting service
     */
    public ErrorReportingServiceExceptionStrategy(ExceptionHandlingService errorReportingService) {

        this.errorReportingService = errorReportingService;
    }

    /**
     * Instantiates a new error reporting service exception strategy.
     *
     * @param errorReportingService the error reporting service
     * @param rethrowException      the rethrow exception
     */
    public ErrorReportingServiceExceptionStrategy(ExceptionHandlingService errorReportingService, boolean rethrowException) {

        this.errorReportingService = errorReportingService;
        this.rethrowException = rethrowException;
    }

    /**
     * {@inheritDoc}
     */
    public void handleException(Exception e) {

        this.errorReportingService.handledException(new UnhandledException(e));

        if (rethrowException) {

            throw new MetricsException(e);
        }
    }
}
