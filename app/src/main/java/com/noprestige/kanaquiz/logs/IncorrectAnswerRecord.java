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

@Entity(tableName = "incorrect_answers", primaryKeys = {"date", "question", "type", "incorrect_answer"})
public class IncorrectAnswerRecord
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

    @ColumnInfo(name = "incorrect_answer")
    @NonNull
    private final String incorrectAnswer;

    @ColumnInfo(name = "occurrences")
    private int occurrences;

    @Ignore
    public IncorrectAnswerRecord(String question, QuestionType type, String incorrectAnswer)
    {
        date = LocalDate.now();
        this.question = question;
        this.type = type;
        this.incorrectAnswer = incorrectAnswer;
        occurrences = 1;
    }

    IncorrectAnswerRecord(LocalDate date, String question, QuestionType type, String incorrectAnswer, int occurrences)
    {
        this.date = date;
        this.question = question;
        this.type = type;
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
    public QuestionType getType()
    {
        return type;
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
