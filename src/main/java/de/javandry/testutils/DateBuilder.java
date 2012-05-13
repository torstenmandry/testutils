package de.javandry.testutils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class DateBuilder {

    private final Calendar calendar;

    public static DateBuilder today() {
        return new DateBuilder();
    }

    private DateBuilder() {
        calendar = new GregorianCalendar();
    }

    public int getDay() {
        return calendar.get(DAY_OF_MONTH);
    }

    public int getMonth() {
        return calendar.get(MONTH) + 1;
    }

    public int getYear() {
        return calendar.get(YEAR);
    }
}
