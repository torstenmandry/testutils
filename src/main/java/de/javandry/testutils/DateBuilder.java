package de.javandry.testutils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.util.Calendar.*;

/**
 * Utility class for build date values.<br/>
 * <br/>
 * Examples:<br/>
 * <code>
 *     DateBuilder.today().daysAgo(2);
 *     DateBuilder.givenDate(15, 5, 2012).monthsAhead(4);
 * </code>
 */
public class DateBuilder {

    private final Calendar calendar;

    /**
     * Returns a new DateBuilder instance initialized with the current system date.
     * @return the new DateBuilder instance.
     */
    public static DateBuilder today() {
        return new DateBuilder();
    }

    /**
     * Returns a new DateBuilder instance initialized with the given day, month and year.
     * @param day the day.
     * @param month the month.
     * @param year the year.
     * @return the new DateBuilder instance.
     */
    public static DateBuilder givenDate(int day, int month, int year) {
        return new DateBuilder(day, month, year);
    }

    /**
     * Returns a new DateBuilder instance initialized with the value of the given Date.
     * @param date the Date.
     * @return the new DateBuilder instance.
     */
    public static DateBuilder valueOf(Date date) {
        return new DateBuilder(date);
    }

    /**
     * Returns a new DateBuilder instance initialized with the value of the given date string.
     * Uses the systems default DateFormat instance to parse the date string.
     * @param dateString the date string.
     * @return the new DateBuilder instance.
     * @throws RuntimeException if the given date string could not be parsed using the systems default DateFormat
     *          instance.
     */
    public static DateBuilder parse(String dateString) {
        return new DateBuilder(dateString);
    }

    /**
     * Returns a new DateBuilder instance initialized with the value of the given date string.
     * Uses the default DateFormat date style pattern for the given locale to parse the date string.
     * @param dateString the date string.
     * @param locale the locale.
     * @return the new DateBuilder instance.
     * @throws RuntimeException if the given date string could not be parsed using the default DateFormat date style
     *          pattern for the given locale.
     */
    public static DateBuilder parse(String dateString, Locale locale) {
        return new DateBuilder(dateString, locale);
    }

    /**
     * Returns a new DateBuilder instance initialized with the value of the given date string.
     * Uses the given format pattern to parse the date string.
     * @param dateString the date string.
     * @param format the date format pattern.
     * @return the new DateBuilder instance.
     * @throws RuntimeException if the given date string could not be parsed using the given date format pattern.
     */
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

    /**
     * Returns the day part (day of month) of the current DateBuilder instance.
     * @return the day.
     */
    public int getDay() {
        return calendar.get(DAY_OF_MONTH);
    }

    /**
     * Returns the month part (month of year) of the current DateBuilder instance.
     * @return the month.
     */
    public int getMonth() {
        return calendar.get(MONTH) + 1;
    }

    /**
     * Returns the year part of the current DateBuilder instance.
     * @return the year.
     */
    public int getYear() {
        return calendar.get(YEAR);
    }

    /**
     * Returns the current DateBuilder instance decremented by the given number of days.
     * @param days the number of days.
     * @return the decremented DateBuilder instance.
     */
    public DateBuilder daysAgo(int days) {
        return daysAhead(-days);
    }

    /**
     * Returns the current DateBuilder instance incremented by the given number of days.
     * @param days the number of days.
     * @return the incremented DateBuilder instance.
     */
    public DateBuilder daysAhead(int days) {
        calendar.add(DAY_OF_MONTH, days);
        return this;
    }

    /**
     * Returns the current DateBuilder instance decremented by the given number of months.
     * @param months the number of months.
     * @return the decremented DateBuilder instance.
     */
    public DateBuilder monthsAgo(int months) {
        return monthsAhead(-months);
    }

    /**
     * Returns the current DateBuilder instance incremented by the given number of months.
     * @param months the number of months.
     * @return the incremented DateBuilder instance.
     */
    public DateBuilder monthsAhead(int months) {
        calendar.add(MONTH, months);
        return this;
    }

    /**
     * Returns the current DateBuilder instance decremented by the given number of years.
     * @param years the number of years.
     * @return the decremented DateBuilder instance.
     */
    public DateBuilder yearsAgo(int years) {
        return yearsAhead(-years);
    }

    /**
     * Returns the current DateBuilder instance incremented by the given number of years.
     * @param years the number of years.
     * @return the incremented DateBuilder instance.
     */
    public DateBuilder yearsAhead(int years) {
        calendar.add(YEAR, years);
        return this;
    }

    /**
     * Converts the current DateBuilder instance to a java.util.Date.
     * The time part (hours, minutes, seconds, milliseconds) of the Date is always 0:00:00.000.
     * @return the java.util.Date.
     */
    public Date toDate() {
        return calendar.getTime();
    }

    /**
     * Converts the current DateBuilder instance to a java.sql.Date.
     * The time part (hours, minutes, seconds) of the Date is always 0:00:00.
     * @return the java.sql.Date.
     */
    public java.sql.Date toSqlDate() {
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * Converts the current DateBuilder instance to a String.
     * Uses the systems default DateFormat instance to format the date string.
     * @return the date string.
     */
    @Override
    public String toString() {
        return DateFormat.getDateInstance().format(calendar.getTime());
    }

    /**
     * Indicates whether some other object is "equal to" this DateBuilder instance.<br/>
     * The other object is "equal to" this DateBuilder instance if
     * <ul>
     *     <li>it is the same DateBuilder instance</li>
     *     <li>it is an DateBuilder that represents/contains the same date (day, month, year)</li>
     * </ul>
     * The other object is not "equal to" this DateBuilder instance if
     * <ul>
     *     <li>it is <code>null</code></li>
     *     <li>it is no DateBuilder</li>
     * </ul>
     * @param otherObject the other object.
     * @return <code>true</code> if the other object is "equal to" this one, otherwise returns <code>false</code>.
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null || getClass() != otherObject.getClass()) return false;

        DateBuilder that = (DateBuilder) otherObject;

        return !(calendar != null ? !calendar.equals(that.calendar) : that.calendar != null);
    }

    /**
     * Returns a hash code value for the object.
     * @return the hash code value.
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return calendar != null ? calendar.hashCode() : 0;
    }
}
