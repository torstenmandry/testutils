package de.javandry.testutils;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class DateBuilderTests {

    @Test
    public void today() {
        Calendar cal = GregorianCalendar.getInstance();
        DateBuilder today = DateBuilder.today();

        assertEqualsCalendar(cal, today);
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
        Calendar cal = GregorianCalendar.getInstance();
        java.util.Date utilDate = cal.getTime();
        DateBuilder dateBuilder = DateBuilder.valueOf(utilDate);

        assertEqualsCalendar(cal, dateBuilder);
    }

    private void assertEqualsCalendar(Calendar cal, DateBuilder today) {
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), today.getDay());
        assertEquals(cal.get(Calendar.MONTH) + 1, today.getMonth());
        assertEquals(cal.get(Calendar.YEAR), today.getYear());
    }
}
