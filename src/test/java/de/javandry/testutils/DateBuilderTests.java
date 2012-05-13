package de.javandry.testutils;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

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
