package sgf.gateway.integration.thredds.transform;

import sgf.gateway.integration.node.Node;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import thredds.catalog.InvDataset;

import static org.springframework.util.StringUtils.hasText;

public class NodeDatasetDoi implements Node<ThreddsDatasetPayload> {

    @Override
    public void process(ThreddsDatasetPayload payload) {

        InvDataset invDataset = payload.getInvDataset();

        String doi = invDataset.findProperty("doi");

        if (hasText(doi)) {

            doi = doi.trim();

            if (startsWithDoiColon(doi)) {

                payload.setDoi(doi);
            }
        }
    }

    private boolean startsWithDoiColon(String doi) {

        return doi.startsWith("doi:");
    }

}
