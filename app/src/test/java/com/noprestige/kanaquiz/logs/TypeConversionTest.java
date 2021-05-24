/*
 *    Copyright 2021 T Duke Perry
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.noprestige.kanaquiz.logs;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

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
