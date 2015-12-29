package sgf.gateway.integration.metrics.gridftp.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.gridftp.transformer.FieldTransformer;
import sgf.gateway.integration.metrics.gridftp.transformer.LogEntryTokenizer;
import sgf.gateway.integration.metrics.gridftp.transformer.LogEntryTransformer;
import sgf.gateway.service.metrics.FileDownloadDetails;

import java.util.List;
import java.util.Map;

public class LogEntryTransformerImpl implements LogEntryTransformer {

    private final LogEntryTokenizer tokenizer;
    private final List<FieldTransformer> fieldTransformers;

    public LogEntryTransformerImpl(LogEntryTokenizer tokenizer, List<FieldTransformer> fieldTransformers) {
        super();
        this.tokenizer = tokenizer;
        this.fieldTransformers = fieldTransformers;
    }

    @Override
    public FileDownloadDetails transform(String gridFTPMetrics) {

        FileDownloadPayload payload = new FileDownloadPayload(gridFTPMetrics);

        Map<String, String> tokens = tokenizer.tokenize(gridFTPMetrics);

        for (FieldTransformer fieldTransformer : fieldTransformers) {
            payload = fieldTransformer.transform(payload, tokens);
        }

        return payload;
    }
}
