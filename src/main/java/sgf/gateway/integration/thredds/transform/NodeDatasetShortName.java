package sgf.gateway.integration.thredds.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import sgf.gateway.utils.FileNameAndURIRenameStrategy;
import thredds.catalog.InvDataset;

public class NodeDatasetShortName implements Node<ThreddsDatasetPayload> {

    private final static Logger LOG = LoggerFactory.getLogger(Node.class);

    private final FileNameAndURIRenameStrategy identifierRenameStrategy;

    public NodeDatasetShortName(final FileNameAndURIRenameStrategy identifierRenameStrategy) {
        super();
        this.identifierRenameStrategy = identifierRenameStrategy;
    }

    @Override
    public void process(ThreddsDatasetPayload payload) {

        String shortName = getShortName(payload.getInvDataset());

        payload.setShortName(shortName);

        if (LOG.isDebugEnabled()) {
            LOG.debug("thredds dataset short name: " + shortName);
        }
    }

    private String getShortName(InvDataset invDataset) {

        String shortName = invDataset.getID();

        shortName = identifierRenameStrategy.rename(shortName.trim());

        return shortName;
    }
}
