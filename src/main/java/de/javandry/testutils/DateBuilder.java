package de.javandry.testutils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.*;

public class DateBuilder {

    private final Calendar calendar;

    public static DateBuilder today() {
        return new DateBuilder();
    }

    public static DateBuilder givenDate(int day, int month, int year) {
        return new DateBuilder(day, month, year);
    }

    public static DateBuilder valueOf(Date date) {
        return new DateBuilder(date);
    }

    private DateBuilder() {
        calendar = new GregorianCalendar();
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
    }

    private DateBuilder(int day, int month, int year) {
        this();
        calendar.set(DAY_OF_MONTH, day);
        calendar.set(MONTH, month - 1);
        calendar.set(YEAR, year);
    }

    private DateBuilder(Date date) {
        this();
        calendar.setTime(date);
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

    public Date toDate() {
        return calendar.getTime();
    }

    public java.sql.Date toSqlDate() {
        return new java.sql.Date(calendar.getTimeInMillis());
    }
}
