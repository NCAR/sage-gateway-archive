package sgf.gateway.integration.thredds.transformer;

import sgf.gateway.integration.thredds.ThreddsDatasetPayload;

import java.net.URI;

public class URIToThreddsDatasetPayloadTransformer {

    private final String dataCenterName;

    public URIToThreddsDatasetPayloadTransformer(String dataCenterName) {
        super();
        this.dataCenterName = dataCenterName;
    }

    public ThreddsDatasetPayload transform(URI uri) {

        ThreddsDatasetPayload payload = new ThreddsDatasetPayload();

        payload.setSource(uri);
        payload.setDataCenterName(dataCenterName);

        return payload;
    }

}
