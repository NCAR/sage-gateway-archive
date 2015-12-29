package sgf.gateway.integration.thredds.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import thredds.catalog.InvDataset;

public class NodeDatasetAuthoritativeIdentifier implements Node<ThreddsDatasetPayload> {

    private final static Logger LOG = LoggerFactory.getLogger(Node.class);

    public NodeDatasetAuthoritativeIdentifier() {
        super();
    }

    @Override
    public void process(ThreddsDatasetPayload payload) {

        String authoritativeIdentifier = getAuthoritativeIdentifier(payload.getInvDataset());

        payload.setAuthoritativeIdentifier(authoritativeIdentifier);

        if (LOG.isDebugEnabled()) {
            LOG.debug("thredds dataset authoritative identifier: " + authoritativeIdentifier);
        }
    }

    private String getAuthoritativeIdentifier(InvDataset invDataset) {
        String authoritativeIdentifier = invDataset.getID();
        return authoritativeIdentifier;
    }
}
