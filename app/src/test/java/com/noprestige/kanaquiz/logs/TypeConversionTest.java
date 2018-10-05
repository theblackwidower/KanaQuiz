package com.noprestige.kanaquiz.logs;

import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TypeConversionTest
{
    private void assertConversion(int year, Month month, int day, int all)
    {
        LocalDate dateObject = LocalDate.of(year, month, day);
        assertThat(LogTypeConversion.dateToTimestamp(dateObject), is(all));

        LocalDate testObject = LogTypeConversion.fromTimestamp(all);
        assertThat(testObject.getYear(), is(year));
        assertThat(testObject.getMonth(), is(month));
        assertThat(testObject.getDayOfMonth(), is(day));
    }

    @Test
    public void testConverter()
    {
        assertConversion(2013, Month.DECEMBER, 19, 20131219);
        assertConversion(2015, Month.JUNE, 12, 20150612);
        assertConversion(1995, Month.JANUARY, 5, 19950105);
        assertConversion(2025, Month.SEPTEMBER, 21, 20250921);

        assertThat(LogTypeConversion.dateToTimestamp(null), is(nullValue()));
        assertThat(LogTypeConversion.fromTimestamp(null), is(nullValue()));
    }
}
