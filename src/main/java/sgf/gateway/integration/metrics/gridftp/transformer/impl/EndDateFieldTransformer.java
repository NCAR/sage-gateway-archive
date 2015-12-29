package sgf.gateway.integration.metrics.gridftp.transformer.impl;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.gridftp.transformer.FieldTransformer;

import java.util.Date;
import java.util.Map;

public class EndDateFieldTransformer implements FieldTransformer {

    private final String endDateKey;

    public EndDateFieldTransformer(String endDateKey) {
        this.endDateKey = endDateKey;
    }

    @Override
    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        //END=20110216193217.971458
        String endDateToken = tokens.get(endDateKey);
        String endDateNoMillisToken = endDateToken.substring(0, endDateToken.indexOf("."));

        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyyMMddHHmmss").withZone(DateTimeZone.UTC);
        DateTime dateTime = dateTimeFormat.parseDateTime(endDateNoMillisToken);

        Date endDate = dateTime.toDate();

        payload.setEndDate(endDate);

        return payload;
    }
}
