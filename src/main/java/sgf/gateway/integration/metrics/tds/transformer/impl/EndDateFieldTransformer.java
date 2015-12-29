package sgf.gateway.integration.metrics.tds.transformer.impl;

import org.joda.time.DateTime;
import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.tds.transformer.FieldTransformer;
import sgf.gateway.integration.metrics.tds.transformer.StartDateStrategy;

import java.util.Date;
import java.util.Map;

public class EndDateFieldTransformer implements FieldTransformer {

    private final String durationKey;
    private final String startDateKey;
    private final String startTimeKey;
    private final StartDateStrategy startDateStrategy;

    public EndDateFieldTransformer(String durationKey, String startDateKey, String startTimeKey, StartDateStrategy startDateStrategy) {
        this.durationKey = durationKey;
        this.startDateKey = startDateKey;
        this.startTimeKey = startTimeKey;
        this.startDateStrategy = startDateStrategy;
    }

    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        String startDateToken = tokens.get(startDateKey);
        String startTimeToken = tokens.get(startTimeKey);
        String durationToken = tokens.get(durationKey);

        String[] timeTokens = durationToken.split("\\.");
        String secondsToken = timeTokens[0];
        String millisToken = timeTokens[1];

        int elapsedSeconds = Integer.parseInt(secondsToken);
        int elapsedMillis = Integer.parseInt(millisToken);

        Date startDate = startDateStrategy.extractStartDate(startDateToken, startTimeToken);

        // Add duration to start date (e.g. 145.502).
        DateTime endDate = (new DateTime(startDate)).plusSeconds(elapsedSeconds).plusMillis(elapsedMillis);

        payload.setEndDate(endDate.toDate());

        return payload;
    }
}
