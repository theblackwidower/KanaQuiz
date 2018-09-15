package com.noprestige.kanaquiz.logs;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;

public class TypeConversionTest
{
    private void assertConversion(int year, int month, int day, int all)
    {
        Date dateObject = new GregorianCalendar(year, month, day).getTime();
        Assert.assertThat(LogTypeConversion.dateToTimestamp(dateObject), is(all));
    }

    @Test
    public void testConverter()
    {
        assertConversion(2013, Calendar.DECEMBER, 19, 20131219);
        assertConversion(2015, Calendar.JUNE, 12, 20150612);
        assertConversion(1995, Calendar.JANUARY, 5, 19950105);
        assertConversion(2025, Calendar.SEPTEMBER, 21, 20250921);
    }
}
