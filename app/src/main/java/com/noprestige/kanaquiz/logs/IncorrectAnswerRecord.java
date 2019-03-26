package com.noprestige.kanaquiz.logs;

import org.threeten.bp.LocalDate;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

//TODO: Edit field names 'kana' and 'incorrect_romanji' to 'question' and 'incorrect_answer' on next major,
// migration-requiring database change
@Entity(tableName = "incorrect_answers", primaryKeys = {"date", "kana", "incorrect_romanji"})
public class IncorrectAnswerRecord
{
    @ColumnInfo(name = "date")
    @NonNull
    private final LocalDate date;

    @ColumnInfo(name = "kana")
    @NonNull
    private final String question;

    @ColumnInfo(name = "incorrect_romanji")
    @NonNull
    private final String incorrectAnswer;

    @ColumnInfo(name = "occurrences")
    private int occurrences;

    @Ignore
    public IncorrectAnswerRecord(String question, String incorrectAnswer)
    {
        date = LocalDate.now();
        this.question = question;
        this.incorrectAnswer = incorrectAnswer;
        occurrences = 1;
    }

    IncorrectAnswerRecord(LocalDate date, String question, String incorrectAnswer, int occurrences)
    {
        this.date = date;
        this.question = question;
        this.incorrectAnswer = incorrectAnswer;
        this.occurrences = occurrences;
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

    @NonNull
    public String getIncorrectAnswer()
    {
        return incorrectAnswer;
    }

    public int getOccurrences()
    {
        return occurrences;
    }

    public void incrementOccurrences()
    {
        occurrences++;
    }
}
