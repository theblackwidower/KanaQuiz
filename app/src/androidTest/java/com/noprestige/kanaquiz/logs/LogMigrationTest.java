package com.noprestige.kanaquiz.logs;

import android.database.Cursor;

import org.joda.time.LocalDate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
    public void migrate1To2() throws IOException
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
            assertThat(cursor.getCount(), is(1));
        }
        Cursor cursor = db.query("SELECT * FROM daily_record");
        assertThat(cursor.getCount(), is(testData.length));
    }

    @Test
    public void migrate2To3() throws IOException
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
        int currentDate = LogTypeConversion.dateToTimestamp(LocalDate.now());

        assertThat(db.query("SELECT * FROM kana_records WHERE NOT date = ?", new Integer[]{currentDate}).getCount(),
                is(0));
        assertThat(
                db.query("SELECT * FROM incorrect_answers WHERE NOT date = ?", new Integer[]{currentDate}).getCount(),
                is(0));

        for (Object[] kanaData : kanaTestData)
        {
            Cursor cursor = db.query("SELECT * FROM kana_records WHERE date = ? AND kana = ? AND correct_answers = ? " +
                    "AND incorrect_answers = ?", new Object[]{currentDate, kanaData[0], kanaData[1], kanaData[2]});
            assertThat(cursor.getCount(), is(1));
        }
        for (Object[] incorrectData : incorrectTestData)
        {
            Cursor cursor = db.query("SELECT * FROM incorrect_answers WHERE date = ? AND kana = ? AND " +
                            "incorrect_romanji = ? AND occurrences = ?",
                    new Object[]{currentDate, incorrectData[0], incorrectData[1], incorrectData[2]});
            assertThat(cursor.getCount(), is(1));
        }

        assertThat(db.query("SELECT * FROM kana_records").getCount(), is(kanaTestData.length));
        assertThat(db.query("SELECT * FROM incorrect_answers").getCount(), is(incorrectTestData.length));
    }
}