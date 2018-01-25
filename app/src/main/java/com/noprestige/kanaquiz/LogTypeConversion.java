package com.noprestige.kanaquiz;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;
import java.util.GregorianCalendar;

public class LogTypeConversion
{
    @TypeConverter
    public Date fromTimestamp(Integer value)
    {
        return value == null ? null :
                new GregorianCalendar(value / 10000, value % 10000 / 100, value % 100).getTime();
    }

    @TypeConverter
    public static Integer dateToTimestamp(Date date)
    {
        //TODO: replace deprecated methods
        return date == null ? null : (date.getYear() + 1900) * 10000 + date.getMonth() * 100 + date.getDate();
    }
}
