package sgf.gateway.integration.metrics.tds.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.tds.transformer.FieldTransformer;

import java.util.Map;

public class RemoteAddressFieldTransformer implements FieldTransformer {

    private final String remoteAddressKey;

    public RemoteAddressFieldTransformer(String remoteAddressKey) {
        this.remoteAddressKey = remoteAddressKey;
    }


    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        String remoteAddress = tokens.get(remoteAddressKey);

        payload.setRemoteAddress(remoteAddress);

        return payload;
    }
}
