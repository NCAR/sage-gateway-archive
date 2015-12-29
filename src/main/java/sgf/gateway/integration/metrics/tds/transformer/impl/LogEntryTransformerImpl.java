package sgf.gateway.integration.metrics.tds.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.tds.transformer.FieldSpecifiedStrategy;
import sgf.gateway.integration.metrics.tds.transformer.FieldTransformer;
import sgf.gateway.integration.metrics.tds.transformer.LogEntryTokenizer;
import sgf.gateway.integration.metrics.tds.transformer.LogEntryTransformer;

import java.util.List;
import java.util.Map;

public class LogEntryTransformerImpl implements LogEntryTransformer {

    private final LogEntryTokenizer tokenizer;
    private final FieldSpecifiedStrategy fieldSpecifiedStrategy;
    private final List<FieldTransformer> fieldTransformers;

    public LogEntryTransformerImpl(LogEntryTokenizer tokenizer, FieldSpecifiedStrategy fieldSpecifiedStrategy, List<FieldTransformer> fieldTransformers) {
        super();
        this.tokenizer = tokenizer;
        this.fieldSpecifiedStrategy = fieldSpecifiedStrategy;
        this.fieldTransformers = fieldTransformers;
    }

    @Override
    public FileDownloadPayload transform(String tdsMetrics) {

        Map<String, String> tokens = extract(tdsMetrics);

        FileDownloadPayload payload = new FileDownloadPayload(tdsMetrics);

        for (FieldTransformer fieldTransformer : fieldTransformers) {
            payload = fieldTransformer.transform(payload, tokens);
        }

        return payload;
    }

    private Map<String, String> extract(String tdsMetrics) {

        Map<String, String> tokens = tokenizer.tokenize(tdsMetrics);

        for (String key : tokens.keySet()) {
            if (!fieldSpecifiedStrategy.isSpecified(tokens.get(key))) {
                tokens.put(key, null);
            }
        }

        return tokens;
    }
}