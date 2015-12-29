package sgf.gateway.integration.publishing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.MessagingException;
import sgf.gateway.integration.service.IntegrationExceptionService;

public class ThreddsPublishingFailureIntegrationExceptionServiceImplLogger implements IntegrationExceptionService {

    private IntegrationExceptionService integrationExceptionService;

    private final static Logger LOG = LoggerFactory.getLogger(ThreddsPublishingFailureIntegrationExceptionServiceImpl.class);

    public ThreddsPublishingFailureIntegrationExceptionServiceImplLogger(IntegrationExceptionService integrationExceptionService) {

        this.integrationExceptionService = integrationExceptionService;
    }

    @Override
    public void handleException(MessagingException exception) {

        if (LOG.isErrorEnabled()) {

            if (exception.getFailedMessage() != null) {
                LOG.error("Publishing Error, FailedMessage: " + exception.getFailedMessage(), exception);
            } else {
                LOG.error("Publishing Error", exception);
            }
        }

        this.integrationExceptionService.handleException(exception);
    }
}
