package sgf.gateway.integration.ade.opensearch.receiver.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.integration.ade.opensearch.Feed;
import sgf.gateway.integration.ade.opensearch.receiver.Receiver;

public class ReceiverStub implements Receiver {

    private final static Logger LOG = LoggerFactory.getLogger(Receiver.class);

    public Feed receive() {
        LOG.info("Stub Receiver called!");
        return null;
    }
}
