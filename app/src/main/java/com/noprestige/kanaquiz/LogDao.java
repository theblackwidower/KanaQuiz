package com.noprestige.kanaquiz;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;

@Dao
public abstract class LogDao
{
    @Query("SELECT * FROM daily_record WHERE date = :date")
    abstract LogDailyRecord getDateRecord(Date date);

    @Query("SELECT * FROM kana_records WHERE kana = :kana")
    abstract LogKanaRecord getKanaRecord(String kana);

    @Query("SELECT * FROM incorrect_answers WHERE kana = :kana AND incorrect_romanji = :romanji")
    abstract LogIncorrectAnswer getAnswerRecord(String kana, String romanji);

    @Query("SELECT * FROM daily_record")
    abstract LogDailyRecord[] getAllDailyRecords();

    @Query("SELECT * FROM kana_records")
    abstract LogKanaRecord[] getAllKanaRecords();

    @Query("SELECT * FROM incorrect_answers ORDER BY kana")
    abstract LogIncorrectAnswer[] getAllAnswerRecords();

    public float getDailyPercentage(Date date)
    {
        LogDailyRecord record = getDateRecord(date);
        return (float) record.correct_answers / (float) (record.incorrect_answers + record.correct_answers);
    }

    public float getKanaPercentage(String kana)
    {
        LogKanaRecord record = getKanaRecord(kana);
        if (record == null)
            return 0.9f;
        else
            return (float) record.correct_answers / (float) (record.incorrect_answers + record.correct_answers);
    }

    public int getIncorrectAnswerCount(String kana, String romanji)
    {
        LogIncorrectAnswer record = getAnswerRecord(kana, romanji);
        if (record == null)
            return 0;
        else
            return record.occurrences;
    }

    void addTodaysRecord(boolean isCorrect)
    {
        LogDailyRecord record = getDateRecord(new Date());
        if (record == null)
        {
            record = new LogDailyRecord();
            insertDailyRecord(record);
        }
        if (isCorrect)
            record.correct_answers++;
        else
            record.incorrect_answers++;
        updateDailyRecord(record);
    }

    void addKanaRecord(String kana, boolean isCorrect)
    {
        LogKanaRecord record = getKanaRecord(kana);
        if (record == null)
        {
            record = new LogKanaRecord(kana);
            insertKanaRecord(record);
        }
        if (isCorrect)
            record.correct_answers++;
        else
            record.incorrect_answers++;
        updateKanaRecord(record);
    }

    void addIncorrectAnswerRecord(String kana, String romanji)
    {
        LogIncorrectAnswer record = getAnswerRecord(kana, romanji);
        if (record == null)
        {
            record = new LogIncorrectAnswer(kana, romanji);
            insertIncorrectAnswer(record);
        }
        else
        {
            record.occurrences++;
            updateIncorrectAnswer(record);
        }
    }

    public void reportCorrectAnswer(String kana)
    {
        addTodaysRecord(true);
        addKanaRecord(kana, true);
    }

    public void reportIncorrectAnswer(String kana, String romanji)
    {
        addTodaysRecord(false);
        addKanaRecord(kana, false);
        addIncorrectAnswerRecord(kana, romanji);
    }

    public void reportIncorrectRetry(String kana, String romanji)
    {
        addIncorrectAnswerRecord(kana, romanji);
    }

    @Query("DELETE FROM daily_record WHERE 1 = 1")
    abstract void deleteAllDailyRecords();

    @Query("DELETE FROM kana_records WHERE 1 = 1")
    abstract void deleteAllKanaRecords();

    @Query("DELETE FROM incorrect_answers WHERE 1 = 1")
    abstract void deleteAllAnswerRecords();

    public void deleteAll()
    {
        deleteAllDailyRecords();
        deleteAllKanaRecords();
        deleteAllAnswerRecords();
    }

    @Insert
    abstract void insertDailyRecord(LogDailyRecord... record);

    @Update
    abstract void updateDailyRecord(LogDailyRecord... record);

    @Delete
    abstract void deleteDailyRecord(LogDailyRecord record);

    @Insert
    abstract void insertKanaRecord(LogKanaRecord... record);

    @Update
    abstract void updateKanaRecord(LogKanaRecord... record);

    @Delete
    abstract void deleteKanaRecord(LogKanaRecord record);

    @Insert
    abstract void insertIncorrectAnswer(LogIncorrectAnswer... record);

    @Update
    abstract void updateIncorrectAnswer(LogIncorrectAnswer... record);

    @Delete
    abstract void deleteIncorrectAnswer(LogIncorrectAnswer record);
}
