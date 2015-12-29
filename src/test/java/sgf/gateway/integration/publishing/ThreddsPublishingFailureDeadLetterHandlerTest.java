package sgf.gateway.integration.publishing;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import sgf.gateway.integration.message.DeadLetterHandler;
import sgf.gateway.publishing.thredds.ThreddsDataServer;
import sgf.gateway.publishing.thredds.ThreddsDataServerException;
import sgf.gateway.publishing.thredds.ThreddsDatasetDetails;
import sgf.gateway.publishing.thredds.ThreddsPublishingMailService;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import thredds.catalog.InvDataset;

import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ThreddsPublishingFailureDeadLetterHandlerTest {

    private ThreddsPublishingMessageDummy messageDummy;
    private NextDeadLetterHandlerDummy nextDeadLetterHandler;
    private ThreddsPublishingMailServiceSpy mailService;
    private ExceptionHandlingServiceSpy exceptionHandlingService;

    @Before
    public void setup() {
        messageDummy = new ThreddsPublishingMessageDummy();
        nextDeadLetterHandler = new NextDeadLetterHandlerDummy();
        mailService = new ThreddsPublishingMailServiceSpy();
        exceptionHandlingService = new ExceptionHandlingServiceSpy();
    }

    @Test
    public void failMessageSent() {

        ThreddsPublishingFailureDeadLetterHandler threddsPublishingFailureDeadLetterHandler =
                new ThreddsPublishingFailureDeadLetterHandler(nextDeadLetterHandler, mailService, new InValidThreddsDataServer(), exceptionHandlingService);

        threddsPublishingFailureDeadLetterHandler.handleMessage(messageDummy);

        assertThat(mailService.isFailMessageSent(), is(true));
    }

    @Test
    public void ifThreddsDataServerException_ThreddsDataServerExceptionHandled() {

        ThreddsPublishingFailureDeadLetterHandler threddsPublishingFailureDeadLetterHandler =
                new ThreddsPublishingFailureDeadLetterHandler(nextDeadLetterHandler, mailService, new InValidThreddsDataServer(), exceptionHandlingService);

        threddsPublishingFailureDeadLetterHandler.handleMessage(messageDummy);

        assertThat(exceptionHandlingService.isHandleUnexpectedExceptionCalled(), is(true));
        assertThat(exceptionHandlingService.getCauseWithWhichHandleUnexpectedExceptionIsCalled() instanceof ThreddsDataServerException, is(true));
    }

    @Test
    public void ifNoThreddsDataServerException_CouldNotReproduceThreddsDataServerExceptionHandled() {

        ThreddsPublishingFailureDeadLetterHandler threddsPublishingFailureDeadLetterHandler =
                new ThreddsPublishingFailureDeadLetterHandler(nextDeadLetterHandler, mailService, new ValidThreddsDataServer(), exceptionHandlingService);

        threddsPublishingFailureDeadLetterHandler.handleMessage(messageDummy);

        assertThat(exceptionHandlingService.isHandleUnexpectedExceptionCalled(), is(true));
        assertThat(exceptionHandlingService.getCauseWithWhichHandleUnexpectedExceptionIsCalled() instanceof CouldNotReproduceThreddsDataServerException, is(true));
    }

    class NextDeadLetterHandlerDummy implements DeadLetterHandler {

        @Override
        public void handle(Message message) {

        }
    }

    class ThreddsPublishingMailServiceSpy implements ThreddsPublishingMailService {

        private Boolean failMessageSent = false;

        @Override
        public void sendFailMessage(ThreddsDatasetDetails details) {
            failMessageSent = true;
        }

        @Override
        public void sendSuccessMessage(ThreddsDatasetDetails details) {
        }

        public Boolean isFailMessageSent() {
            return failMessageSent;
        }
    }

    class ValidThreddsDataServer implements ThreddsDataServer {

        @Override
        public InvDataset get(URI authoritativeSourceURI) {
            return null;
        }
    }

    class InValidThreddsDataServer implements ThreddsDataServer {

        @Override
        public InvDataset get(URI authoritativeSourceURI) {
            throw new ThreddsDataServerException("invalid");
        }
    }

    class ExceptionHandlingServiceSpy implements ExceptionHandlingService {

        private Boolean called = false;
        private Throwable cause;

        @Override
        public void handledException(UnhandledException exception) {

        }

        @Override
        public void handleUnexpectedException(UnhandledException exception) {
            called = true;
            cause = exception.getCause();
        }

        public Boolean isHandleUnexpectedExceptionCalled() {
            return called;
        }

        public Throwable getCauseWithWhichHandleUnexpectedExceptionIsCalled() {
            return cause;
        }
    }

    class ThreddsPublishingMessageDummy implements Message<ThreddsDatasetDetails> {

        @Override
        public MessageHeaders getHeaders() {
            return null;
        }

        @Override
        public ThreddsDatasetDetails getPayload() {
            return new ThreddsDatasetDetails() {

                @Override
                public URI getAuthoritativeSourceURI() {
                    return null;
                }

                @Override
                public String getParentShortName() {
                    return null;
                }

                @Override
                public UUID getUserID() {
                    return null;
                }

                @Override
                public UUID getDatasetID() {
                    return null;
                }

                @Override
                public void setDatasetID(UUID datasetID) {

                }
            };
        }
    }
}
