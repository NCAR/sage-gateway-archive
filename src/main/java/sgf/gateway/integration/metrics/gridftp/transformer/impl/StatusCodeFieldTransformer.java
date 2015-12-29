package sgf.gateway.integration.metrics.gridftp.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.gridftp.transformer.FieldTransformer;

import java.util.Map;

public class StatusCodeFieldTransformer implements FieldTransformer {

    private final String statusCodeKey;

    public StatusCodeFieldTransformer(String statusCodeKey) {
        this.statusCodeKey = statusCodeKey;
    }

    @Override
    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        String statusCode = tokens.get(statusCodeKey);

        payload.setStatus(statusCode);

        return payload;
    }
}
