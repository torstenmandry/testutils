package de.javandry.testutils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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

    public static DateBuilder parse(String dateString) {
        return new DateBuilder(dateString);
    }

    public static DateBuilder parse(String dateString, Locale locale) {
        return new DateBuilder(dateString, locale);
    }

    public static DateBuilder parse(String dateString, String format) {
        return new DateBuilder(dateString, format);
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

    private DateBuilder(String dateString) {
        this(dateString, Locale.getDefault());
    }

    private DateBuilder(String dateString, Locale locale) {
        this(dateString, DateFormat.getDateInstance(DateFormat.DEFAULT, locale));
    }

    private DateBuilder(String dateString, String format) {
        this(dateString, new SimpleDateFormat(format));
    }

    private DateBuilder(String dateString, DateFormat dateFormat) {
        this();
        try {
            calendar.setTime(dateFormat.parse(dateString));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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

    public DateBuilder daysAgo(int days) {
        return daysAhead(-days);
    }

    public DateBuilder daysAhead(int days) {
        calendar.add(DAY_OF_MONTH, days);
        return this;
    }

    public DateBuilder monthsAgo(int months) {
        calendar.add(MONTH, -months);
        return this;
    }

    public Date toDate() {
        return calendar.getTime();
    }

    public java.sql.Date toSqlDate() {
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    @Override
    public String toString() {
        return DateFormat.getDateInstance().format(calendar.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateBuilder that = (DateBuilder) o;

        if (calendar != null ? !calendar.equals(that.calendar) : that.calendar != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return calendar != null ? calendar.hashCode() : 0;
    }
}
