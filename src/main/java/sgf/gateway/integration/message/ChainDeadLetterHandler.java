package sgf.gateway.integration.message;

import org.springframework.integration.Message;

public abstract class ChainDeadLetterHandler implements DeadLetterHandler {

    private Class supportedClass;
    private DeadLetterHandler nextHandler;

    public ChainDeadLetterHandler(Class supportedClass, DeadLetterHandler nextHandler) {
        this.supportedClass = supportedClass;
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(Message message) {
        if (supportsPayload(message)) {
            handleMessage(message);
        } else {
            this.nextHandler.handle(message);
        }
    }

    private Boolean supportsPayload(Message message) {
        if (supportedClass.isAssignableFrom(message.getPayload().getClass())) {
            return true;
        }
        return false;
    }

    protected abstract void handleMessage(Message message);
}
