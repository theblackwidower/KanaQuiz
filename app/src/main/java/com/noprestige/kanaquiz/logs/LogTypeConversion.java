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

import com.noprestige.kanaquiz.Fraction;

import com.noprestige.kanaquiz.questions.QuestionType;

import java.time.LocalDate;

import androidx.room.TypeConverter;

import static com.noprestige.kanaquiz.questions.QuestionType.KANA;
import static com.noprestige.kanaquiz.questions.QuestionType.KANJI;
import static com.noprestige.kanaquiz.questions.QuestionType.KUN_YOMI;
import static com.noprestige.kanaquiz.questions.QuestionType.ON_YOMI;
import static com.noprestige.kanaquiz.questions.QuestionType.VOCABULARY;

public final class LogTypeConversion
{
    private LogTypeConversion() {}

    @TypeConverter
    public static LocalDate fromTimestamp(Integer value)
    {
        return (value == null) ? null : LocalDate.of(value / 10000, (value % 10000) / 100, value % 100);
    }

    @TypeConverter
    public static Integer dateToTimestamp(LocalDate date)
    {
        return (date == null) ? null : ((date.getYear() * 10000) + (date.getMonthValue() * 100) + date.getDayOfMonth());
    }

    @TypeConverter
    public static QuestionType fromCharToType(char type)
    {
        switch (type)
        {
            case 'a':
                return KANA;
            case 'v':
                return VOCABULARY;
            case 'j':
                return KANJI;
            case 'u':
                return KUN_YOMI;
            case 'o':
                return ON_YOMI;
            default:
                throw new IllegalArgumentException("'" + type + "' is not representative of a valid type.");
        }
    }

    @TypeConverter
    public static char toCharFromType(QuestionType type)
    {
        switch (type)
        {
            case KANA:
                return 'a';
            case VOCABULARY:
                return 'v';
            case KANJI:
                return 'j';
            case KUN_YOMI:
                return 'u';
            case ON_YOMI:
                return 'o';
            default:
                throw new IllegalArgumentException(type + " is not a valid type.");
        }
    }

    @TypeConverter
    public static Fraction toFraction(Float value)
    {
        return Fraction.fromFloat(value);
    }

    @TypeConverter
    public static Float fromFraction(Fraction value)
    {
        return value.getDecimal();
    }
}
