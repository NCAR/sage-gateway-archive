package sgf.gateway.search.core;

import java.text.DateFormat;


public class TemporalFilter {

    private final String endDate;
    private final String startDate;
    private final DateFormat dateFormat;

    public TemporalFilter(String startDate, String endDate, DateFormat dateFormat) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateFormat = dateFormat;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public DateFormat getDateFormat() {
        return this.dateFormat;
    }
}
