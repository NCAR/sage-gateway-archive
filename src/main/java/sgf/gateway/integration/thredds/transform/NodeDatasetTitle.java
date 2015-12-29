package sgf.gateway.integration.thredds.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;

public class NodeDatasetTitle implements Node<ThreddsDatasetPayload> {

    private final static Logger LOG = LoggerFactory.getLogger(Node.class);

    public NodeDatasetTitle() {
        super();
    }

    @Override
    public void process(ThreddsDatasetPayload payload) {

        String name = payload.getInvDataset().getName();
        payload.setTitle(name);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Dataset name is " + name + " for payload " + payload);
        }
    }
}
