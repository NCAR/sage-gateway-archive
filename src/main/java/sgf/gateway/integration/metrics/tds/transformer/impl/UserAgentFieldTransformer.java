package sgf.gateway.integration.metrics.tds.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.tds.transformer.FieldTransformer;

import java.util.Map;

public class UserAgentFieldTransformer implements FieldTransformer {

    private final String userAgentKey;

    public UserAgentFieldTransformer(String userAgentKey) {
        this.userAgentKey = userAgentKey;
    }


    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        String userAgent = tokens.get(userAgentKey);

        payload.setUserAgent(userAgent);

        return payload;
    }
}
