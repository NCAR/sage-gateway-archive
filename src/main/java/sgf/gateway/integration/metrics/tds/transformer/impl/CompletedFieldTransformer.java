package sgf.gateway.integration.metrics.tds.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.tds.transformer.FieldTransformer;

import java.util.Map;

public class CompletedFieldTransformer implements FieldTransformer {

    private final String contentLengthKey;
    private final String bytesSentKey;

    public CompletedFieldTransformer(String contentLengthKey, String bytesSentKey) {
        this.contentLengthKey = contentLengthKey;
        this.bytesSentKey = bytesSentKey;
    }

    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        Boolean completed = isDownloadCompleted(tokens);
        payload.setCompleted(completed);

        return payload;
    }

    public Boolean isDownloadCompleted(Map<String, String> tokens) {

        Boolean completed = false;

        String bytesSentToken = tokens.get(bytesSentKey);
        String contentLengthToken = tokens.get(contentLengthKey);

        if (bytesSentToken != null && contentLengthToken != null) {

            Long bytesSent = new Long(bytesSentToken);
            Long contentLength = new Long(contentLengthToken);

            if (contentLength.equals(bytesSent) && contentLength > 0) {
                completed = true;
            }
        }

        return completed;
    }
}
