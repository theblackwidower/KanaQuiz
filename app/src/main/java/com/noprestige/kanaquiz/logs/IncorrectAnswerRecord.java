package com.noprestige.kanaquiz.logs;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "incorrect_answers", primaryKeys = {"date", "kana", "incorrect_romanji"})
public class IncorrectAnswerRecord
{
    @ColumnInfo(name = "date")
    @NonNull
    public Date date;

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
        date = new Date();
        this.kana = kana;
        this.incorrect_romanji = incorrect_romanji;
        occurrences = 1;
    }
}
