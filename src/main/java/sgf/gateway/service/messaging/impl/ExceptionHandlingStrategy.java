package sgf.gateway.service.messaging.impl;

import sgf.gateway.service.messaging.UnhandledException;

interface ExceptionHandlingStrategy {

    void handleException(UnhandledException exception);
}
