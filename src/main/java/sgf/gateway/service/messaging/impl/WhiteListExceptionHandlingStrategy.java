package sgf.gateway.service.messaging.impl;

import sgf.gateway.service.messaging.UnhandledException;

import java.util.Set;

public class WhiteListExceptionHandlingStrategy implements ExceptionHandlingStrategy {

    private final ExceptionHandlingStrategy exceptionHandlingStrategy;

    private final Set<Class> whiteListExceptions;


    public WhiteListExceptionHandlingStrategy(final ExceptionHandlingStrategy exceptionHandlingStrategy, final Set<Class> whiteListExceptions) {

        this.exceptionHandlingStrategy = exceptionHandlingStrategy;
        this.whiteListExceptions = whiteListExceptions;
    }


    @Override
    public void handleException(UnhandledException exception) {

        boolean whiteListed = this.isWhiteListException(exception);

        if (!whiteListed) {

            this.exceptionHandlingStrategy.handleException(exception);
        }
    }

    /**
     * Checks if is white list exception. Only checks to see if the Throwables Class is contained in the whiteListException Set.
     *
     * @param throwable the throwable
     * @return true, if is white list exception
     */
    boolean isWhiteListException(Throwable throwable) {

        boolean result = false;

        if (throwable != null) {

            if (this.whiteListExceptions.contains(throwable.getClass())) {

                result = true;

            } else {

                result = this.isWhiteListException(throwable.getCause());
            }
        }

        return result;
    }
}
