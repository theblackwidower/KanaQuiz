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

import java.time.LocalDate;

import androidx.room.TypeConverter;

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
    public static Fraction toFraction(Float value)
    {
        return new Fraction(value);
    }

    @TypeConverter
    public static Float fromFraction(Fraction value)
    {
        return value.getDecimal();
    }
}
