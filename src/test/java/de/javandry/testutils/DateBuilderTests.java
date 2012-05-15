package de.javandry.testutils;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateBuilderTests {

    private Calendar calendar;

    @Before
    public void setUp() throws Exception {
        calendar = GregorianCalendar.getInstance();
    }

    @Test
    public void today() {
        assertEqualsCalendar(calendar, DateBuilder.today());
    }

    @Test
    public void givenDate() {
        DateBuilder givenDate = DateBuilder.givenDate(14, 12, 1972);

        assertEquals(14, givenDate.getDay());
        assertEquals(12, givenDate.getMonth());
        assertEquals(1972, givenDate.getYear());
    }

    @Test
    public void valueOfUtilDate() {
        java.util.Date utilDate = calendar.getTime();
        assertEqualsCalendar(calendar, DateBuilder.valueOf(utilDate));
    }

    @Test
    public void valueOfSqlDate() {
        java.sql.Date sqlDate = new java.sql.Date(calendar.getTimeInMillis());
        assertEqualsCalendar(calendar, DateBuilder.valueOf(sqlDate));
    }

    @Test
    // TODO Weitere Beispiele mit anderen Locales und Formaten
    public void parseString() {
        parseString(Locale.GERMANY, "14.12.1972", 14, 12, 1972);
        parseString(Locale.GERMANY, "29.02.2012", 29, 2, 2012);
    }

    private void parseString(Locale locale, String dateString, int expectedDay, int expectedMonth, int expectedYear) {
        Locale.setDefault(locale);
        DateBuilder dateBuilder = DateBuilder.parse(dateString);

        assertEquals(expectedDay, dateBuilder.getDay());
        assertEquals(expectedMonth, dateBuilder.getMonth());
        assertEquals(expectedYear, dateBuilder.getYear());
    }

    @Test
    public void daysAgo() {
        Locale.setDefault(Locale.GERMANY);
        assertDaysAgo(14, 12, 1972, 2, 12, 12, 1972);
        assertDaysAgo(14, 12, 1972, 5, 9, 12, 1972);
        assertDaysAgo(1, 12, 1972, 1, 30, 11, 1972);
        assertDaysAgo(1, 1, 1972, 1, 31, 12, 1971);
    }

    private void assertDaysAgo(int givenDay, int givenMonth, int givenYear, int days, int expectedDay, int expectedMonth, int expectedYear) {
        DateBuilder dateBuilder = DateBuilder.givenDate(givenDay, givenMonth, givenYear);

        dateBuilder.daysAgo(days);

        assertEquals(expectedDay, dateBuilder.getDay());
        assertEquals(expectedMonth, dateBuilder.getMonth());
        assertEquals(expectedYear, dateBuilder.getYear());
    }

    @Test
    public void equals() {
        Locale.setDefault(Locale.GERMANY);
        DateBuilder givenDate = DateBuilder.givenDate(14, 12, 1972);

        assertTrue(givenDate.equals(givenDate));
        assertTrue(givenDate.equals(DateBuilder.givenDate(14, 12, 1972)));
        assertTrue(givenDate.equals(DateBuilder.parse("14.12.1972")));
        assertFalse(givenDate.equals(DateBuilder.givenDate(14, 12, 1973)));
    }

    @Test
    public void toUtilDate() {
        DateBuilder dateBuilder = DateBuilder.givenDate(14, 12, 1972);

        Calendar utilDateCalendar = calendarFor(dateBuilder.toDate());

        assertEquals(14, getDayOfMonth(utilDateCalendar));
        assertEquals(12, getMonth(utilDateCalendar));
        assertEquals(1972, getYear(utilDateCalendar));
        assertNoTime(utilDateCalendar);
    }

    @Test
    public void toSqlDate() {
        DateBuilder dateBuilder = DateBuilder.givenDate(14, 12, 1972);

        java.sql.Date sqlDate = dateBuilder.toSqlDate();
        Calendar sqlDateCalendar = calendarFor(sqlDate);

        assertEquals(14, getDayOfMonth(sqlDateCalendar));
        assertEquals(12, getMonth(sqlDateCalendar));
        assertEquals(1972, getYear(sqlDateCalendar));
        assertNoTime(sqlDateCalendar);
    }

    private void assertEqualsCalendar(Calendar cal, DateBuilder today) {
        assertEquals(getDayOfMonth(cal), today.getDay());
        assertEquals(getMonth(cal), today.getMonth());
        assertEquals(getYear(cal), today.getYear());
    }

    private void assertNoTime(Calendar utilDateCalendar) {
        assertEquals(0, utilDateCalendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, utilDateCalendar.get(Calendar.MINUTE));
        assertEquals(0, utilDateCalendar.get(Calendar.SECOND));
        assertEquals(0, utilDateCalendar.get(Calendar.MILLISECOND));
    }

    private Calendar calendarFor(Date utilDate) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(utilDate);
        return cal;
    }

    private int getDayOfMonth(Calendar utilDateCalendar) {
        return utilDateCalendar.get(Calendar.DAY_OF_MONTH);
    }

    private int getMonth(Calendar sqlDateCalendar) {
        return sqlDateCalendar.get(Calendar.MONTH) + 1;
    }

    private int getYear(Calendar sqlDateCalendar) {
        return sqlDateCalendar.get(Calendar.YEAR);
    }
}
