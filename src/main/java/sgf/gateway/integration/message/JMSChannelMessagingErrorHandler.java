package sgf.gateway.integration.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.MessagingException;
import org.springframework.util.ErrorHandler;
import sgf.gateway.integration.service.IntegrationExceptionService;

public class JMSChannelMessagingErrorHandler implements ErrorHandler {

    private final static Logger LOG = LoggerFactory.getLogger(JMSChannelMessagingErrorHandler.class);

    private final IntegrationExceptionService exceptionService;

    public JMSChannelMessagingErrorHandler(IntegrationExceptionService exceptionService) {
        super();
        this.exceptionService = exceptionService;
    }

    @Override
    public void handleError(Throwable throwable) {

        try {

            MessagingException messagingException = (MessagingException) throwable;

            MessagingException cause = (MessagingException) messagingException.getCause();

            exceptionService.handleException(cause);
        } catch (Throwable t) {
            LOG.error("Could not handle messaging error", t);
        }
    }
}
