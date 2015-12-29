package sgf.gateway.integration.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;

public class SprIntDeadLetterLogger implements DeadLetterHandler {

    private final static Logger LOG = LoggerFactory.getLogger(SprIntDeadLetterLogger.class);

    @Override
    public void handle(Message message) {
        LOG.error("Received the following message from the dead letter queue: " + message);
    }
}
