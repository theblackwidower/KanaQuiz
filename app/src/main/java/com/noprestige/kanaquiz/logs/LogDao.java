package com.noprestige.kanaquiz.logs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.os.AsyncTask;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Dao
public abstract class LogDao
{
    private static class GetKanaPercentage extends AsyncTask<String, Void, Float>
    {
        @Override
        protected Float doInBackground(String... data)
        {
            String kana = data[0];
            KanaRecord[] records = LogDatabase.DAO.getKanaRecord(kana);
            if (records.length <= 0)
                return null;
            else
            {
                int totalCorrect = 0;
                int totalIncorrect = 0;
                for (KanaRecord record : records)
                {
                    totalCorrect += record.correct_answers;
                    totalIncorrect += record.incorrect_answers;
                }
                return (float) totalCorrect / (float) (totalIncorrect + totalCorrect);
            }
        }
    }

    private static class GetIncorrectAnswerCount extends AsyncTask<String, Void, Integer>
    {
        @Override
        protected Integer doInBackground(String... data)
        {
            String kana = data[0];
            String romanji = data[1];
            IncorrectAnswerRecord[] records = LogDatabase.DAO.getAnswerRecord(kana, romanji);
            if (records.length <= 0)
                return 0;
            else
            {
                int totalOccurrences = 0;
                for (IncorrectAnswerRecord record : records)
                    totalOccurrences += record.occurrences;
                return totalOccurrences;
            }
        }
    }

    private static class ReportCorrectAnswer extends AsyncTask<String, Void, Void>
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

    private static class ReportIncorrectAnswer extends AsyncTask<String, Void, Void>
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

    private static class ReportRetriedCorrectAnswer extends AsyncTask<String, Void, Void>
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

    private static class ReportIncorrectRetry extends AsyncTask<String, Void, Void>
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
    abstract KanaRecord[] getKanaRecord(String kana);

    @Query("SELECT * FROM incorrect_answers WHERE kana = :kana AND incorrect_romanji = :romanji")
    abstract IncorrectAnswerRecord[] getAnswerRecord(String kana, String romanji);

    @Query("SELECT * FROM kana_records WHERE kana = :kana AND date = :date")
    abstract KanaRecord getDaysKanaRecord(String kana, Date date);

    @Query("SELECT * FROM incorrect_answers WHERE kana = :kana AND incorrect_romanji = :romanji AND date = :date")
    abstract IncorrectAnswerRecord getDaysAnswerRecord(String kana, String romanji, Date date);

    @Query("SELECT * FROM daily_record")
    abstract DailyRecord[] getAllDailyRecords();

    @Query("SELECT * FROM kana_records")
    abstract KanaRecord[] getAllKanaRecords();

    @Query("SELECT * FROM incorrect_answers ORDER BY kana")
    abstract IncorrectAnswerRecord[] getAllAnswerRecords();

    public static Float getKanaPercentage(String kana)
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

    public static int getIncorrectAnswerCount(String kana, String romanji)
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

    private static void addTodaysRecord(float score)
    {
        DailyRecord record = LogDatabase.DAO.getDateRecord(new Date());
        if (record == null)
        {
            record = new DailyRecord();
            LogDatabase.DAO.insertDailyRecord(record);
        }
        if (score > 0)
            record.correct_answers += score;

        record.total_answers++;

        LogDatabase.DAO.updateDailyRecord(record);
    }

    private static void addKanaRecord(String kana, boolean isCorrect)
    {
        KanaRecord record = LogDatabase.DAO.getDaysKanaRecord(kana, new Date());
        if (record == null)
        {
            record = new KanaRecord(kana);
            LogDatabase.DAO.insertKanaRecord(record);
        }
        if (isCorrect)
            record.correct_answers++;
        else
            record.incorrect_answers++;
        LogDatabase.DAO.updateKanaRecord(record);
    }

    private static void addIncorrectAnswerRecord(String kana, String romanji)
    {
        IncorrectAnswerRecord record = LogDatabase.DAO.getDaysAnswerRecord(kana, romanji, new Date());
        if (record == null)
        {
            record = new IncorrectAnswerRecord(kana, romanji);
            LogDatabase.DAO.insertIncorrectAnswer(record);
        }
        else
        {
            record.occurrences++;
            LogDatabase.DAO.updateIncorrectAnswer(record);
        }
    }

    public static void reportCorrectAnswer(String kana)
    {
        new ReportCorrectAnswer().execute(kana);
    }

    public static void reportIncorrectAnswer(String kana, String romanji)
    {
        new ReportIncorrectAnswer().execute(kana, romanji);
    }

    public static void reportRetriedCorrectAnswer(String kana, float score)
    {
        new ReportRetriedCorrectAnswer().execute(kana, Float.toString(score));
    }

    public static void reportIncorrectRetry(String kana, String romanji)
    {
        new ReportIncorrectRetry().execute(kana, romanji);
    }

    @Query("DELETE FROM daily_record WHERE 1 = 1")
    abstract void deleteAllDailyRecords();

    @Query("DELETE FROM kana_records WHERE 1 = 1")
    abstract void deleteAllKanaRecords();

    @Query("DELETE FROM incorrect_answers WHERE 1 = 1")
    abstract void deleteAllAnswerRecords();

    public static void deleteAll()
    {
        LogDatabase.DAO.deleteAllDailyRecords();
        LogDatabase.DAO.deleteAllKanaRecords();
        LogDatabase.DAO.deleteAllAnswerRecords();
    }

    @Insert
    abstract void insertDailyRecord(DailyRecord... record);

    @Update
    abstract void updateDailyRecord(DailyRecord... record);

    @Insert
    abstract void insertKanaRecord(KanaRecord... record);

    @Update
    abstract void updateKanaRecord(KanaRecord... record);

    @Insert
    abstract void insertIncorrectAnswer(IncorrectAnswerRecord... record);

    @Update
    abstract void updateIncorrectAnswer(IncorrectAnswerRecord... record);
}
