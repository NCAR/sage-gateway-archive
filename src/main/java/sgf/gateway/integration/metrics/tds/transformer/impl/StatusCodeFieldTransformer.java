package sgf.gateway.integration.metrics.tds.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.tds.transformer.FieldTransformer;

import java.util.Map;

public class StatusCodeFieldTransformer implements FieldTransformer {

    private final String statusCodeKey;

    public StatusCodeFieldTransformer(String statusCodeKey) {
        this.statusCodeKey = statusCodeKey;
    }


    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        String statusCodeToken = tokens.get(statusCodeKey);

        payload.setStatus(statusCodeToken);

        return payload;
    }
}
