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

import com.noprestige.kanaquiz.questions.QuestionType;

import java.time.LocalDate;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "question_records", primaryKeys = {"date", "question", "type"})
public class QuestionRecord
{
    @ColumnInfo(name = "date")
    @NonNull
    private final LocalDate date;

    @ColumnInfo(name = "question")
    @NonNull
    private final String question;

    @ColumnInfo(name = "type")
    @NonNull
    private final QuestionType type;

    @ColumnInfo(name = "correct_answers")
    private int correctAnswers;

    @ColumnInfo(name = "incorrect_answers")
    private int incorrectAnswers;

    @Ignore
    public QuestionRecord(@NonNull String question, @NonNull QuestionType type)
    {
        date = LocalDate.now();
        this.question = question;
        this.type = type;
        correctAnswers = 0;
        incorrectAnswers = 0;
    }

    QuestionRecord(@NonNull LocalDate date, @NonNull String question, @NonNull QuestionType type, int correctAnswers,
            int incorrectAnswers)
    {
        this.date = date;
        this.question = question;
        this.type = type;
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

    @NonNull
    public QuestionType getType()
    {
        return type;
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
