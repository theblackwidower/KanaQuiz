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
                LogDatabase.class.getCanonicalName(),
                new FrameworkSQLiteOpenHelperFactory());
    }

    @Test
    public void migrate1To2() throws Exception
    {
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 1);

        Integer[][] testData = new Integer[][]{
                new Integer[]{20180123, 15, 4},
                new Integer[]{20171121, 5, 11},
                new Integer[]{20130624, 22, 2},
                new Integer[]{20180122, 19, 87},
                new Integer[]{20180101, 23, 44},
                new Integer[]{20180120, 101, 26},
                new Integer[]{20180124, 88, 21},
                new Integer[]{20180125, 22, 12},
                new Integer[]{20180126, 34, 19},
                new Integer[]{20180127, 4, 8},
        };

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        for (Integer[] data : testData)
            db.execSQL("INSERT INTO daily_record (date, correct_answers, incorrect_answers) VALUES (?, ?, ?)",
                    data);

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
            Cursor cursor = db.query("SELECT * FROM daily_record WHERE " +
                    "date = ? AND correct_answers = ? AND total_answers = ?", data);
            assertEquals(cursor.getCount(), 1);
        }
        Cursor cursor = db.query("SELECT * FROM daily_record");
        assertEquals(cursor.getCount(), testData.length);
    }
}