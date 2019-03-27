/*
 *    Copyright 2018 T Duke Perry
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
