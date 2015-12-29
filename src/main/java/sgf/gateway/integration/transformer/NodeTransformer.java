package sgf.gateway.integration.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.integration.node.Node;

public class NodeTransformer<T> {

    private final static Logger LOG = LoggerFactory.getLogger(Node.class);

    private final Node<T> transformNode;

    public NodeTransformer(Node<T> transformNode) {
        super();
        this.transformNode = transformNode;
    }

    public T transform(T payload) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("transforming " + payload.getClass().getName() + " " + payload);
        }

        try {
            transformNode.process(payload);
        } catch (Exception e) {
            LOG.error("exception transforming " + payload.getClass().getName() + " " + payload + ": ", e);
            throw new RuntimeException(e);
        }

        return payload;
    }
}
