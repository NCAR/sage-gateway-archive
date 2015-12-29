package sgf.gateway.utils.time.impl;

import sgf.gateway.utils.time.CalendarStrategy;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class NewGregorianCalendarStrategy implements CalendarStrategy {

    /**
     * {@inheritDoc}
     */
    public Calendar getCalendar() {

        return new GregorianCalendar();
    }
}
