package sgf.gateway.integration.thredds.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import ucar.nc2.units.DateRange;

public class NodeDatasetStartEndDate implements Node<ThreddsDatasetPayload> {

    private final static Logger LOG = LoggerFactory.getLogger(Node.class);

    @Override
    public void process(ThreddsDatasetPayload payload) {

        DateRange dates = payload.getInvDataset().getTimeCoverage();

        payload.setStartDate(dates.getStart().getDate());
        payload.setEndDate(dates.getEnd().getDate());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Dataset dates are " + payload.getStartDate().toString() +
                    " through " + payload.getEndDate() + " for payload " + payload);
        }
    }
}
