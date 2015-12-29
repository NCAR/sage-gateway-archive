package sgf.gateway.integration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStringParser {

    private final String dateFormat;
    private final DateFormat parser;

    public DateStringParser(String dateFormat) {
        super();
        this.dateFormat = dateFormat;
        this.parser = new SimpleDateFormat(dateFormat);
        this.parser.setLenient(false);
    }

    public Date parse(String date) {
        Date parsedDate = tryParse(date);
        return parsedDate;
    }

    private Date tryParse(String date) {
        try {
            return parser.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Exception parsing " + date + " as " + dateFormat, e);
        }
    }
}
