package sgf.gateway.integration.publishing.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.MessagingException;
import sgf.gateway.integration.service.IntegrationExceptionService;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import sgf.gateway.publishing.thredds.ThreddsDataServerException;
import sgf.gateway.publishing.thredds.ThreddsPublishingException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ThreddsPublishingFailureIntegrationExceptionServiceImplTest {

    private ThreddsDatasetPayload payload;
    private IntegrationExceptionServiceSpy integrationExceptionService;
    private ThreddsPublishingFailureIntegrationExceptionServiceImpl exceptionService;

    @Before
    public void setup() {
        payload = new ThreddsDatasetPayload();
        integrationExceptionService = new IntegrationExceptionServiceSpy();
        exceptionService = new ThreddsPublishingFailureIntegrationExceptionServiceImpl(integrationExceptionService);
    }

    @Test
    public void ifFirstCauseNotThreddsPublishingException_Report() {

        MessagingException messagingException = new MessagingException(new DummyPublishingMessage(), new RuntimeException());

        exceptionService.handleException(messagingException);

        assertThat(integrationExceptionService.isCalled(), is(true));
    }

    @Test
    public void ifFirstCauseThreddsPublishingExceptionSecondCauseNotThreddsDataServerException_Report() {

        MessagingException messagingException = new MessagingException(new DummyPublishingMessage(), new ThreddsPublishingException(new RuntimeException()));

        exceptionService.handleException(messagingException);

        assertThat(integrationExceptionService.isCalled(), is(true));
    }

    @Test
    public void ifFirstCauseThreddsPublishingExceptionSecondCauseThreddsDataServerException_DoNotReport() {

        MessagingException messagingException = new MessagingException(new DummyPublishingMessage(), new ThreddsPublishingException(new ThreddsDataServerException("test")));

        exceptionService.handleException(messagingException);

        assertThat(integrationExceptionService.isCalled(), is(false));
    }

    class DummyPublishingMessage implements Message<ThreddsDatasetPayload> {

        @Override
        public MessageHeaders getHeaders() {
            return null;
        }

        @Override
        public ThreddsDatasetPayload getPayload() {
            return null;
        }
    }

    class IntegrationExceptionServiceSpy implements IntegrationExceptionService {

        private Boolean called = false;

        @Override
        public void handleException(MessagingException exception) {
            called = true;
        }

        public Boolean isCalled() {
            return called;
        }
    }
}
