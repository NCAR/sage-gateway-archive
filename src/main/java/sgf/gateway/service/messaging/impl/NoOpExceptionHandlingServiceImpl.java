package sgf.gateway.service.messaging.impl;

import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

public class NoOpExceptionHandlingServiceImpl implements ExceptionHandlingService {

    @Override
    public void handledException(UnhandledException exception) {

    }

    @Override
    public void handleUnexpectedException(UnhandledException exception) {

    }
}
