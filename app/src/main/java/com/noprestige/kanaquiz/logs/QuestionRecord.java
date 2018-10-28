package com.noprestige.kanaquiz.logs;

import org.threeten.bp.LocalDate;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

//TODO: Edit field name 'kana' to 'question' and table name from 'kana_records' to 'question_records' on next major,
// migration-requiring database change
@Entity(tableName = "kana_records", primaryKeys = {"date", "kana"})
public class QuestionRecord
{
    @ColumnInfo(name = "date")
    @NonNull
    private final LocalDate date;

    @ColumnInfo(name = "kana")
    @NonNull
    private final String question;

    @ColumnInfo(name = "correct_answers")
    private int correctAnswers;

    @ColumnInfo(name = "incorrect_answers")
    private int incorrectAnswers;

    @Ignore
    public QuestionRecord(String question)
    {
        date = LocalDate.now();
        this.question = question;
        correctAnswers = 0;
        incorrectAnswers = 0;
    }

    QuestionRecord(LocalDate date, String question, int correctAnswers, int incorrectAnswers)
    {
        this.date = date;
        this.question = question;
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = incorrectAnswers;
    }

    @NonNull
    public LocalDate getDate()
    {
        return date;
    }

    @NonNull
    public String getQuestion()
    {
        return question;
    }

    public int getCorrectAnswers()
    {
        return correctAnswers;
    }

    public int getIncorrectAnswers()
    {
        return incorrectAnswers;
    }


    public void incrementCorrectAnswers()
    {
        correctAnswers++;
    }

    public void incrementIncorrectAnswers()
    {
        incorrectAnswers++;
    }
}
