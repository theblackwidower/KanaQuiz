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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build().logDao();
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
}
