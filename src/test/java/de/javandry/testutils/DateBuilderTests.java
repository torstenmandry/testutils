package de.javandry.testutils;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class DateBuilderTests {

    @Test
    public void today() {
        Calendar cal = new GregorianCalendar();
        DateBuilder today = DateBuilder.today();

        assertEquals(cal.get(Calendar.DAY_OF_MONTH), today.getDay());
        assertEquals(cal.get(Calendar.MONTH) + 1, today.getMonth());
        assertEquals(cal.get(Calendar.YEAR), today.getYear());
    }

    @Test
    public void givenDate() {
        DateBuilder givenDate = DateBuilder.givenDate(14, 12, 1972);

        assertEquals(14, givenDate.getDay());
        assertEquals(12, givenDate.getMonth());
        assertEquals(1972, givenDate.getYear());
    }
}
