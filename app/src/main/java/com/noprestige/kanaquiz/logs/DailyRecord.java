package com.noprestige.kanaquiz.logs;

import org.threeten.bp.LocalDate;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
