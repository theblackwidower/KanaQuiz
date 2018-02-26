package com.noprestige.kanaquiz.logs;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.os.AsyncTask;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Dao
public abstract class LogDao
{
    @SuppressLint("StaticFieldLeak")
    private class GetKanaPercentage extends AsyncTask<String, Void, Float>
    {
        @Override
        protected Float doInBackground(String... data)
        {
            String kana = data[0];
            KanaRecord record = getKanaRecord(kana);
            if (record == null)
                return 0.9f;
            else
                return (float) record.correct_answers / (float) (record.incorrect_answers + record.correct_answers);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetIncorrectAnswerCount extends AsyncTask<String, Void, Integer>
    {
        @Override
        protected Integer doInBackground(String... data)
        {
            String kana = data[0];
            String romanji = data[1];
            IncorrectAnswerRecord record = getAnswerRecord(kana, romanji);
            if (record == null)
                return 0;
            else
                return record.occurrences;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ReportCorrectAnswer extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... data)
        {
            String kana = data[0];

            addTodaysRecord(1);
            addKanaRecord(kana, true);

            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ReportIncorrectAnswer extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... data)
        {
            String kana = data[0];
            String romanji = data[1];

            addTodaysRecord(0);
            addKanaRecord(kana, false);
            addIncorrectAnswerRecord(kana, romanji);

            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ReportRetriedCorrectAnswer extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... data)
        {
            String kana = data[0];
            float score = Float.parseFloat(data[1]);

            addTodaysRecord(score);
            addKanaRecord(kana, false);

            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ReportIncorrectRetry extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... data)
        {
            String kana = data[0];
            String romanji = data[1];

            addIncorrectAnswerRecord(kana, romanji);

            return null;
        }
    }

    @Query("SELECT * FROM daily_record WHERE date = :date")
    public abstract DailyRecord getDateRecord(Date date);

    @Query("SELECT * FROM kana_records WHERE kana = :kana")
    abstract KanaRecord getKanaRecord(String kana);

    @Query("SELECT * FROM incorrect_answers WHERE kana = :kana AND incorrect_romanji = :romanji")
    abstract IncorrectAnswerRecord getAnswerRecord(String kana, String romanji);

    @Query("SELECT * FROM daily_record")
    abstract DailyRecord[] getAllDailyRecords();

    @Query("SELECT * FROM kana_records")
    abstract KanaRecord[] getAllKanaRecords();

    @Query("SELECT * FROM incorrect_answers ORDER BY kana")
    abstract IncorrectAnswerRecord[] getAllAnswerRecords();

    public float getDailyPercentage(Date date)
    {
        DailyRecord record = getDateRecord(date);
        return record.correct_answers / (float) (record.total_answers);
    }

    public float getKanaPercentage(String kana)
    {
        try
        {
            return new GetKanaPercentage().execute(kana).get();
        }
        catch (InterruptedException | ExecutionException ex)
        {
            //if this happens, we have bigger problems
            throw new RuntimeException(ex);
        }
    }

    public int getIncorrectAnswerCount(String kana, String romanji)
    {
        try
        {
            return new GetIncorrectAnswerCount().execute(kana, romanji).get();
        }
        catch (InterruptedException | ExecutionException ex)
        {
            //if this happens, we have bigger problems
            throw new RuntimeException(ex);
        }
    }

    private void addTodaysRecord(float score)
    {
        DailyRecord record = getDateRecord(new Date());
        if (record == null)
        {
            record = new DailyRecord();
            insertDailyRecord(record);
        }
        if (score > 0)
            record.correct_answers += score;

        record.total_answers++;

        updateDailyRecord(record);
    }

    private void addKanaRecord(String kana, boolean isCorrect)
    {
        KanaRecord record = getKanaRecord(kana);
        if (record == null)
        {
            record = new KanaRecord(kana);
            insertKanaRecord(record);
        }
        if (isCorrect)
            record.correct_answers++;
        else
            record.incorrect_answers++;
        updateKanaRecord(record);
    }

    private void addIncorrectAnswerRecord(String kana, String romanji)
    {
        IncorrectAnswerRecord record = getAnswerRecord(kana, romanji);
        if (record == null)
        {
            record = new IncorrectAnswerRecord(kana, romanji);
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
        new ReportCorrectAnswer().execute(kana);
    }

    public void reportIncorrectAnswer(String kana, String romanji)
    {
        new ReportIncorrectAnswer().execute(kana, romanji);
    }

    public void reportRetriedCorrectAnswer(String kana, float score)
    {
        new ReportRetriedCorrectAnswer().execute(kana, Float.toString(score));
    }

    public void reportIncorrectRetry(String kana, String romanji)
    {
        new ReportIncorrectRetry().execute(kana, romanji);
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
    abstract void insertDailyRecord(DailyRecord... record);

    @Update
    abstract void updateDailyRecord(DailyRecord... record);

    @Delete
    abstract void deleteDailyRecord(DailyRecord record);

    @Insert
    abstract void insertKanaRecord(KanaRecord... record);

    @Update
    abstract void updateKanaRecord(KanaRecord... record);

    @Delete
    abstract void deleteKanaRecord(KanaRecord record);

    @Insert
    abstract void insertIncorrectAnswer(IncorrectAnswerRecord... record);

    @Update
    abstract void updateIncorrectAnswer(IncorrectAnswerRecord... record);

    @Delete
    abstract void deleteIncorrectAnswer(IncorrectAnswerRecord record);
}
