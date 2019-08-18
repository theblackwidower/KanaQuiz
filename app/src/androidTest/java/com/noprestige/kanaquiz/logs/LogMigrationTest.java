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

import android.database.Cursor;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.noprestige.kanaquiz.questions.QuestionType;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDate;

import java.io.IOException;

import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

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
        AndroidThreeTen.init(InstrumentationRegistry.getInstrumentation().getTargetContext());
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

    @Test
    public void migrate3To4() throws IOException
    {
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 3);

        Object[][] questionTestData = {
                new Object[]{"あ", QuestionType.KANA, 20010101, 5, 12},
                new Object[]{"い", QuestionType.KANA, 19290517, 12, 18},
                new Object[]{"う", QuestionType.KANA, 23230819, 17, 66},
                new Object[]{"え", QuestionType.KANA, 20190712, 6, 5},
                new Object[]{"お", QuestionType.KANA, 20080526, 17, 8},
                new Object[]{"か", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"き", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"く", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"け", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"こ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"が", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぎ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぐ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"げ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ご", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"きゃ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"きゅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"きょ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぎゃ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぎゅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぎょ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"さ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"し", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"す", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"せ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"そ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ざ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"じ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ず", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぜ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぞ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"しゃ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"しゅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"しょ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"じゃ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"じゅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"じょ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"た", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ち", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"つ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"て", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"と", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"だ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぢ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"づ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"で", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ど", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ちゃ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ちゅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ちょ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぢゃ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぢゅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぢょ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"な", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"に", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぬ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ね", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"の", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"にゃ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"にゅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"にょ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"は", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ひ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ふ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"へ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ほ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ば", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"び", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぶ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"べ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぼ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぱ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぴ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぷ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぺ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぽ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ひゃ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ひゅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ひょ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"びゃ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"びゅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"びょ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぴゃ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぴゅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ぴょ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ま", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"み", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"む", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"め", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"も", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"みゃ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"みゅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"みょ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ら", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"り", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"る", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"れ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ろ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"りゃ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"りゅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"りょ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"や", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ゆ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"よ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"わ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ゐ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ゑ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"を", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ん", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ア", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"イ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ウ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"エ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"オ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"カ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"キ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ク", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ケ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"コ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ガ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ギ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"グ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ゲ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ゴ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"キャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"キュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"キョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ギャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ギュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ギョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"サ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"シ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ス", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"セ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ソ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ザ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ジ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ズ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ゼ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ゾ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"シャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"シュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ショ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ジャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ジュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ジョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"タ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"チ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ツ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"テ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ト", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ダ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヂ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヅ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"デ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ド", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"チャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"チュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"チョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヂャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヂュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヂョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ナ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ニ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヌ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ネ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ノ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ニャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ニュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ニョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ハ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヒ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"フ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヘ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ホ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"バ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ビ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ブ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ベ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ボ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"パ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ピ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"プ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ペ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ポ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヒャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヒュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヒョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ビャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ビュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ビョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ピャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ピュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ピョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"マ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ミ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ム", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"メ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"モ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ミャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ミュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ミョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ラ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"リ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ル", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"レ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ロ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"リャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"リュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"リョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヤ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ユ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヨ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ワ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヰ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヱ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヲ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ン", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"シェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ジェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"チェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ツァ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ツェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ツォ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ティ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ディ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"デュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ファ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"フィ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"フェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"フォ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"イェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ウィ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ウェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ウォ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヴァ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヴィ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヴ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヴェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヴォ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヴュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"クァ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"クィ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"クェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"クォ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"グァ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ツィ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"トゥ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"テュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ドゥ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"フュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ウァ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ウュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヴャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヴィェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヴョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"キェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ギェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"クヮ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"グィ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"グェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"グォ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"グヮ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ツュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ニェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヒェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ビェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ピェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"フャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"フィェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"フョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ミェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"リェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"リ゚ャ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"リ゚ュ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"リ゚ェ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"リ゚ョ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヷ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヸ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヹ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ヺ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"イィ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ウゥ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"スィ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ズィ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ホゥ", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ラ゚", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"リ゚", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ル゚", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"レ゚", QuestionType.KANA, 20111105, 6, 8},
                new Object[]{"ロ゚", QuestionType.KANA, 20111105, 6, 8},

                new Object[]{"水", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"火", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"木", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"石", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"金", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"日", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"月", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"年", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"早", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"夕", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"一", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"二", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"三", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"四", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"五", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"六", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"七", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"八", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"九", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"十", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"上", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"下", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"左", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"右", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"中", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"百", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"千", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"本", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"文", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"字", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"力", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"気", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"生", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"休", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"立", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"校", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"学", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"正", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"入", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"出", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"花", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"草", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"田", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"雨", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"空", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"王", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"玉", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"円", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"大", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"小", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"人", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"名", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"女", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"男", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"子", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"犬", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"目", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"耳", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"口", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"手", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"足", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"見", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"林", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"森", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"山", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"川", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"土", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"天", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"村", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"町", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"赤", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"青", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"白", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"先", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"車", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"虫", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"糸", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"貝", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"竹", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"音", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"交", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"計", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"語", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"話", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"言", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"声", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"答", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"聞", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"夏", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"秋", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"冬", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"春", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"雪", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"雲", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"国", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"園", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"原", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"野", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"谷", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"地", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"体", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"頭", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"顔", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"毛", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"首", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"心", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"食", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"米", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"麦", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"肉", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"魚", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"茶", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"教", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"知", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"才", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"作", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"工", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"元", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"馬", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"牛", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"鳥", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"羽", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"池", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"海", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"場", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"市", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"京", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"里", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"内", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"外", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"時", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"分", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"今", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"前", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"後", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"毎", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"朝", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"昼", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"午", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"夜", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"曜", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"週", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"親", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"母", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"父", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"姉", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"妹", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"兄", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"弟", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"形", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"丸", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"太", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"細", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"広", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"点", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"長", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"直", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"理", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"算", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"切", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"活", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"店", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"買", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"売", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"科", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"岩", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"星", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"弱", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"強", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"刀", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"弓", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"引", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"当", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"家", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"室", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"戸", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"寺", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"門", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"通", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"色", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"黄", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"黒", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"道", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"角", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"高", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"行", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"帰", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"来", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"歩", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"走", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"止", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"新", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"古", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"何", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"用", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"楽", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"歌", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"線", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"矢", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"台", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"自", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"友", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"同", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"読", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"書", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"記", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"紙", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"画", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"絵", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"図", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"数", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"番", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"多", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"少", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"万", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"半", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"間", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"方", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"北", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"南", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"東", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"西", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"近", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"遠", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"光", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"明", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"電", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"社", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"会", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"風", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"回", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"晴", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"思", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"考", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"鳴", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"汽", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"組", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"船", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"合", QuestionType.KANJI, 20111105, 6, 8},
                new Object[]{"公", QuestionType.KANJI, 20111105, 6, 8}
        };

        Object[][] incorrectTestData = {
                new Object[]{"ぐ", QuestionType.KANA, 10581105, "f", 21},
                new Object[]{"げ", QuestionType.KANA, 20896745, "na", 7},
                new Object[]{"ご", QuestionType.KANA, 64845166, "z", 8},
                new Object[]{"きゃ", QuestionType.KANA, 20111105, "eo", 3},
                new Object[]{"きゅ", QuestionType.KANA, 20211015, "q", 32},
                new Object[]{"きょ", QuestionType.KANA, 20101111, "u", 4},
                new Object[]{"ぎゃ", QuestionType.KANA, 20000515, "n", 6},
                new Object[]{"ぎゅ", QuestionType.KANA, 20080519, "z", 24},
                new Object[]{"年", QuestionType.KANJI, 19990812, "blue", 16},
                new Object[]{"早", QuestionType.KANJI, 20220119, "gram", 4},
                new Object[]{"夕", QuestionType.KANJI, 19951109, "blood", 7},
                new Object[]{"一", QuestionType.KANJI, 20050818, "zebra", 2},
                new Object[]{"二", QuestionType.KANJI, 20060709, "file", 6},
                new Object[]{"三", QuestionType.KANJI, 20070906, "zipper", 3},
                new Object[]{"四", QuestionType.KANJI, 20080103, "mate", 4}
        };

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        for (Object[] data : questionTestData)
            db.execSQL("INSERT INTO kana_records (date, kana, correct_answers, incorrect_answers) VALUES (?, ?, ?, ?)",
                    new Object[]{data[2], data[0], data[3], data[4]});

        for (Object[] data : incorrectTestData)
            db.execSQL("INSERT INTO incorrect_answers (date, kana, incorrect_romanji, occurrences) VALUES (?, ?, ?, ?)",
                    new Object[]{data[2], data[0], data[3], data[4]});

        // Prepare for the next version.
        db.close();
        db = helper.runMigrationsAndValidate(TEST_DB, 4, true, LogDatabase.MIGRATION_3_4);

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.

        for (Object[] data : questionTestData)
        {
            int type = LogTypeConversion.toCharFromType((QuestionType) data[1]);

            Cursor cursor = db.query(
                    "SELECT * FROM question_records WHERE date = ? AND question = ? AND type = ? AND " +
                            "correct_answers = ? AND incorrect_answers = ?",
                    new Object[]{data[2], data[0], type, data[3], data[4]});
            assertThat(cursor.getCount(), is(1));
        }
        for (Object[] data : incorrectTestData)
        {
            int type = LogTypeConversion.toCharFromType((QuestionType) data[1]);

            Cursor cursor = db.query(
                    "SELECT * FROM incorrect_answers WHERE date = ? AND question = ? AND type = ? AND " +
                            "incorrect_answer = ? AND occurrences = ?",
                    new Object[]{data[2], data[0], type, data[3], data[4]});
            assertThat(cursor.getCount(), is(1));
        }

        assertThat(db.query("SELECT * FROM question_records").getCount(), is(questionTestData.length));
        assertThat(db.query("SELECT * FROM incorrect_answers").getCount(), is(incorrectTestData.length));
    }
}