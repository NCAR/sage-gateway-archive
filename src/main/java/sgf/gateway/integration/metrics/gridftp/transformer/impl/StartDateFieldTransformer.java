package sgf.gateway.integration.metrics.gridftp.transformer.impl;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.gridftp.transformer.FieldTransformer;

import java.util.Date;
import java.util.Map;

public class StartDateFieldTransformer implements FieldTransformer {

    private final String startDateKey;

    public StartDateFieldTransformer(String startDateKey) {
        this.startDateKey = startDateKey;
    }

    @Override
    public FileDownloadPayload transform(FileDownloadPayload payload, Map<String, String> tokens) {

        //START=20110216193159.532499
        String startDateToken = tokens.get(startDateKey);
        String startDateNoMillisToken = startDateToken.substring(0, startDateToken.indexOf("."));

        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyyMMddHHmmss").withZone(DateTimeZone.UTC);
        DateTime dateTime = dateTimeFormat.parseDateTime(startDateNoMillisToken);

        Date startDate = dateTime.toDate();

        payload.setStartDate(startDate);

        return payload;
    }
}
