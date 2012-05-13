package de.javandry.testutils;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
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

    private void assertEqualsCalendar(Calendar cal, DateBuilder today) {
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), today.getDay());
        assertEquals(cal.get(Calendar.MONTH) + 1, today.getMonth());
        assertEquals(cal.get(Calendar.YEAR), today.getYear());
    }
}
