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
    public void parseString() {
        Locale.setDefault(Locale.GERMANY);
        assertEquals(DateBuilder.givenDate(14, 12, 1972), DateBuilder.parse("14.12.1972"));
        Locale.setDefault(Locale.US);
        assertEquals(DateBuilder.givenDate(14, 12, 1972), DateBuilder.parse("Dec 14, 1972"));
        Locale.setDefault(Locale.UK);
        assertEquals(DateBuilder.givenDate(14, 12, 1972), DateBuilder.parse("14-Dec-1972"));
    }

    @Test
    public void testParseStringWithGivenLocale() {
        Locale.setDefault(Locale.CHINESE);
        assertEquals(DateBuilder.givenDate(14, 12, 1972), DateBuilder.parse("14.12.1972", Locale.GERMANY));
        assertEquals(DateBuilder.givenDate(14, 12, 1972), DateBuilder.parse("Dec 14, 1972", Locale.US));
        assertEquals(DateBuilder.givenDate(14, 12, 1972), DateBuilder.parse("14-Dec-1972", Locale.UK));
    }

    @Test
    public void daysAgo() {
        Locale.setDefault(Locale.GERMANY);
        assertEquals(DateBuilder.givenDate(14, 12, 1972).daysAgo(2), DateBuilder.givenDate(12, 12, 1972));
        assertEquals(DateBuilder.givenDate(14, 12, 1972).daysAgo(5), DateBuilder.givenDate(9, 12, 1972));
        assertEquals(DateBuilder.givenDate(1, 12, 1972).daysAgo(1), DateBuilder.givenDate(30, 11, 1972));
        assertEquals(DateBuilder.givenDate(1, 1, 1972).daysAgo(1), DateBuilder.givenDate(31, 12, 1971));
        assertEquals(DateBuilder.givenDate(14, 12, 1972).daysAgo(0), DateBuilder.givenDate(14, 12, 1972));
        assertEquals(DateBuilder.givenDate(14, 12, 1972).daysAgo(-2), DateBuilder.givenDate(16, 12, 1972));
    }

    @Test
    public void daysAhead() {
        Locale.setDefault(Locale.GERMANY);
        assertEquals(DateBuilder.givenDate(14, 12, 1972).daysAhead(2), DateBuilder.givenDate(16, 12, 1972));
        assertEquals(DateBuilder.givenDate(14, 12, 1972).daysAhead(5), DateBuilder.givenDate(19, 12, 1972));
        assertEquals(DateBuilder.givenDate(30, 11, 1972).daysAhead(1), DateBuilder.givenDate(1, 12, 1972));
        assertEquals(DateBuilder.givenDate(31, 12, 1972).daysAhead(1), DateBuilder.givenDate(1, 1, 1973));
        assertEquals(DateBuilder.givenDate(14, 12, 1972).daysAhead(0), DateBuilder.givenDate(14, 12, 1972));
        assertEquals(DateBuilder.givenDate(14, 12, 1972).daysAhead(-2), DateBuilder.givenDate(12, 12, 1972));
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

    @Test
    public void testToString() {
        Locale.setDefault(Locale.GERMANY);
        assertEquals(DateBuilder.givenDate(14, 12, 1972).toString(), "14.12.1972");
        Locale.setDefault(Locale.US);
        assertEquals(DateBuilder.givenDate(14, 12, 1972).toString(), "Dec 14, 1972");
        Locale.setDefault(Locale.UK);
        assertEquals(DateBuilder.givenDate(14, 12, 1972).toString(), "14-Dec-1972");
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
