package sgf.gateway.integration.metrics.tds.transformer;

import java.util.Date;

public interface StartDateStrategy {
    Date extractStartDate(String startDate, String startTime);
}
