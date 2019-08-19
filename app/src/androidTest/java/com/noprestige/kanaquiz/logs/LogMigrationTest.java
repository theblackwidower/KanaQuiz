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
                new Object[]{"引", QuestionType.KANJI, 26859255, 9, 4},
                new Object[]{"当", QuestionType.KANJI, 35862151, 18, 9},
                new Object[]{"家", QuestionType.KANJI, 69874521, 8, 15},
                new Object[]{"室", QuestionType.KANJI, 17170509, 9, 12}
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

    @Test
    public void migrate3To4SortTest() throws IOException
    {
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 3);

        String[] kanaTest = {
                "あ", "い", "う", "え", "お", "か", "き", "く", "け", "こ", "が", "ぎ", "ぐ", "げ", "ご", "きゃ", "きゅ", "きょ", "ぎゃ", "ぎゅ",
                "ぎょ", "さ", "し", "す", "せ", "そ", "ざ", "じ", "ず", "ぜ", "ぞ", "しゃ", "しゅ", "しょ", "じゃ", "じゅ", "じょ", "た", "ち",
                "つ", "て", "と", "だ", "ぢ", "づ", "で", "ど", "ちゃ", "ちゅ", "ちょ", "ぢゃ", "ぢゅ", "ぢょ", "な", "に", "ぬ", "ね", "の",
                "にゃ", "にゅ", "にょ", "は", "ひ", "ふ", "へ", "ほ", "ば", "び", "ぶ", "べ", "ぼ", "ぱ", "ぴ", "ぷ", "ぺ", "ぽ", "ひゃ", "ひゅ",
                "ひょ", "びゃ", "びゅ", "びょ", "ぴゃ", "ぴゅ", "ぴょ", "ま", "み", "む", "め", "も", "みゃ", "みゅ", "みょ", "ら", "り", "る", "れ",
                "ろ", "りゃ", "りゅ", "りょ", "や", "ゆ", "よ", "わ", "ゐ", "ゑ", "を", "ん", "ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク",
                "ケ", "コ", "ガ", "ギ", "グ", "ゲ", "ゴ", "キャ", "キュ", "キョ", "ギャ", "ギュ", "ギョ", "サ", "シ", "ス", "セ", "ソ", "ザ",
                "ジ", "ズ", "ゼ", "ゾ", "シャ", "シュ", "ショ", "ジャ", "ジュ", "ジョ", "タ", "チ", "ツ", "テ", "ト", "ダ", "ヂ", "ヅ", "デ",
                "ド", "チャ", "チュ", "チョ", "ヂャ", "ヂュ", "ヂョ", "ナ", "ニ", "ヌ", "ネ", "ノ", "ニャ", "ニュ", "ニョ", "ハ", "ヒ", "フ", "ヘ",
                "ホ", "バ", "ビ", "ブ", "ベ", "ボ", "パ", "ピ", "プ", "ペ", "ポ", "ヒャ", "ヒュ", "ヒョ", "ビャ", "ビュ", "ビョ", "ピャ", "ピュ",
                "ピョ", "マ", "ミ", "ム", "メ", "モ", "ミャ", "ミュ", "ミョ", "ラ", "リ", "ル", "レ", "ロ", "リャ", "リュ", "リョ", "ヤ", "ユ",
                "ヨ", "ワ", "ヰ", "ヱ", "ヲ", "ン", "シェ", "ジェ", "チェ", "ツァ", "ツェ", "ツォ", "ティ", "ディ", "デュ", "ファ", "フィ", "フェ",
                "フォ", "イェ", "ウィ", "ウェ", "ウォ", "ヴァ", "ヴィ", "ヴ", "ヴェ", "ヴォ", "ヴュ", "クァ", "クィ", "クェ", "クォ", "グァ", "ツィ",
                "トゥ", "テュ", "ドゥ", "フュ", "ウァ", "ウュ", "ヴャ", "ヴィェ", "ヴョ", "キェ", "ギェ", "クヮ", "グィ", "グェ", "グォ", "グヮ", "ツュ",
                "ニェ", "ヒェ", "ビェ", "ピェ", "フャ", "フィェ", "フョ", "ミェ", "リェ", "リ゚ャ", "リ゚ュ", "リ゚ェ", "リ゚ョ", "ヷ", "ヸ", "ヹ", "ヺ",
                "イィ", "ウゥ", "スィ", "ズィ", "ホゥ", "ラ゚", "リ゚", "ル゚", "レ゚", "ロ゚"
        };
        String[] kanjiTest = {
                "水", "火", "木", "石", "金", "日", "月", "年", "早", "夕", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "上",
                "下", "左", "右", "中", "百", "千", "本", "文", "字", "力", "気", "生", "休", "立", "校", "学", "正", "入", "出", "花", "草",
                "田", "雨", "空", "王", "玉", "円", "大", "小", "人", "名", "女", "男", "子", "犬", "目", "耳", "口", "手", "足", "見", "林",
                "森", "山", "川", "土", "天", "村", "町", "赤", "青", "白", "先", "車", "虫", "糸", "貝", "竹", "音", "交", "計", "語", "話",
                "言", "声", "答", "聞", "夏", "秋", "冬", "春", "雪", "雲", "国", "園", "原", "野", "谷", "地", "体", "頭", "顔", "毛", "首",
                "心", "食", "米", "麦", "肉", "魚", "茶", "教", "知", "才", "作", "工", "元", "馬", "牛", "鳥", "羽", "池", "海", "場", "市",
                "京", "里", "内", "外", "時", "分", "今", "前", "後", "毎", "朝", "昼", "午", "夜", "曜", "週", "親", "母", "父", "姉", "妹",
                "兄", "弟", "形", "丸", "太", "細", "広", "点", "長", "直", "理", "算", "切", "活", "店", "買", "売", "科", "岩", "星", "弱",
                "強", "刀", "弓", "引", "当", "家", "室", "戸", "寺", "門", "通", "色", "黄", "黒", "道", "角", "高", "行", "帰", "来", "歩",
                "走", "止", "新", "古", "何", "用", "楽", "歌", "線", "矢", "台", "自", "友", "同", "読", "書", "記", "紙", "画", "絵", "図",
                "数", "番", "多", "少", "万", "半", "間", "方", "北", "南", "東", "西", "近", "遠", "光", "明", "電", "社", "会", "風", "回",
                "晴", "思", "考", "鳴", "汽", "組", "船", "合", "公"
        };
        String[] wordTest = {
                "Hai", "Iie", "Watashi", "Anata", "Pen", "Enpitsu", "Tsukue", "Isu", "Hon", "Noto", "Inu", "Neko",
                "Uma", "Buta", "Ika", "Ahiru", "Ushi", "Sakana", "Tori", "Kame", "Densha", "Jitensha", "Jidousha",
                "Jidōsha", "Hikouki", "Hikōki", "Basu", "Fune", "Eki", "Kuukou", "Kūkō", "Shinkansen", "Kippu", "San",
                "Sensei", "Tomodachi", "Kazoku", "Otousan", "Otōsan", "Okaasan", "Oniisan", "Otouto", "Otōto",
                "Oneesan", "Imouto", "Imōto", "Kudamono", "Yasai", "Mizu", "Ocha", "Gyuunyuu", "Gyūnyū", "Niku", "Pan",
                "Asagohan", "Hirugohan", "Bangohan", "Uchi", "Heya", "Beddo", "Makura", "Moufu", "Mōfu", "Ofuro",
                "Mado", "Taoru", "Kagami", "Denwa", "Rei", "Zero", "Ichi", "Ni", "San", "Yon", "Shi", "Go", "Roku",
                "Nana", "Shichi", "Hachi", "Kyuu", "Kyū", "Juu", "Jū", "Amerika", "Osutoraria", "Kanada", "Afurika",
                "Chugoku", "Ingurando", "Indo", "Apato", "Depato", "Konbini", "Hoteru", "Resutoran", "Mise", "Kouen",
                "Kōen", "Ginkou", "Ginkō", "Byouin", "Byōin", "Yuubinkyoku", "Yūbinkyoku", "Shatsu", "Zubon", "Sukato",
                "Beruto", "Botan", "Fuku", "Megane", "Kutsu", "Kutsushita", "Kimono", "Konpyuuta", "Konpyūta", "Terebi",
                "Toire", "Beddo", "Sofaa", "Houki", "Hōki", "Hashi", "Kagu", "Kagi", "Osara", "Sukuru", "Gakkou",
                "Tesuto", "Tekisutobukku", "tekisuto", "Tekisuto", "Noto", "Chokku", "Gakusei", "Kyoushitsu", "Sensei",
                "Kouchousensei", "Shukudai", "Enjinia", "Shefu", "Pairotto", "Sarariman", "Konsarutanto", "Keisatsukan",
                "Isha", "Kangoshi", "Bengoshi", "Kashu", "Gakkō", "Daigaku", "Ōkii", "Chiisai", "Otona",
                "Otoko no hito", "Otoko no ko", "Onna no hito", "Onna no ko", "Sensei", "Akachan", "Natsuyasumi", "Iu",
                "Namae", "Mainichi", "Kyō", "Nichiyōbi", "Getsuyōbi", "Kayōbi", "Suiyōbi", "Mokuyōbi", "Kinyōbi",
                "Doyōbi", "Kyōshitsu", "Tokei", "Hashiru", "Dekiru", "Ongaku", "Atarashii", "Furui", "Tegami", "Jikan",
                "Shinbun", "Ningen", "Denwa", "Kaisha", "Kōen", "Densha", "Denki", "Ashita"
        };

        int testDate = 20111105;
        int testCorrect = 6;
        int testIncorrect = 8;
        String testIncorrectString = "blob";
        int testOccurances = 55;

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        for (String kana : kanaTest)
        {
            db.execSQL("INSERT INTO kana_records (date, kana, correct_answers, incorrect_answers) VALUES (?, ?, ?, ?)",
                    new Object[]{testDate, kana, testCorrect, testIncorrect});
            db.execSQL("INSERT INTO incorrect_answers (date, kana, incorrect_romanji, occurrences) VALUES (?, ?, ?, ?)",
                    new Object[]{testDate, kana, testIncorrectString, testOccurances});
        }

        for (String kanji : kanjiTest)
        {
            db.execSQL("INSERT INTO kana_records (date, kana, correct_answers, incorrect_answers) VALUES (?, ?, ?, ?)",
                    new Object[]{testDate, kanji, testCorrect, testIncorrect});
            db.execSQL("INSERT INTO incorrect_answers (date, kana, incorrect_romanji, occurrences) VALUES (?, ?, ?, ?)",
                    new Object[]{testDate, kanji, testIncorrectString, testOccurances});
        }

        // Prepare for the next version.
        db.close();
        db = helper.runMigrationsAndValidate(TEST_DB, 4, true, LogDatabase.MIGRATION_3_4);

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
        int testType = LogTypeConversion.toCharFromType(QuestionType.KANA);
        for (String kana : kanaTest)
        {
            Cursor questionCursor = db.query(
                    "SELECT * FROM question_records WHERE date = ? AND question = ? AND type = ? AND " +
                            "correct_answers = ? AND incorrect_answers = ?",
                    new Object[]{testDate, kana, testType, testCorrect, testIncorrect});

            assertThat(questionCursor.getCount(), is(1));

            Cursor incorrectCursor = db.query(
                    "SELECT * FROM incorrect_answers WHERE date = ? AND question = ? AND type = ? AND " +
                            "incorrect_answer = ? AND occurrences = ?",
                    new Object[]{testDate, kana, testType, testIncorrectString, testOccurances});

            assertThat(incorrectCursor.getCount(), is(1));
        }

        testType = LogTypeConversion.toCharFromType(QuestionType.KANJI);
        for (String kanji : kanjiTest)
        {
            Cursor questionCursor = db.query(
                    "SELECT * FROM question_records WHERE date = ? AND question = ? AND type = ? AND " +
                            "correct_answers = ? AND incorrect_answers = ?",
                    new Object[]{testDate, kanji, testType, testCorrect, testIncorrect});

            assertThat(questionCursor.getCount(), is(1));

            Cursor incorrectCursor = db.query(
                    "SELECT * FROM incorrect_answers WHERE date = ? AND question = ? AND type = ? AND " +
                            "incorrect_answer = ? AND occurrences = ?",
                    new Object[]{testDate, kanji, testType, testIncorrectString, testOccurances});

            assertThat(incorrectCursor.getCount(), is(1));
        }

        assertThat(db.query("SELECT * FROM question_records").getCount(), is(kanaTest.length + kanjiTest.length));
        assertThat(db.query("SELECT * FROM incorrect_answers").getCount(), is(kanaTest.length + kanjiTest.length));
    }
}