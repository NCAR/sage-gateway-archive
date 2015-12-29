package sgf.gateway.integration.thredds.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import thredds.catalog.InvDataset;
import ucar.nc2.units.DateType;

import java.util.Date;


public class NodeDatasetAuthoritativeSourceDateCreated implements Node<ThreddsDatasetPayload> {

    private final static Logger LOG = LoggerFactory.getLogger(Node.class);

    @Override
    public void process(ThreddsDatasetPayload payload) {

        Date date = getAuthoritativeSourceDateCreated(payload.getInvDataset());
        payload.setAuthoritativeSourceDateCreated(date);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Dataset authoritative source date created is " + date + " for payload " + payload);
        }
    }

    private Date getAuthoritativeSourceDateCreated(InvDataset invDataset) {

        for (DateType dateType : invDataset.getDates()) {
            if (dateType.getType().equalsIgnoreCase("metadataCreated")) {
                return dateType.getDate();
            }
        }

        return null;
    }
}
