/*
 *    Copyright 2021 T Duke Perry
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.noprestige.kanaquiz.logs;

import android.os.AsyncTask;

import com.noprestige.kanaquiz.Fraction;
import com.noprestige.kanaquiz.questions.QuestionType;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class LogDao
{
    static class GetQuestionPercentage extends AsyncTask<String, Void, Float>
    {
        @Override
        protected Float doInBackground(String... data)
        {
            String question = data[0];
            QuestionType type = QuestionType.valueOf(data[1]);
            QuestionRecord[] records = LogDatabase.DAO.getQuestionRecord(question, type);
            if (records.length <= 0)
                return null;
            else
            {
                int totalCorrect = 0;
                int totalIncorrect = 0;
                for (QuestionRecord record : records)
                {
                    totalCorrect += record.getCorrectAnswers();
                    totalIncorrect += record.getIncorrectAnswers();
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
            QuestionType type = QuestionType.valueOf(data[1]);
            String answer = data[2];
            IncorrectAnswerRecord[] records = LogDatabase.DAO.getAnswerRecord(question, type, answer);
            if (records.length <= 0)
                return 0;
            else
            {
                int totalOccurrences = 0;
                for (IncorrectAnswerRecord record : records)
                    totalOccurrences += record.getOccurrences();
                return totalOccurrences;
            }
        }
    }

    public static void reportCorrectAnswer(String question, QuestionType type)
    {
        //ref: https://www.codespeedy.com/multithreading-in-java/
        new Thread(() ->
        {
            addTodaysRecord(Fraction.ONE);
            addQuestionRecord(question, type, true);
        }).start();
    }

    public static void reportIncorrectAnswer(String question, QuestionType type, String answer)
    {
        //ref: https://www.codespeedy.com/multithreading-in-java/
        new Thread(() ->
        {
            addTodaysRecord(Fraction.ZERO);
            addQuestionRecord(question, type, false);
            addIncorrectAnswerRecord(question, type, answer);
        }).start();
    }

    public static void reportRetriedCorrectAnswer(String question, QuestionType type, Fraction score)
    {
        //ref: https://www.codespeedy.com/multithreading-in-java/
        new Thread(() ->
        {
            addTodaysRecord(score);
            addQuestionRecord(question, type, false);
        }).start();
    }

    public static void reportIncorrectRetry(String question, QuestionType type, String answer)
    {
        //ref: https://www.codespeedy.com/multithreading-in-java/
        new Thread(() -> addIncorrectAnswerRecord(question, type, answer)).start();
    }

    @Query("SELECT * FROM daily_record WHERE date = :date")
    public abstract DailyRecord getDateRecord(LocalDate date);

    @Query("SELECT * FROM question_records WHERE question = :question AND type = :type")
    abstract QuestionRecord[] getQuestionRecord(String question, QuestionType type);

    @Query("SELECT * FROM incorrect_answers WHERE question = :question AND type = :type AND incorrect_answer = :answer")
    abstract IncorrectAnswerRecord[] getAnswerRecord(String question, QuestionType type, String answer);

    @Query("SELECT * FROM question_records WHERE question = :question AND type = :type AND date = :date")
    abstract QuestionRecord getDaysQuestionRecord(String question, QuestionType type, LocalDate date);

    @Query("SELECT * FROM incorrect_answers WHERE question = :question AND type = :type AND incorrect_answer = " +
            ":answer AND date = :date")
    abstract IncorrectAnswerRecord getDaysAnswerRecord(String question, QuestionType type, String answer,
            LocalDate date);

    @Query("SELECT * FROM daily_record")
    abstract DailyRecord[] getAllDailyRecords();

    @Query("SELECT * FROM question_records")
    abstract QuestionRecord[] getAllQuestionRecords();

    @Query("SELECT * FROM question_records WHERE date = :date")
    abstract QuestionRecord[] getDatesQuestionRecords(LocalDate date);

    @Query("SELECT * FROM incorrect_answers ORDER BY question")
    abstract IncorrectAnswerRecord[] getAllAnswerRecords();

    public static Float getQuestionPercentage(String question, QuestionType type)
    {
        try
        {
            return new GetQuestionPercentage().execute(question, type.toString()).get();
        }
        catch (InterruptedException | ExecutionException ex)
        {
            //if this happens, we have bigger problems
            //noinspection ProhibitedExceptionThrown
            throw new RuntimeException(ex);
        }
    }

    public static int getIncorrectAnswerCount(String question, QuestionType type, String answer)
    {
        try
        {
            return new GetIncorrectAnswerCount().execute(question, type.toString(), answer).get();
        }
        catch (InterruptedException | ExecutionException ex)
        {
            //if this happens, we have bigger problems
            //noinspection ProhibitedExceptionThrown
            throw new RuntimeException(ex);
        }
    }

    static void addTodaysRecord(Fraction score)
    {
        DailyRecord record = LogDatabase.DAO.getDateRecord(LocalDate.now());
        if (record == null)
        {
            record = new DailyRecord();
            LogDatabase.DAO.insertDailyRecord(record);
        }
        if (score.compareTo(Fraction.ZERO) > 0)
            record.addToCorrectAnswers(score);

        record.incrementTotalAnswers();

        LogDatabase.DAO.updateDailyRecord(record);
    }

    static void addQuestionRecord(String question, QuestionType type, boolean isCorrect)
    {
        QuestionRecord record = LogDatabase.DAO.getDaysQuestionRecord(question, type, LocalDate.now());
        if (record == null)
        {
            record = new QuestionRecord(question, type);
            LogDatabase.DAO.insertQuestionRecord(record);
        }
        if (isCorrect)
            record.incrementCorrectAnswers();
        else
            record.incrementIncorrectAnswers();
        LogDatabase.DAO.updateQuestionRecord(record);
    }

    static void addIncorrectAnswerRecord(String question, QuestionType type, String answer)
    {
        IncorrectAnswerRecord record = LogDatabase.DAO.getDaysAnswerRecord(question, type, answer, LocalDate.now());
        if (record == null)
        {
            record = new IncorrectAnswerRecord(question, type, answer);
            LogDatabase.DAO.insertIncorrectAnswer(record);
        }
        else
        {
            record.incrementOccurrences();
            LogDatabase.DAO.updateIncorrectAnswer(record);
        }
    }

    @Query("DELETE FROM daily_record WHERE 1 = 1")
    abstract void deleteAllDailyRecords();

    @Query("DELETE FROM question_records WHERE 1 = 1")
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
