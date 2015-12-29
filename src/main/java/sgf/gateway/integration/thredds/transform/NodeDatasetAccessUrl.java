package sgf.gateway.integration.thredds.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import thredds.catalog.InvDataset;
import thredds.catalog.InvDocumentation;

import java.net.URI;

public class NodeDatasetAccessUrl implements Node<ThreddsDatasetPayload> {

    private final static Logger LOG = LoggerFactory.getLogger(Node.class);

    public static final String DATA_ACCESS_MARKER = "EOL Data Archive website";

    public NodeDatasetAccessUrl() {
        super();
    }

    @Override
    public void process(ThreddsDatasetPayload payload) {

        InvDocumentation dataAccessInvDoc = getDataAccessInvDocumentation(payload.getInvDataset());

        if (null != dataAccessInvDoc) {

            String dataAccessText = dataAccessInvDoc.getXlinkTitle();
            URI dataAccessURI = URI.create(dataAccessInvDoc.getXlinkHref());

            if (LOG.isDebugEnabled()) {
                LOG.debug("Dataset access uri is " + dataAccessURI + " titled " + dataAccessText + " for payload " + payload);
            }

            payload.setDataAccessText(dataAccessText);
            payload.setDataAccessURI(dataAccessURI);
        }
    }

    private InvDocumentation getDataAccessInvDocumentation(InvDataset invDataset) {

        InvDocumentation dataAccessInvDoc = null;

        for (InvDocumentation invDoc : invDataset.getDocumentation()) {

            if (invDoc.hasXlink()) {

                String xlinkTitle = invDoc.getXlinkTitle();

                if (xlinkTitle.toLowerCase().contains(DATA_ACCESS_MARKER.toLowerCase())) {
                    dataAccessInvDoc = invDoc;
                    break;
                }
            }
        }

        return dataAccessInvDoc;
    }
}
