package com.noprestige.kanaquiz.logs;

import org.joda.time.LocalDate;

import androidx.room.TypeConverter;

public class LogTypeConversion
{
    @TypeConverter
    public static LocalDate fromTimestamp(Integer value)
    {
        return (value == null) ? null : new LocalDate(value / 10000, (value % 10000) / 100, value % 100);
    }

    @TypeConverter
    public static Integer dateToTimestamp(LocalDate date)
    {
        return (date == null) ? null :
                ((date.getYear() * 10000) + (date.getMonthOfYear() * 100) + date.getDayOfMonth());
    }
}
