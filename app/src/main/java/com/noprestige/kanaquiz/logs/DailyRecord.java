package com.noprestige.kanaquiz.logs;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "daily_record")
public class DailyRecord
{
    @PrimaryKey
    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name = "correct_answers")
    public float correct_answers;

    @ColumnInfo(name = "total_answers")
    public int total_answers;

    public DailyRecord()
    {
        date = new Date();
        correct_answers = 0;
        total_answers = 0;
    }
}
