package com.noprestige.kanaquiz.logs;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import org.joda.time.LocalDate;

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
    public int correct_answers;

    @ColumnInfo(name = "incorrect_answers")
    public int incorrect_answers;

    public KanaRecord(String kana)
    {
        date = LocalDate.now();
        this.kana = kana;
        correct_answers = 0;
        incorrect_answers = 0;
    }
}
