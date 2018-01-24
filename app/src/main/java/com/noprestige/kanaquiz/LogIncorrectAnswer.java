package com.noprestige.kanaquiz;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "incorrect_answers", primaryKeys = {"kana", "incorrect_romanji"})
public class LogIncorrectAnswer
{
    @ColumnInfo(name = "kana")
    @NonNull
    public String kana;

    @ColumnInfo(name = "incorrect_romanji")
    @NonNull
    public String incorrect_romanji;

    @ColumnInfo(name = "occurrences")
    public int occurrences;

    public LogIncorrectAnswer(String kana, String incorrect_romanji)
    {
        this.kana = kana;
        this.incorrect_romanji = incorrect_romanji;
        occurrences = 1;
    }
}
