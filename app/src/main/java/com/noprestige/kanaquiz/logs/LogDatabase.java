package com.noprestige.kanaquiz.logs;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.database.Cursor;

@Database(entities = {DailyRecord.class, KanaRecord.class, IncorrectAnswerRecord.class}, version = 2)
@TypeConverters({LogTypeConversion.class})
public abstract class LogDatabase extends RoomDatabase
{
    public static LogDao DAO = null;

    public abstract LogDao logDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2)
    {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {
            database.execSQL("ALTER TABLE daily_record RENAME TO old_daily_record");
            database.execSQL("CREATE TABLE IF NOT EXISTS daily_record " +
                    "(date INTEGER, correct_answers REAL NOT NULL, total_answers INTEGER NOT NULL, PRIMARY KEY(date))");

            Cursor cursor = database.query("SELECT * FROM old_daily_record");
            int dateIndex = cursor.getColumnIndex("date");
            int correctIndex = cursor.getColumnIndex("correct_answers");
            int incorrectIndex = cursor.getColumnIndex("incorrect_answers");

            int dateValue;
            int correctValue;
            int incorrectValue;

            while (!cursor.isLast())
            {
                cursor.moveToNext();

                dateValue = cursor.getInt(dateIndex);
                correctValue = cursor.getInt(correctIndex);
                incorrectValue = cursor.getInt(incorrectIndex);

                database.execSQL("INSERT INTO daily_record (date, correct_answers, total_answers) VALUES (?, ?, ?)",
                        new Integer[]{dateValue, correctValue, (correctValue + incorrectValue)});
            }
            database.execSQL("DROP TABLE old_daily_record");
        }
    };
}
