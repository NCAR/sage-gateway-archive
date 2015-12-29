package sgf.gateway.integration.metrics.tds.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.tds.transformer.FieldTransformer;

import java.util.Map;

public class OpenIdFieldTransformer implements FieldTransformer {

    private final String openIdKey;

    public OpenIdFieldTransformer(String openIdKey) {
        this.openIdKey = openIdKey;
    }

    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        String openIdToken = tokens.get(openIdKey);

        payload.setOpenId(openIdToken);

        return payload;
    }
}
