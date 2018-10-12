package com.noprestige.kanaquiz.logs;

import android.os.AsyncTask;

import org.threeten.bp.LocalDate;

import java.util.concurrent.ExecutionException;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class LogDao
{
    static class GetKanaPercentage extends AsyncTask<String, Void, Float>
    {
        @Override
        protected Float doInBackground(String... data)
        {
            String question = data[0];
            QuestionRecord[] records = LogDatabase.DAO.getQuestionRecord(question);
            if (records.length <= 0)
                return null;
            else
            {
                int totalCorrect = 0;
                int totalIncorrect = 0;
                for (QuestionRecord record : records)
                {
                    totalCorrect += record.correctAnswers;
                    totalIncorrect += record.incorrectAnswers;
                }
                return (float) totalCorrect / (float) (totalIncorrect + totalCorrect);
            }
        }
    }

    static class GetIncorrectAnswerCount extends AsyncTask<String, Void, Integer>
    {
        @Override
        protected Integer doInBackground(String... data)
        {
            String question = data[0];
            String answer = data[1];
            IncorrectAnswerRecord[] records = LogDatabase.DAO.getAnswerRecord(question, answer);
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

    static class ReportCorrectAnswer extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... data)
        {
            String question = data[0];

            addTodaysRecord(1);
            addQuestionRecord(question, true);

            return null;
        }
    }

    static class ReportIncorrectAnswer extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... data)
        {
            String question = data[0];
            String answer = data[1];

            addTodaysRecord(0);
            addQuestionRecord(question, false);
            addIncorrectAnswerRecord(question, answer);

            return null;
        }
    }

    static class ReportRetriedCorrectAnswer extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... data)
        {
            String question = data[0];
            float score = Float.parseFloat(data[1]);

            addTodaysRecord(score);
            addQuestionRecord(question, false);

            return null;
        }
    }

    static class ReportIncorrectRetry extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... data)
        {
            String question = data[0];
            String answer = data[1];

            addIncorrectAnswerRecord(question, answer);

            return null;
        }
    }

    @Query("SELECT * FROM daily_record WHERE date = :date")
    public abstract DailyRecord getDateRecord(LocalDate date);

    @Query("SELECT * FROM kana_records WHERE kana = :question")
    abstract QuestionRecord[] getQuestionRecord(String question);

    @Query("SELECT * FROM incorrect_answers WHERE kana = :question AND incorrect_romanji = :answer")
    abstract IncorrectAnswerRecord[] getAnswerRecord(String question, String answer);

    @Query("SELECT * FROM kana_records WHERE kana = :question AND date = :date")
    abstract QuestionRecord getDaysQuestionRecord(String question, LocalDate date);

    @Query("SELECT * FROM incorrect_answers WHERE kana = :question AND incorrect_romanji = :answer AND date = :date")
    abstract IncorrectAnswerRecord getDaysAnswerRecord(String question, String answer, LocalDate date);

    @Query("SELECT * FROM daily_record")
    abstract DailyRecord[] getAllDailyRecords();

    @Query("SELECT * FROM kana_records")
    abstract QuestionRecord[] getAllQuestionRecords();

    @Query("SELECT * FROM incorrect_answers ORDER BY kana")
    abstract IncorrectAnswerRecord[] getAllAnswerRecords();

    public static Float getKanaPercentage(String question)
    {
        try
        {
            return new GetKanaPercentage().execute(question).get();
        }
        catch (InterruptedException | ExecutionException ex)
        {
            //if this happens, we have bigger problems
            //noinspection ProhibitedExceptionThrown
            throw new RuntimeException(ex);
        }
    }

    public static int getIncorrectAnswerCount(String question, String answer)
    {
        try
        {
            return new GetIncorrectAnswerCount().execute(question, answer).get();
        }
        catch (InterruptedException | ExecutionException ex)
        {
            //if this happens, we have bigger problems
            //noinspection ProhibitedExceptionThrown
            throw new RuntimeException(ex);
        }
    }

    static void addTodaysRecord(float score)
    {
        DailyRecord record = LogDatabase.DAO.getDateRecord(LocalDate.now());
        if (record == null)
        {
            record = new DailyRecord();
            LogDatabase.DAO.insertDailyRecord(record);
        }
        if (score > 0)
            record.correctAnswers += score;

        record.totalAnswers++;

        LogDatabase.DAO.updateDailyRecord(record);
    }

    static void addQuestionRecord(String question, boolean isCorrect)
    {
        QuestionRecord record = LogDatabase.DAO.getDaysQuestionRecord(question, LocalDate.now());
        if (record == null)
        {
            record = new QuestionRecord(question);
            LogDatabase.DAO.insertQuestionRecord(record);
        }
        if (isCorrect)
            record.correctAnswers++;
        else
            record.incorrectAnswers++;
        LogDatabase.DAO.updateQuestionRecord(record);
    }

    static void addIncorrectAnswerRecord(String question, String answer)
    {
        IncorrectAnswerRecord record = LogDatabase.DAO.getDaysAnswerRecord(question, answer, LocalDate.now());
        if (record == null)
        {
            record = new IncorrectAnswerRecord(question, answer);
            LogDatabase.DAO.insertIncorrectAnswer(record);
        }
        else
        {
            record.occurrences++;
            LogDatabase.DAO.updateIncorrectAnswer(record);
        }
    }

    public static void reportCorrectAnswer(String question)
    {
        new ReportCorrectAnswer().execute(question);
    }

    public static void reportIncorrectAnswer(String question, String answer)
    {
        new ReportIncorrectAnswer().execute(question, answer);
    }

    public static void reportRetriedCorrectAnswer(String question, float score)
    {
        new ReportRetriedCorrectAnswer().execute(question, Float.toString(score));
    }

    public static void reportIncorrectRetry(String question, String answer)
    {
        new ReportIncorrectRetry().execute(question, answer);
    }

    @Query("DELETE FROM daily_record WHERE 1 = 1")
    abstract void deleteAllDailyRecords();

    @Query("DELETE FROM kana_records WHERE 1 = 1")
    abstract void deleteAllQuestionRecords();

    @Query("DELETE FROM incorrect_answers WHERE 1 = 1")
    abstract void deleteAllAnswerRecords();

    public static void deleteAll()
    {
        LogDatabase.DAO.deleteAllDailyRecords();
        LogDatabase.DAO.deleteAllQuestionRecords();
        LogDatabase.DAO.deleteAllAnswerRecords();
    }

    @Insert
    abstract void insertDailyRecord(DailyRecord... record);

    @Update
    abstract void updateDailyRecord(DailyRecord... record);

    @Insert
    abstract void insertQuestionRecord(QuestionRecord... record);

    @Update
    abstract void updateQuestionRecord(QuestionRecord... record);

    @Insert
    abstract void insertIncorrectAnswer(IncorrectAnswerRecord... record);

    @Update
    abstract void updateIncorrectAnswer(IncorrectAnswerRecord... record);
}
