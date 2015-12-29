package sgf.gateway.integration.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NodeAggregator<T> implements Node<T> {

    private final static Logger LOG = LoggerFactory.getLogger(NodeAggregator.class);

    private List<Node<T>> nodes;

    public NodeAggregator(List<Node<T>> nodes) {
        super();
        this.nodes = nodes;
    }

    @Override
    public void process(T payload) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("processing through aggregator " + payload.getClass().getName() + " " + payload);
        }

        for (Node<T> node : nodes) {
            node.process(payload);
        }
    }
}
