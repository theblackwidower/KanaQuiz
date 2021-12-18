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

import android.app.Application;
import android.database.Cursor;

import com.noprestige.kanaquiz.questions.QuestionType;

import java.time.LocalDate;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {DailyRecord.class, QuestionRecord.class, IncorrectAnswerRecord.class}, version = 4)
@TypeConverters(LogTypeConversion.class)
public abstract class LogDatabase extends RoomDatabase
{
    public static LogDao DAO;

    protected abstract LogDao logDao();

    public static void initialize(Application context)
    {
        if (DAO == null)
            DAO = Room.databaseBuilder(context, LogDatabase.class, "user-logs")
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build().logDao();
    }

    public static final Migration MIGRATION_1_2 = new Migration(1, 2)
    {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {
            //rebuilding entire table because ALTER TABLE can't delete columns or change data types
            database.execSQL("ALTER TABLE daily_record RENAME TO old_daily_record");
            database.execSQL("CREATE TABLE IF NOT EXISTS daily_record (date INTEGER, correct_answers REAL NOT NULL, " +
                    "total_answers INTEGER NOT NULL, PRIMARY KEY(date))");
            database.execSQL(
                    "INSERT INTO daily_record (date, correct_answers, total_answers) SELECT date, correct_answers, " +
                            "correct_answers + incorrect_answers FROM old_daily_record");
            database.execSQL("DROP TABLE old_daily_record");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3)
    {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {
            int currentDate = LogTypeConversion.dateToTimestamp(LocalDate.now());

            //rebuilding tables because ALTER TABLE can't alter, or even add to, the primary key
            database.execSQL("ALTER TABLE kana_records RENAME TO old_kana_records");
            database.execSQL("CREATE TABLE IF NOT EXISTS kana_records (date INTEGER NOT NULL, kana TEXT NOT NULL, " +
                    "correct_answers INTEGER NOT NULL, incorrect_answers INTEGER NOT NULL, PRIMARY KEY(date, " +
                    "kana))");
            database.execSQL(
                    "INSERT INTO kana_records (date, kana, correct_answers, incorrect_answers) SELECT '" + currentDate +
                            "', kana, correct_answers, incorrect_answers FROM old_kana_records");
            database.execSQL("DROP TABLE old_kana_records");

            database.execSQL("ALTER TABLE incorrect_answers RENAME TO old_incorrect_answers");
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS incorrect_answers (date INTEGER NOT NULL, kana TEXT NOT NULL, " +
                            "incorrect_romanji TEXT NOT NULL, occurrences INTEGER NOT NULL, PRIMARY KEY(date, kana, " +
                            "incorrect_romanji))");
            database.execSQL("INSERT INTO incorrect_answers (date, kana, incorrect_romanji, occurrences) SELECT '" +
                    currentDate + "', kana, incorrect_romanji, occurrences FROM old_incorrect_answers");
            database.execSQL("DROP TABLE old_incorrect_answers");
        }
    };

    private static final QuestionType identifyQuestion(CharSequence question)
    {
        int kanaCount = 0;
        int kanjiCount = 0;
        int latinCount = 0;
        for (int i = 0; i < question.length(); i++)
        {
            if ((question.charAt(i) >= 0x3040) && (question.charAt(i) <= 0x30FF))
                kanaCount++;
            else if (((question.charAt(i) >= 0x3400) && (question.charAt(i) <= 0x4DB5)) ||
                    ((question.charAt(i) >= 0x4E00) && (question.charAt(i) <= 0x9FCB)) ||
                    ((question.charAt(i) >= 0xF900) && (question.charAt(i) <= 0xFA6A)))
                kanjiCount++;
            else if (((question.charAt(i) >= 0x0041) && (question.charAt(i) <= 0x005A)) ||
                    ((question.charAt(i) >= 0x0061) && (question.charAt(i) <= 0x007A)))
                latinCount++;
        }

        if ((kanaCount > 0) && (kanaCount <= 3) && (kanjiCount == 0) && (latinCount == 0))
            return QuestionType.KANA;
        else if ((kanaCount == 0) && (kanjiCount == 1) && (latinCount == 0))
            return QuestionType.KANJI;
        else
            return QuestionType.VOCABULARY;
    }

    static final int TEMP_TYPE = 'x';

    public static final Migration MIGRATION_3_4 = new Migration(3, 4)
    {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {
            //rebuilding tables because ALTER TABLE can't alter, or even add to, the primary key
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS question_records (date INTEGER NOT NULL, question TEXT NOT NULL, type" +
                            " INTEGER NOT NULL, correct_answers INTEGER NOT NULL, incorrect_answers INTEGER NOT NULL," +
                            " PRIMARY KEY(date, question, type))");
            database.execSQL(
                    "INSERT INTO question_records (date, question, type, correct_answers, incorrect_answers) SELECT " +
                            "date, kana, " + TEMP_TYPE + ", correct_answers, incorrect_answers FROM kana_records");
            database.execSQL("DROP TABLE kana_records");

            database.execSQL("ALTER TABLE incorrect_answers RENAME TO old_incorrect_answers");
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS incorrect_answers (date INTEGER NOT NULL, question TEXT NOT NULL, " +
                            "type INTEGER NOT NULL, incorrect_answer TEXT NOT NULL, occurrences INTEGER NOT NULL, " +
                            "PRIMARY KEY(date, question, type, incorrect_answer))");
            database.execSQL(
                    "INSERT INTO incorrect_answers (date, question, type, incorrect_answer, occurrences) SELECT date," +
                            " kana, " + TEMP_TYPE + ", incorrect_romanji, occurrences FROM old_incorrect_answers");
            database.execSQL("DROP TABLE old_incorrect_answers");

            Cursor data = database.query("SELECT DISTINCT question FROM question_records WHERE type = " + TEMP_TYPE +
                    " UNION SELECT DISTINCT question FROM incorrect_answers WHERE type = " + TEMP_TYPE);

            if (data.getCount() > 0)
            {
                int questionIndex = data.getColumnIndex("question");

                while (!data.isLast())
                {
                    data.moveToNext();

                    String questionValue = data.getString(questionIndex);
                    int typeId = LogTypeConversion.toCharFromType(identifyQuestion(questionValue));

                    database.execSQL("UPDATE question_records SET type = ? WHERE question = ?",
                            new Object[]{typeId, questionValue});
                    database.execSQL("UPDATE incorrect_answers SET type = ? WHERE question = ?",
                            new Object[]{typeId, questionValue});
                }
            }
        }
    };
}
