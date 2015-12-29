package sgf.gateway.integration.metrics.tds.transformer.impl;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import sgf.gateway.integration.metrics.tds.transformer.StartDateStrategy;

import java.util.Date;

public class StartDateStrategyImpl implements StartDateStrategy {

    @Override
    public Date extractStartDate(String startDate, String startTime) {

        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC);

        DateTime dateTime = dateTimeFormat.parseDateTime(startDate + " " + startTime);

        Date toDate = dateTime.toDate();

        return toDate;
    }
}
