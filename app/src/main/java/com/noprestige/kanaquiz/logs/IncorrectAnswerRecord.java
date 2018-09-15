package com.noprestige.kanaquiz.logs;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import org.joda.time.LocalDate;

@Entity(tableName = "incorrect_answers", primaryKeys = {"date", "kana", "incorrect_romanji"})
public class IncorrectAnswerRecord
{
    @ColumnInfo(name = "date")
    @NonNull
    public LocalDate date;

    @ColumnInfo(name = "kana")
    @NonNull
    public String kana;

    @ColumnInfo(name = "incorrect_romanji")
    @NonNull
    public String incorrect_romanji;

    @ColumnInfo(name = "occurrences")
    public int occurrences;

    public IncorrectAnswerRecord(String kana, String incorrect_romanji)
    {
        date = LocalDate.now();
        this.kana = kana;
        this.incorrect_romanji = incorrect_romanji;
        occurrences = 1;
    }
}
