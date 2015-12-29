package sgf.gateway.integration.metrics.tds.transformer.impl;

import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.tds.transformer.FieldTransformer;
import sgf.gateway.integration.metrics.tds.transformer.StartDateStrategy;

import java.util.Date;
import java.util.Map;

public class StartDateFieldTransformer implements FieldTransformer {

    private final String startDateKey;
    private final String startTimeKey;
    private final StartDateStrategy startDateStrategy;

    public StartDateFieldTransformer(String startDateKey, String startTimeKey, StartDateStrategy startDateStrategy) {
        this.startDateKey = startDateKey;
        this.startTimeKey = startTimeKey;
        this.startDateStrategy = startDateStrategy;
    }

    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        String startDateToken = tokens.get(startDateKey);
        String startTimeToken = tokens.get(startTimeKey);

        Date startDate = startDateStrategy.extractStartDate(startDateToken, startTimeToken);

        payload.setStartDate(startDate);

        return payload;
    }
}
