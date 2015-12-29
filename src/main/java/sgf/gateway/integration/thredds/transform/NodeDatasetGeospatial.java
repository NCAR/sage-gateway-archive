package sgf.gateway.integration.thredds.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import thredds.catalog.ThreddsMetadata;

public class NodeDatasetGeospatial implements Node<ThreddsDatasetPayload> {

    private final static Logger LOG = LoggerFactory.getLogger(Node.class);

    public NodeDatasetGeospatial() {
        super();
    }

    @Override
    public void process(ThreddsDatasetPayload payload) {

        ThreddsMetadata.GeospatialCoverage extent = payload.getInvDataset().getGeospatialCoverage();

        payload.setNorthernLatitude(extent.getLatNorth());
        payload.setSouthernLatitude(extent.getLatSouth());
        payload.setWesternLongitude(extent.getLonWest());
        payload.setEasternLongitude(extent.getLonEast());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Dataset extent is  lat[" +
                    payload.getSouthernLatitude() + ", " + payload.getNorthernLatitude() + "] lon[" +
                    payload.getWesternLongitude() + ", " + payload.getEasternLongitude() + "]" +
                    " for payload " + payload);
        }
    }
}
