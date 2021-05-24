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

import java.time.LocalDate;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "daily_record")
public class DailyRecord
{
    @PrimaryKey
    @ColumnInfo(name = "date")
    private final LocalDate date;

    @ColumnInfo(name = "correct_answers")
    private float correctAnswers;

    @ColumnInfo(name = "total_answers")
    private int totalAnswers;

    @Ignore
    public DailyRecord()
    {
        date = LocalDate.now();
        correctAnswers = 0;
        totalAnswers = 0;
    }

    DailyRecord(LocalDate date, float correctAnswers, int totalAnswers)
    {
        this.date = date;
        this.correctAnswers = correctAnswers;
        this.totalAnswers = totalAnswers;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public float getCorrectAnswers()
    {
        return correctAnswers;
    }

    public int getTotalAnswers()
    {
        return totalAnswers;
    }

    public void addToCorrectAnswers(float score)
    {
        correctAnswers += score;
    }

    public void incrementTotalAnswers()
    {
        totalAnswers++;
    }
}
