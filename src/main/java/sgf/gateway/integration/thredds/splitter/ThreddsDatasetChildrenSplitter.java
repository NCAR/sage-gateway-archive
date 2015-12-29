package sgf.gateway.integration.thredds.splitter;

import sgf.gateway.integration.thredds.ThreddsDatasetChildrenPayload;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ThreddsDatasetChildrenSplitter {

    public List<ThreddsDatasetPayload> createDatasetPayloads(ThreddsDatasetChildrenPayload payload) {

        List<ThreddsDatasetPayload> datasetPayloads = new ArrayList<ThreddsDatasetPayload>();

        for (URI threddsDatasetURI : payload.getThreddsDatasetURIs()) {

            ThreddsDatasetPayload datasetPayload = new ThreddsDatasetPayload();

            datasetPayload.setSource(threddsDatasetURI);
            datasetPayload.setParentDatasetShortName(payload.getParentDatasetShortName());
            datasetPayload.setDataCenterName(payload.getDataCenterName());

            datasetPayloads.add(datasetPayload);
        }

        return datasetPayloads;
    }
}