package sgf.gateway.integration.service.impl;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import sgf.gateway.integration.service.IntegrationExceptionService;

public class ChainIntegrationExceptionServiceImpl implements IntegrationExceptionService {

    private final Class<?> supportedClass;
    private final IntegrationExceptionService service;
    private final IntegrationExceptionService nextService;

    public ChainIntegrationExceptionServiceImpl(Class<?> supportedClass, IntegrationExceptionService service, IntegrationExceptionService nextService) {
        super();
        this.supportedClass = supportedClass;
        this.service = service;
        this.nextService = nextService;
    }

    @Override
    public void handleException(MessagingException exception) {

        Message<?> failedMessage = exception.getFailedMessage();
        Object payload = failedMessage.getPayload();

        if (supports(payload)) {
            handleExceptionSupported(exception);
        } else {
            nextService.handleException(exception);
        }
    }

    protected void handleExceptionSupported(MessagingException exception) {
        service.handleException(exception);
    }

    protected Boolean supports(Object payload) {

        Boolean supported = false;
        Class<?> clazz = payload.getClass();

        if (this.supportedClass.isAssignableFrom(clazz)) {
            supported = true;
        }

        return supported;
    }
}