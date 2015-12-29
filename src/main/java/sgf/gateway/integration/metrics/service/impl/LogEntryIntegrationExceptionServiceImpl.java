package sgf.gateway.integration.metrics.service.impl;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.service.ErrorLogService;
import sgf.gateway.integration.service.IntegrationExceptionService;

public class LogEntryIntegrationExceptionServiceImpl implements IntegrationExceptionService {

    private final ErrorLogService errorLogService;

    public LogEntryIntegrationExceptionServiceImpl(ErrorLogService errorLogService) {
        super();
        this.errorLogService = errorLogService;
    }

    @Override
    public void handleException(MessagingException exception) {

        Message<?> failedMessage = exception.getFailedMessage();
        Object payload = failedMessage.getPayload();

        String logEntry = ((FileDownloadPayload) payload).getLogEntry();

        errorLogService.error(logEntry);
    }
}
