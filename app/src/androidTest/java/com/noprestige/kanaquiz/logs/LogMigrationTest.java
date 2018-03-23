package com.noprestige.kanaquiz.logs;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LogMigrationTest
{
    private static final String TEST_DB = "test-logs";

    @Rule
    public MigrationTestHelper helper;

    public LogMigrationTest()
    {
        helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                LogDatabase.class.getCanonicalName(), new FrameworkSQLiteOpenHelperFactory());
    }

    @Test
    public void migrate1To2() throws Exception
    {
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 1);

        Integer[][] testData = {
                new Integer[]{20180123, 15, 4}, new Integer[]{20171121, 5, 11}, new Integer[]{20130624, 22, 2},
                new Integer[]{20180122, 19, 87}, new Integer[]{20180101, 23, 44}, new Integer[]{20180120, 101, 26},
                new Integer[]{20180124, 88, 21}, new Integer[]{20180125, 22, 12}, new Integer[]{20180126, 34, 19},
                new Integer[]{20180127, 4, 8},
        };

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        for (Integer[] data : testData)
            db.execSQL("INSERT INTO daily_record (date, correct_answers, incorrect_answers) VALUES (?, ?, ?)", data);

        // Prepare for the next version.
        db.close();

        for (Integer[] data : testData)
            data[2] += data[1];

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true, LogDatabase.MIGRATION_1_2);

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
        for (Integer[] data : testData)
        {
            Cursor cursor =
                    db.query("SELECT * FROM daily_record WHERE date = ? AND correct_answers = ? AND total_answers = ?",
                            data);
            assertEquals(cursor.getCount(), 1);
        }
        Cursor cursor = db.query("SELECT * FROM daily_record");
        assertEquals(cursor.getCount(), testData.length);
    }

    @Test
    public void migrate2To3() throws Exception
    {
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 2);

        Object[][] kanaTestData = {
                new Object[]{"A", 15, 4}, new Object[]{"B", 5, 11}, new Object[]{"C", 22, 2}, new Object[]{"D", 19, 87},
                new Object[]{"E", 23, 44}, new Object[]{"F", 101, 26}, new Object[]{"G", 88, 21},
                new Object[]{"H", 22, 12}, new Object[]{"I", 34, 19}, new Object[]{"J", 4, 8},
        };
        Object[][] incorrectTestData = {
                new Object[]{"A", "c", 6}, new Object[]{"B", "j", 7}, new Object[]{"B", "f", 7},
                new Object[]{"C", "l", 4}, new Object[]{"D", "f", 2}, new Object[]{"D", "q", 5},
                new Object[]{"E", "r", 23}, new Object[]{"F", "w", 7}, new Object[]{"F", "n", 1},
                new Object[]{"F", "x", 1}, new Object[]{"G", "37", 3}, new Object[]{"H", "mercury", 8},
        };

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        for (Object[] data : kanaTestData)
            db.execSQL("INSERT INTO kana_records (kana, correct_answers, incorrect_answers) VALUES (?, ?, ?)", data);

        for (Object[] data : incorrectTestData)
            db.execSQL("INSERT INTO incorrect_answers (kana, incorrect_romanji, occurrences) VALUES (?, ?, ?)", data);

        // Prepare for the next version.
        db.close();

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = helper.runMigrationsAndValidate(TEST_DB, 3, true, LogDatabase.MIGRATION_2_3);

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
        int currentDate = LogTypeConversion.dateToTimestamp(new Date());

        assertEquals(db.query("SELECT * FROM kana_records WHERE NOT date = ?", new Integer[]{currentDate}).getCount(),
                0);
        assertEquals(
                db.query("SELECT * FROM incorrect_answers WHERE NOT date = ?", new Integer[]{currentDate}).getCount(),
                0);

        for (Object[] kanaData : kanaTestData)
        {
            Cursor cursor = db.query("SELECT * FROM kana_records WHERE date = ? AND kana = ? AND correct_answers = ? " +
                    "AND incorrect_answers = ?", new Object[]{currentDate, kanaData[0], kanaData[1], kanaData[2]});
            assertEquals(cursor.getCount(), 1);
        }
        for (Object[] incorrectData : incorrectTestData)
        {
            Cursor cursor = db.query("SELECT * FROM incorrect_answers WHERE date = ? AND kana = ? AND " +
                            "incorrect_romanji = ? AND occurrences = ?",
                    new Object[]{currentDate, incorrectData[0], incorrectData[1], incorrectData[2]});
            assertEquals(cursor.getCount(), 1);
        }

        assertEquals(db.query("SELECT * FROM kana_records").getCount(), kanaTestData.length);
        assertEquals(db.query("SELECT * FROM incorrect_answers").getCount(), incorrectTestData.length);
    }
}