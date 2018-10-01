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
    public float correctAnswers;

    @ColumnInfo(name = "total_answers")
    public int totalAnswers;

    public DailyRecord()
    {
        date = LocalDate.now();
        correctAnswers = 0;
        totalAnswers = 0;
    }
}
