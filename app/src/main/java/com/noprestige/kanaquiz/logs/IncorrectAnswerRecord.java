package com.noprestige.kanaquiz.logs;

import org.threeten.bp.LocalDate;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

//TODO: Edit field names 'kana' and 'incorrect_romanji' to 'question' and 'incorrect_answer' on next major,
// migration-requiring database change
@Entity(tableName = "incorrect_answers", primaryKeys = {"date", "kana", "incorrect_romanji"})
public class IncorrectAnswerRecord
{
    @ColumnInfo(name = "date")
    @NonNull
    public LocalDate date;

    @ColumnInfo(name = "kana")
    @NonNull
    public String question;

    @ColumnInfo(name = "incorrect_romanji")
    @NonNull
    public String incorrectAnswer;

    @ColumnInfo(name = "occurrences")
    public int occurrences;

    public IncorrectAnswerRecord(String question, String incorrectAnswer)
    {
        date = LocalDate.now();
        this.question = question;
        this.incorrectAnswer = incorrectAnswer;
        occurrences = 1;
    }
}
