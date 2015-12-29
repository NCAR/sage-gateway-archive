package sgf.gateway.integration.publishing;

import org.springframework.integration.Message;
import sgf.gateway.integration.message.ChainDeadLetterHandler;
import sgf.gateway.integration.message.DeadLetterHandler;
import sgf.gateway.publishing.thredds.ThreddsDataServer;
import sgf.gateway.publishing.thredds.ThreddsDatasetDetails;
import sgf.gateway.publishing.thredds.ThreddsPublishingMailService;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;

public class ThreddsPublishingFailureDeadLetterHandler extends ChainDeadLetterHandler {

    private ThreddsPublishingMailService mailService;
    private ThreddsDataServer threddsDataServer;
    private ExceptionHandlingService exceptionHandlingService;

    public ThreddsPublishingFailureDeadLetterHandler(DeadLetterHandler nextHandler, ThreddsPublishingMailService mailService, ThreddsDataServer threddsDataServer,
                                                     ExceptionHandlingService exceptionHandlingService) {
        super(ThreddsDatasetDetails.class, nextHandler);
        this.mailService = mailService;
        this.threddsDataServer = threddsDataServer;
        this.exceptionHandlingService = exceptionHandlingService;
    }

    @Override
    protected void handleMessage(Message message) {
        mailService.sendFailMessage((ThreddsDatasetDetails) message.getPayload());
        reproduceAndHandleThreddsDataServerException(message);
    }

    private void reproduceAndHandleThreddsDataServerException(Message message) {

        try {
            threddsDataServer.get(((ThreddsDatasetDetails) message.getPayload()).getAuthoritativeSourceURI());
            handleUnexpectedException(createCouldNotReproduceThreddsDataServerException(), message);
        } catch (Exception e) {
            handleUnexpectedException(e, message);
        }
    }

    private void handleUnexpectedException(Exception exception, Message<ThreddsDatasetDetails> failedMessage) {
        UnhandledException unhandledException = createUnhandledException(exception, failedMessage);
        exceptionHandlingService.handleUnexpectedException(unhandledException);
    }

    private UnhandledException createUnhandledException(Exception exception, Message failedMessage) {

        UnhandledException unhandledException = new UnhandledException(exception.getMessage(), exception);

        unhandledException.put("failedMessage", failedMessage.toString());
        unhandledException.put("payload", failedMessage.getPayload().toString());

        return unhandledException;
    }

    private CouldNotReproduceThreddsDataServerException createCouldNotReproduceThreddsDataServerException() {
        return new CouldNotReproduceThreddsDataServerException("Thredds Catalog URI successfully produced InvCatalog post arrival in dead letter queue");
    }
}

class CouldNotReproduceThreddsDataServerException extends RuntimeException {
    CouldNotReproduceThreddsDataServerException(String message) {
        super(message);
    }
}

