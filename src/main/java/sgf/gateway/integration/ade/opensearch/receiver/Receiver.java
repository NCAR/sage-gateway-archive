package sgf.gateway.integration.ade.opensearch.receiver;

import sgf.gateway.integration.ade.opensearch.Feed;

public interface Receiver {
    Feed receive();
}
