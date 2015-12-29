package sgf.gateway.integration.thredds.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import thredds.catalog.InvDataset;

public class NodeDatasetDescription implements Node<ThreddsDatasetPayload> {

    private final static Logger LOG = LoggerFactory.getLogger(Node.class);

    private static final String DESCRIPTION_KEY = "summary";

    public NodeDatasetDescription() {
        super();
    }

    @Override
    public void process(ThreddsDatasetPayload payload) {

        String description = getDescription(payload.getInvDataset());

        payload.setDescription(description);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Dataset description is " + description + " for payload " + payload);
        }
    }

    private String getDescription(InvDataset invDataset) {

        String description = null;

        if (invDataset.getDocumentation() != null && (invDataset.getDocumentation().size() > 0)) {
            description = invDataset.getDocumentation(DESCRIPTION_KEY);
        }

        return description;
    }
}
