package com.noprestige.kanaquiz.logs;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "kana_records")
public class KanaRecord
{
    @PrimaryKey
    @ColumnInfo(name = "kana")
    @NonNull
    public String kana;

    @ColumnInfo(name = "correct_answers")
    public int correct_answers;

    @ColumnInfo(name = "incorrect_answers")
    public int incorrect_answers;

    public KanaRecord(String kana)
    {
        this.kana = kana;
        correct_answers = 0;
        incorrect_answers = 0;
    }
}
