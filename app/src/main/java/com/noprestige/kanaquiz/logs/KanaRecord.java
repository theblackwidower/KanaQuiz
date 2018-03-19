package com.noprestige.kanaquiz.logs;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "kana_records", primaryKeys = {"date", "kana"})
public class KanaRecord
{
    @ColumnInfo(name = "date")
    @NonNull
    public Date date;

    @ColumnInfo(name = "kana")
    @NonNull
    public String kana;

    @ColumnInfo(name = "correct_answers")
    public int correct_answers;

    @ColumnInfo(name = "incorrect_answers")
    public int incorrect_answers;

    public KanaRecord(String kana)
    {
        date = new Date();
        this.kana = kana;
        correct_answers = 0;
        incorrect_answers = 0;
    }
}
