package sgf.gateway.integration.metrics.tds.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.tds.transformer.FieldTransformer;

import java.util.Map;

public class BytesSentFieldTransformer implements FieldTransformer {

    private final String bytesSentKey;

    public BytesSentFieldTransformer(String bytesSentKey) {
        this.bytesSentKey = bytesSentKey;
    }

    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        Long bytesSent = getBytesSent(tokens);
        payload.setBytesSent(bytesSent);

        return payload;
    }

    private Long getBytesSent(Map<String, String> tokens) {

        String bytesSentToken = tokens.get(bytesSentKey);
        Long bytesSent = 0L;

        if (bytesSentToken != null) {
            bytesSent = new Long(bytesSentToken);
        }

        return bytesSent;
    }
}
