package com.noprestige.kanaquiz.logs;

import org.threeten.bp.LocalDate;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

//TODO: Edit field name 'kana' to 'question' and table name from 'kana_records' to 'question_records' on next major,
// migration-requiring database change
@Entity(tableName = "kana_records", primaryKeys = {"date", "kana"})
public class QuestionRecord
{
    @ColumnInfo(name = "date")
    @NonNull
    public LocalDate date;

    @ColumnInfo(name = "kana")
    @NonNull
    public String question;

    @ColumnInfo(name = "correct_answers")
    public int correctAnswers;

    @ColumnInfo(name = "incorrect_answers")
    public int incorrectAnswers;

    public QuestionRecord(String question)
    {
        date = LocalDate.now();
        this.question = question;
        correctAnswers = 0;
        incorrectAnswers = 0;
    }
}
