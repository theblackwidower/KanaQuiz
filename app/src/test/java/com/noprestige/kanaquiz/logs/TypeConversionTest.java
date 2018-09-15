package com.noprestige.kanaquiz.logs;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TypeConversionTest
{
    private void assertConversion(int year, int month, int day, int all)
    {
        LocalDate dateObject = new LocalDate(year, month, day);
        assertThat(LogTypeConversion.dateToTimestamp(dateObject), is(all));
    }

    @Test
    public void testConverter()
    {
        assertConversion(2013, DateTimeConstants.DECEMBER, 19, 20131219);
        assertConversion(2015, DateTimeConstants.JUNE, 12, 20150612);
        assertConversion(1995, DateTimeConstants.JANUARY, 5, 19950105);
        assertConversion(2025, DateTimeConstants.SEPTEMBER, 21, 20250921);
    }
}
