package sgf.gateway.service.metrics;

/**
 * The Interface ExceptionStrategy.
 *
 * @author nhook
 */
public interface ExceptionStrategy {

    /**
     * The Constant SPRING_CONTEXT.
     */
    String SPRING_CONTEXT = "exceptionStrategy";

    /**
     * Handle exception.
     *
     * @param e the e
     */
    void handleException(Exception e);
}
