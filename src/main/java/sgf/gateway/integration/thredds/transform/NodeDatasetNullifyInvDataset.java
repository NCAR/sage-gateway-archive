package sgf.gateway.integration.thredds.transform;

import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;

public class NodeDatasetNullifyInvDataset implements Node<ThreddsDatasetPayload> {

    public NodeDatasetNullifyInvDataset() {
        super();
    }

    @Override
    public void process(ThreddsDatasetPayload payload) {
        // nullify the InvDataset--it is not serializable
        payload.setInvDataset(null);
    }
}
