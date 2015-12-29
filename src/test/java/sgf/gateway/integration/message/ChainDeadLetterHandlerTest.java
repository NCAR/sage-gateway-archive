package sgf.gateway.integration.message;

import org.junit.Before;
import org.junit.Test;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ChainDeadLetterHandlerTest {

    DeadLetterHandlerSpy nextHandler;
    ChainDeadLetterHandlerSpy chainHandler;

    @Before
    public void setup() {
        nextHandler = new DeadLetterHandlerSpy();
        chainHandler = new ChainDeadLetterHandlerSpy(SupportedPayloadDummy.class, nextHandler);
    }

    @Test
    public void ifNotSupportedType_DefersDeadLetter() {

        MessageStub<NonSupportedPayloadDummy> message = new MessageStub<NonSupportedPayloadDummy>(new NonSupportedPayloadDummy());

        chainHandler.handle(message);

        assertThat(chainHandler.isMessageHandled(), is(false));
        assertThat(nextHandler.isMessageHandled(), is(true));
    }

    @Test
    public void ifSupportedType_HandlesDeadLetter() {

        MessageStub<SupportedPayloadDummy> message = new MessageStub<SupportedPayloadDummy>(new SupportedPayloadDummy());

        chainHandler.handle(message);

        assertThat(chainHandler.isMessageHandled(), is(true));
        assertThat(nextHandler.isMessageHandled(), is(false));
    }

    class MessageStub<T> implements Message<T> {

        private T payload;

        public MessageStub(T payload) {
            this.payload = payload;
        }

        @Override
        public MessageHeaders getHeaders() {
            return null;
        }

        @Override
        public T getPayload() {
            return payload;
        }
    }

    class ChainDeadLetterHandlerSpy extends ChainDeadLetterHandler {

        private Boolean messageHandled = false;

        public ChainDeadLetterHandlerSpy(Class supportedClass, DeadLetterHandler nextHandler) {
            super(supportedClass, nextHandler);
        }

        @Override
        public void handleMessage(Message message) {
            messageHandled = true;
        }

        public Boolean isMessageHandled() {
            return messageHandled;
        }
    }

    class DeadLetterHandlerSpy implements DeadLetterHandler {

        private Boolean messageHandled = false;

        @Override
        public void handle(Message message) {
            messageHandled = true;
        }

        public Boolean isMessageHandled() {
            return messageHandled;
        }
    }

    class SupportedPayloadDummy {
    }

    class NonSupportedPayloadDummy {
    }
}
