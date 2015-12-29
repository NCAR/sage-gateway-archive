package sgf.gateway.integration.service.impl;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import sgf.gateway.integration.service.IntegrationExceptionService;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

public class EmailIntegrationExceptionServiceImpl implements IntegrationExceptionService {

    private final ExceptionHandlingService exceptionService;

    public EmailIntegrationExceptionServiceImpl(ExceptionHandlingService exceptionService) {
        super();
        this.exceptionService = exceptionService;
    }

    @Override
    public void handleException(MessagingException exception) {

        UnhandledException unhandledException = new UnhandledException(exception.getMessage(), exception);

        if (exception.getFailedMessage() != null) {
            addFailedMessageAttributes(unhandledException, exception.getFailedMessage());
        }

        exceptionService.handleUnexpectedException(unhandledException);
    }

    private void addFailedMessageAttributes(UnhandledException unhandledException, Message<?> failedMessage) {
        unhandledException.put("failedMessage", failedMessage.toString());
        unhandledException.put("payload", failedMessage.getPayload().toString());
    }
}
