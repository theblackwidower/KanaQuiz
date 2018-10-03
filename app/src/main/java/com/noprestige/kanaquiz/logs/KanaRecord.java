package com.noprestige.kanaquiz.logs;

import org.joda.time.LocalDate;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "kana_records", primaryKeys = {"date", "kana"})
public class KanaRecord
{
    @ColumnInfo(name = "date")
    @NonNull
    public LocalDate date;

    @ColumnInfo(name = "kana")
    @NonNull
    public String kana;

    @ColumnInfo(name = "correct_answers")
    public int correctAnswers;

    @ColumnInfo(name = "incorrect_answers")
    public int incorrectAnswers;

    public KanaRecord(String kana)
    {
        date = LocalDate.now();
        this.kana = kana;
        correctAnswers = 0;
        incorrectAnswers = 0;
    }
}
