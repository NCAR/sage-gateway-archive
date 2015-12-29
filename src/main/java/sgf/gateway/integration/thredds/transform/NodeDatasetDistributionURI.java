package sgf.gateway.integration.thredds.transform;


import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import thredds.catalog.InvAccess;
import thredds.catalog.InvDataset;

import java.net.URI;
import java.util.HashSet;
import java.util.List;

public class NodeDatasetDistributionURI implements Node<ThreddsDatasetPayload> {

    //private static final String EOL_DISTRIBUTION_SERVICE_NAME = "codiac_order";
    private static final HashSet<String> EOL_DISTRIBUTION_SERVICE_NAMES;

    static {
        EOL_DISTRIBUTION_SERVICE_NAMES = new HashSet<String>();
        EOL_DISTRIBUTION_SERVICE_NAMES.add("codiac_order");
        EOL_DISTRIBUTION_SERVICE_NAMES.add("codiac_order_eula");
    }

    @Override
    public void process(ThreddsDatasetPayload payload) {
        InvDataset invDataset = payload.getInvDataset();
        InvAccess distributionAccess = getDistributionAccess(invDataset.getAccess());
        if (distributionAccess != null) {
            createDistributionURI(payload, distributionAccess);
            createDistributionText(payload, distributionAccess);
        }
    }

    private InvAccess getDistributionAccess(List<InvAccess> invAccessList) {
        InvAccess distributionAccess = null;
        for (InvAccess invAccess : invAccessList) {
            String serviceName = invAccess.getService().getName();
            if (EOL_DISTRIBUTION_SERVICE_NAMES.contains(serviceName)) {
                distributionAccess = invAccess;
                break;
            }
        }
        return distributionAccess;
    }

    private void createDistributionText(ThreddsDatasetPayload payload, InvAccess distributionAccess) {
        String description = distributionAccess.getService().getDescription();
        payload.setDistributionText(description);
    }

    private void createDistributionURI(ThreddsDatasetPayload payload, InvAccess distributionAccess) {
        String baseURI = distributionAccess.getService().getBase();
        String urlPath = distributionAccess.getUrlPath();
        payload.setDistributionURI(URI.create(baseURI + urlPath));
    }

}
