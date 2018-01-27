package com.noprestige.kanaquiz;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class LogTypeConversion
{
    @TypeConverter
    public Date fromTimestamp(Integer value)
    {
        return value == null ? null :
                new GregorianCalendar(value / 10000, value % 10000 / 100 - 1, value % 100).getTime();
    }

    @TypeConverter
    public static Integer dateToTimestamp(Date date)
    {
        if (date == null)
            return null;
        else
        {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            return calendar.get(YEAR) * 10000 + (calendar.get(MONTH) + 1) * 100 + calendar.get(DAY_OF_MONTH);
        }
    }
}
