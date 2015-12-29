package sgf.gateway.integration.message;

import org.springframework.integration.Message;

public interface DeadLetterHandler {
    public void handle(Message message);
}
