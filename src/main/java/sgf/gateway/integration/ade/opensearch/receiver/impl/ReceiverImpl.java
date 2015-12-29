package sgf.gateway.integration.ade.opensearch.receiver.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.integration.ade.opensearch.Feed;
import sgf.gateway.integration.ade.opensearch.receiver.Reader;
import sgf.gateway.integration.ade.opensearch.receiver.Receiver;

public class ReceiverImpl implements Receiver {

    private static final Log LOG = LogFactory.getLog(Receiver.class);

    private final Object monitor;
    private final Reader reader;

    private boolean reset;

    public ReceiverImpl(Reader reader) {
        this.monitor = new Object();
        this.reader = reader;
        this.reset = true;
    }

    public Feed receive() {
        synchronized (monitor) {
            return synchronizedReceive();
        }
    }

    private Feed synchronizedReceive() {

        if (reset) {
            LOG.info("Harvest commencing...");
            reader.reset();
            reset = false;
        }

        if (reader.continuePaging()) {
            return reader.read();
        }

        reset = true;

        LOG.info("Harvest complete.");

        return null;
    }
}
