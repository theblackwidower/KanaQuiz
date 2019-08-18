/*
 *    Copyright 2019 T Duke Perry
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

import org.threeten.bp.LocalDate;

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

    private static final QuestionType identifyQuestion(String question)
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

        if ((kanaCount > 0) && (kanjiCount == 0) && (latinCount == 0))
            return QuestionType.KANA;
        else if ((kanaCount == 0) && (kanjiCount > 0) && (latinCount == 0))
            return QuestionType.KANJI;
        else
            return QuestionType.VOCABULARY;
    }

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

            Cursor kanaData = database.query("SELECT * FROM kana_records");

            if (kanaData.getCount() > 0)
            {
                int dateIndex = kanaData.getColumnIndex("date");
                int questionIndex = kanaData.getColumnIndex("kana");
                int correctIndex = kanaData.getColumnIndex("correct_answers");
                int incorrectIndex = kanaData.getColumnIndex("incorrect_answers");

                while (!kanaData.isLast())
                {
                    kanaData.moveToNext();

                    int dateValue = kanaData.getInt(dateIndex);
                    String questionValue = kanaData.getString(questionIndex);
                    int correctValue = kanaData.getInt(correctIndex);
                    int incorrectValue = kanaData.getInt(incorrectIndex);

                    char typeId = LogTypeConversion.toCharFromType(identifyQuestion(questionValue));

                    database.execSQL(
                            "INSERT INTO question_records (date, question, type, correct_answers, incorrect_answers) " +
                                    "VALUES (?, ?, ?, ?, ?)",
                            new Object[]{dateValue, questionValue, typeId, correctValue, incorrectValue});
                }
            }
            database.execSQL("DROP TABLE kana_records");


            database.execSQL("ALTER TABLE incorrect_answers RENAME TO old_incorrect_answers");

            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS incorrect_answers (date INTEGER NOT NULL, question TEXT NOT NULL, " +
                            "type INTEGER NOT NULL, incorrect_answer TEXT NOT NULL, occurances INTEGER NOT NULL, " +
                            "PRIMARY KEY(date, question, type, incorrect_answer))");

            Cursor incorrectData = database.query("SELECT * FROM old_incorrect_answers");

            if (incorrectData.getCount() > 0)
            {
                int dateIndex = incorrectData.getColumnIndex("date");
                int questionIndex = incorrectData.getColumnIndex("kana");
                int answerIndex = incorrectData.getColumnIndex("incorrect_romanji");
                int occurrencesIndex = incorrectData.getColumnIndex("occurrences");

                while (!incorrectData.isLast())
                {
                    incorrectData.moveToNext();

                    int dateValue = incorrectData.getInt(dateIndex);
                    String questionValue = incorrectData.getString(questionIndex);
                    String answerValue = incorrectData.getString(answerIndex);
                    int occurrencesValue = incorrectData.getInt(occurrencesIndex);

                    char typeId = LogTypeConversion.toCharFromType(identifyQuestion(questionValue));

                    database.execSQL(
                            "INSERT INTO incorrect_answers (date, question, type, incorrect_answer, occurrences) " +
                                    "VALUES (?, ?, ?, ?, ?)",
                            new Object[]{dateValue, questionValue, typeId, answerValue, occurrencesValue});
                }
            }
            database.execSQL("DROP TABLE old_incorrect_answers");
        }
    };
}
