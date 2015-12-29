package sgf.gateway.integration.publishing.service.impl;

import org.springframework.integration.MessagingException;
import sgf.gateway.integration.service.IntegrationExceptionService;
import sgf.gateway.publishing.thredds.ThreddsDataServerException;
import sgf.gateway.publishing.thredds.ThreddsPublishingException;

public class ThreddsPublishingFailureIntegrationExceptionServiceImpl implements IntegrationExceptionService {

    private IntegrationExceptionService exceptionService;

    public ThreddsPublishingFailureIntegrationExceptionServiceImpl(IntegrationExceptionService exceptionService) {
        this.exceptionService = exceptionService;
    }

    @Override
    public void handleException(MessagingException exception) {
        if (!isThreddsDataServerExceptionRootCause(exception)) {
            exceptionService.handleException(exception);
        }
    }

    private Boolean isThreddsDataServerExceptionRootCause(MessagingException exception) {

        if (isThreddsPublishingException(exception.getCause())) {
            if (isThreddsDataServerException(exception.getCause().getCause())) {
                return true;
            }
        }

        return false;
    }

    private Boolean isThreddsDataServerException(Throwable throwable) {
        return throwable instanceof ThreddsDataServerException;
    }

    private Boolean isThreddsPublishingException(Throwable throwable) {
        return throwable instanceof ThreddsPublishingException;
    }
}
