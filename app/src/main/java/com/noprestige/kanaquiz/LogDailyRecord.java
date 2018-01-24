package com.noprestige.kanaquiz;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "daily_record")
public class LogDailyRecord
{
    @PrimaryKey
    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name = "correct_answers")
    public int correct_answers;

    @ColumnInfo(name = "incorrect_answers")
    public int incorrect_answers;

    public LogDailyRecord()
    {
        date = new Date();
        correct_answers = 0;
        incorrect_answers = 0;
    }
}
