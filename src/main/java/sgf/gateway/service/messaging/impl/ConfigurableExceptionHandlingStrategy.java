package sgf.gateway.service.messaging.impl;

import sgf.gateway.service.messaging.ExceptionHandler;
import sgf.gateway.service.messaging.UnhandledException;

import java.util.List;

class ConfigurableExceptionHandlingStrategy implements ExceptionHandlingStrategy {

    private List<ExceptionHandler> exceptionHandlers;

    public ConfigurableExceptionHandlingStrategy(List<ExceptionHandler> exceptionHandlers) {
        super();
        this.exceptionHandlers = exceptionHandlers;
    }

    public void handleException(UnhandledException exception) {
        for (ExceptionHandler handler : this.exceptionHandlers) {

            // TODO - Think about a flag to terminate processing.
            handler.handleException(exception);
        }

    }

}
