package sgf.gateway.service.messaging;

/**
 * The Interface ErrorReportingService.
 */
public interface ExceptionHandlingService {

    /**
     * Report exception.
     *
     * @param exception the exception
     */
    void handledException(UnhandledException exception);

    /**
     * Report exception.
     *
     * @param exception the exception
     */
    void handleUnexpectedException(UnhandledException exception);

}
