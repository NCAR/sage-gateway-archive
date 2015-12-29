package sgf.gateway.integration.service.impl;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import sgf.gateway.integration.service.ErrorLogService;
import sgf.gateway.integration.service.IntegrationExceptionService;

public class PayloadIntegrationExceptionServiceImpl implements IntegrationExceptionService {

    private final ErrorLogService errorLogService;

    public PayloadIntegrationExceptionServiceImpl(ErrorLogService errorLogService) {
        super();
        this.errorLogService = errorLogService;
    }

    @Override
    public void handleException(MessagingException exception) {

        Message<?> failedMessage = exception.getFailedMessage();
        Object payload = failedMessage.getPayload();

        errorLogService.error(payload.toString());
    }
}
