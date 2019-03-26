package com.noprestige.kanaquiz.logs;

import org.threeten.bp.LocalDate;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "daily_record")
public class DailyRecord
{
    @PrimaryKey
    @ColumnInfo(name = "date")
    private final LocalDate date;

    @ColumnInfo(name = "correct_answers")
    private float correctAnswers;

    @ColumnInfo(name = "total_answers")
    private int totalAnswers;

    @Ignore
    public DailyRecord()
    {
        date = LocalDate.now();
        correctAnswers = 0;
        totalAnswers = 0;
    }

    DailyRecord(LocalDate date, float correctAnswers, int totalAnswers)
    {
        this.date = date;
        this.correctAnswers = correctAnswers;
        this.totalAnswers = totalAnswers;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public float getCorrectAnswers()
    {
        return correctAnswers;
    }

    public int getTotalAnswers()
    {
        return totalAnswers;
    }

    public void addToCorrectAnswers(float score)
    {
        correctAnswers += score;
    }

    public void incrementTotalAnswers()
    {
        totalAnswers++;
    }
}
