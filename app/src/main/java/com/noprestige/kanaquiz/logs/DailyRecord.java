package com.noprestige.kanaquiz.logs;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.joda.time.LocalDate;

@Entity(tableName = "daily_record")
public class DailyRecord
{
    @PrimaryKey
    @ColumnInfo(name = "date")
    public LocalDate date;

    @ColumnInfo(name = "correct_answers")
    public float correct_answers;

    @ColumnInfo(name = "total_answers")
    public int total_answers;

    public DailyRecord()
    {
        date = LocalDate.now();
        correct_answers = 0;
        total_answers = 0;
    }
}
