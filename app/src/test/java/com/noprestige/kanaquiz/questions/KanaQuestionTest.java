package com.noprestige.kanaquiz.questions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class KanaQuestionTest
{
    private static final KanaQuestion[] HIRAGANA_KANA_SET_1 = {
            new KanaQuestion("あ", "a"), new KanaQuestion("い", "i"), new KanaQuestion("う", "u"),
            new KanaQuestion("え", "e"), new KanaQuestion("お", "o")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_2_BASE = {
            new KanaQuestion("か", "ka"), new KanaQuestion("き", "ki"), new KanaQuestion("く", "ku"),
            new KanaQuestion("け", "ke"), new KanaQuestion("こ", "ko")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_2_DAKUTEN = {
            new KanaQuestion("が", "ga"), new KanaQuestion("ぎ", "gi"), new KanaQuestion("ぐ", "gu"),
            new KanaQuestion("げ", "ge"), new KanaQuestion("ご", "go")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_2_BASE_DIGRAPHS = {
            new KanaQuestion("きゃ", "kya"), new KanaQuestion("きゅ", "kyu"), new KanaQuestion("きょ", "kyo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_2_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ぎゃ", "gya"), new KanaQuestion("ぎゅ", "gyu"), new KanaQuestion("ぎょ", "gyo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_3_BASE = {
            new KanaQuestion("さ", "sa"), new KanaQuestion("し", "shi"), new KanaQuestion("す", "su"),
            new KanaQuestion("せ", "se"), new KanaQuestion("そ", "so")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_3_DAKUTEN = {
            new KanaQuestion("ざ", "za"), new KanaQuestion("じ", "ji"), new KanaQuestion("ず", "zu"),
            new KanaQuestion("ぜ", "ze"), new KanaQuestion("ぞ", "zo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_3_BASE_DIGRAPHS = {
            new KanaQuestion("しゃ", "sha"), new KanaQuestion("しゅ", "shu"), new KanaQuestion("しょ", "sho")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_3_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("じゃ", "ja"), new KanaQuestion("じゅ", "ju"), new KanaQuestion("じょ", "jo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_4_BASE = {
            new KanaQuestion("た", "ta"), new KanaQuestion("ち", "chi"), new KanaQuestion("つ", "tsu"),
            new KanaQuestion("て", "te"), new KanaQuestion("と", "to")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_4_DAKUTEN = {
            new KanaQuestion("だ", "da"), new KanaQuestion("ぢ", "ji"), new KanaQuestion("づ", "zu"),
            new KanaQuestion("で", "de"), new KanaQuestion("ど", "do")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_4_BASE_DIGRAPHS = {
            new KanaQuestion("ちゃ", "cha"), new KanaQuestion("ちゅ", "chu"), new KanaQuestion("ちょ", "cho")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_4_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ぢゃ", "ja"), new KanaQuestion("ぢゅ", "ju"), new KanaQuestion("ぢょ", "jo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_5 = {
            new KanaQuestion("な", "na"), new KanaQuestion("に", "ni"), new KanaQuestion("ぬ", "nu"),
            new KanaQuestion("ね", "ne"), new KanaQuestion("の", "no")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_5_DIGRAPHS = {
            new KanaQuestion("にゃ", "nya"), new KanaQuestion("にゅ", "nyu"), new KanaQuestion("にょ", "nyo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_6_BASE = {
            new KanaQuestion("は", "ha"), new KanaQuestion("ひ", "hi"), new KanaQuestion("ふ", "fu"),
            new KanaQuestion("へ", "he"), new KanaQuestion("ほ", "ho")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_6_DAKUTEN = {
            new KanaQuestion("ば", "ba"), new KanaQuestion("び", "bi"), new KanaQuestion("ぶ", "bu"),
            new KanaQuestion("べ", "be"), new KanaQuestion("ぼ", "bo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_6_HANDAKUTEN = {
            new KanaQuestion("ぱ", "pa"), new KanaQuestion("ぴ", "pi"), new KanaQuestion("ぷ", "pu"),
            new KanaQuestion("ぺ", "pe"), new KanaQuestion("ぽ", "po")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_6_BASE_DIGRAPHS = {
            new KanaQuestion("ひゃ", "hya"), new KanaQuestion("ひゅ", "hyu"), new KanaQuestion("ひょ", "hyo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_6_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("びゃ", "bya"), new KanaQuestion("びゅ", "byu"), new KanaQuestion("びょ", "byo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_6_HANDAKUTEN_DIGRAPHS = {
            new KanaQuestion("ぴゃ", "pya"), new KanaQuestion("ぴゅ", "pyu"), new KanaQuestion("ぴょ", "pyo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_7 = {
            new KanaQuestion("ま", "ma"), new KanaQuestion("み", "mi"), new KanaQuestion("む", "mu"),
            new KanaQuestion("め", "me"), new KanaQuestion("も", "mo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_7_DIGRAPHS = {
            new KanaQuestion("みゃ", "mya"), new KanaQuestion("みゅ", "myu"), new KanaQuestion("みょ", "myo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_8 = {
            new KanaQuestion("ら", "ra"), new KanaQuestion("り", "ri"), new KanaQuestion("る", "ru"),
            new KanaQuestion("れ", "re"), new KanaQuestion("ろ", "ro")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_8_DIGRAPHS = {
            new KanaQuestion("りゃ", "rya"), new KanaQuestion("りゅ", "ryu"), new KanaQuestion("りょ", "ryo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_9 = {
            new KanaQuestion("や", "ya"), new KanaQuestion("ゆ", "yu"), new KanaQuestion("よ", "yo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_10_W_GROUP = {
            new KanaQuestion("わ", "wa"), new KanaQuestion("を", "wo")
    };

    private static final KanaQuestion[] HIRAGANA_KANA_SET_10_N_CONSONANT = {
            new KanaQuestion("ん", "n")
    };


    private static final KanaQuestion[] KATAKANA_KANA_SET_1 = {
            new KanaQuestion("ア", "a"), new KanaQuestion("イ", "i"), new KanaQuestion("ウ", "u"),
            new KanaQuestion("エ", "e"), new KanaQuestion("オ", "o")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_2_BASE = {
            new KanaQuestion("カ", "ka"), new KanaQuestion("キ", "ki"), new KanaQuestion("ク", "ku"),
            new KanaQuestion("ケ", "ke"), new KanaQuestion("コ", "ko")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_2_DAKUTEN = {
            new KanaQuestion("ガ", "ga"), new KanaQuestion("ギ", "gi"), new KanaQuestion("グ", "gu"),
            new KanaQuestion("ゲ", "ge"), new KanaQuestion("ゴ", "go")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_2_BASE_DIGRAPHS = {
            new KanaQuestion("キャ", "kya"), new KanaQuestion("キュ", "kyu"), new KanaQuestion("キョ", "kyo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_2_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ギャ", "gya"), new KanaQuestion("ギュ", "gyu"), new KanaQuestion("ギョ", "gyo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_3_BASE = {
            new KanaQuestion("サ", "sa"), new KanaQuestion("シ", "shi"), new KanaQuestion("ス", "su"),
            new KanaQuestion("セ", "se"), new KanaQuestion("ソ", "so")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_3_DAKUTEN = {
            new KanaQuestion("ザ", "za"), new KanaQuestion("ジ", "ji"), new KanaQuestion("ズ", "zu"),
            new KanaQuestion("ゼ", "ze"), new KanaQuestion("ゾ", "zo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_3_BASE_DIGRAPHS = {
            new KanaQuestion("シャ", "sha"), new KanaQuestion("シュ", "shu"), new KanaQuestion("ショ", "sho")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_3_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ジャ", "ja"), new KanaQuestion("ジュ", "ju"), new KanaQuestion("ジョ", "jo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_4_BASE = {
            new KanaQuestion("タ", "ta"), new KanaQuestion("チ", "chi"), new KanaQuestion("ツ", "tsu"),
            new KanaQuestion("テ", "te"), new KanaQuestion("ト", "to")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_4_DAKUTEN = {
            new KanaQuestion("ダ", "da"), new KanaQuestion("ヂ", "ji"), new KanaQuestion("ヅ", "zu"),
            new KanaQuestion("デ", "de"), new KanaQuestion("ド", "do")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_4_BASE_DIGRAPHS = {
            new KanaQuestion("チャ", "cha"), new KanaQuestion("チュ", "chu"), new KanaQuestion("チョ", "cho")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_4_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ヂャ", "ja"), new KanaQuestion("ヂュ", "ju"), new KanaQuestion("ヂョ", "jo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_5 = {
            new KanaQuestion("ナ", "na"), new KanaQuestion("ニ", "ni"), new KanaQuestion("ヌ", "nu"),
            new KanaQuestion("ネ", "ne"), new KanaQuestion("ノ", "no")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_5_DIGRAPHS = {
            new KanaQuestion("ニャ", "nya"), new KanaQuestion("ニュ", "nyu"), new KanaQuestion("ニョ", "nyo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_6_BASE = {
            new KanaQuestion("ハ", "ha"), new KanaQuestion("ヒ", "hi"), new KanaQuestion("フ", "fu"),
            new KanaQuestion("ヘ", "he"), new KanaQuestion("ホ", "ho")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_6_DAKUTEN = {
            new KanaQuestion("バ", "ba"), new KanaQuestion("ビ", "bi"), new KanaQuestion("ブ", "bu"),
            new KanaQuestion("ベ", "be"), new KanaQuestion("ボ", "bo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_6_HANDAKUTEN = {
            new KanaQuestion("パ", "pa"), new KanaQuestion("ピ", "pi"), new KanaQuestion("プ", "pu"),
            new KanaQuestion("ペ", "pe"), new KanaQuestion("ポ", "po")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_6_BASE_DIGRAPHS = {
            new KanaQuestion("ヒャ", "hya"), new KanaQuestion("ヒュ", "hyu"), new KanaQuestion("ヒョ", "hyo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_6_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ビャ", "bya"), new KanaQuestion("ビュ", "byu"), new KanaQuestion("ビョ", "byo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_6_HANDAKUTEN_DIGRAPHS = {
            new KanaQuestion("ピャ", "pya"), new KanaQuestion("ピュ", "pyu"), new KanaQuestion("ピョ", "pyo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_7 = {
            new KanaQuestion("マ", "ma"), new KanaQuestion("ミ", "mi"), new KanaQuestion("ム", "mu"),
            new KanaQuestion("メ", "me"), new KanaQuestion("モ", "mo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_7_DIGRAPHS = {
            new KanaQuestion("ミャ", "mya"), new KanaQuestion("ミュ", "myu"), new KanaQuestion("ミョ", "myo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_8 = {
            new KanaQuestion("ラ", "ra"), new KanaQuestion("リ", "ri"), new KanaQuestion("ル", "ru"),
            new KanaQuestion("レ", "re"), new KanaQuestion("ロ", "ro")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_8_DIGRAPHS = {
            new KanaQuestion("リャ", "rya"), new KanaQuestion("リュ", "ryu"), new KanaQuestion("リョ", "ryo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_9 = {
            new KanaQuestion("ヤ", "ya"), new KanaQuestion("ユ", "yu"), new KanaQuestion("ヨ", "yo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_10_W_GROUP = {
            new KanaQuestion("ワ", "wa"), new KanaQuestion("ヲ", "wo")
    };

    private static final KanaQuestion[] KATAKANA_KANA_SET_10_N_CONSONANT = {
            new KanaQuestion("ン", "n")
    };

    @Test
    public void isDigraphTest()
    {
        assertDigraphTest(HIRAGANA_KANA_SET_1, false);
        assertDigraphTest(HIRAGANA_KANA_SET_2_BASE, false);
        assertDigraphTest(HIRAGANA_KANA_SET_2_BASE_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_KANA_SET_2_DAKUTEN, false);
        assertDigraphTest(HIRAGANA_KANA_SET_2_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_KANA_SET_3_BASE, false);
        assertDigraphTest(HIRAGANA_KANA_SET_3_BASE_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_KANA_SET_3_DAKUTEN, false);
        assertDigraphTest(HIRAGANA_KANA_SET_3_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_KANA_SET_4_BASE, false);
        assertDigraphTest(HIRAGANA_KANA_SET_4_BASE_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_KANA_SET_4_DAKUTEN, false);
        assertDigraphTest(HIRAGANA_KANA_SET_4_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_KANA_SET_5, false);
        assertDigraphTest(HIRAGANA_KANA_SET_5_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_KANA_SET_6_BASE, false);
        assertDigraphTest(HIRAGANA_KANA_SET_6_BASE_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_KANA_SET_6_DAKUTEN, false);
        assertDigraphTest(HIRAGANA_KANA_SET_6_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_KANA_SET_6_HANDAKUTEN, false);
        assertDigraphTest(HIRAGANA_KANA_SET_6_HANDAKUTEN_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_KANA_SET_7, false);
        assertDigraphTest(HIRAGANA_KANA_SET_7_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_KANA_SET_8, false);
        assertDigraphTest(HIRAGANA_KANA_SET_8_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_KANA_SET_9, false);
        assertDigraphTest(HIRAGANA_KANA_SET_10_W_GROUP, false);
        assertDigraphTest(HIRAGANA_KANA_SET_10_N_CONSONANT, false);

        assertDigraphTest(KATAKANA_KANA_SET_1, false);
        assertDigraphTest(KATAKANA_KANA_SET_2_BASE, false);
        assertDigraphTest(KATAKANA_KANA_SET_2_BASE_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_KANA_SET_2_DAKUTEN, false);
        assertDigraphTest(KATAKANA_KANA_SET_2_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_KANA_SET_3_BASE, false);
        assertDigraphTest(KATAKANA_KANA_SET_3_BASE_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_KANA_SET_3_DAKUTEN, false);
        assertDigraphTest(KATAKANA_KANA_SET_3_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_KANA_SET_4_BASE, false);
        assertDigraphTest(KATAKANA_KANA_SET_4_BASE_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_KANA_SET_4_DAKUTEN, false);
        assertDigraphTest(KATAKANA_KANA_SET_4_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_KANA_SET_5, false);
        assertDigraphTest(KATAKANA_KANA_SET_5_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_KANA_SET_6_BASE, false);
        assertDigraphTest(KATAKANA_KANA_SET_6_BASE_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_KANA_SET_6_DAKUTEN, false);
        assertDigraphTest(KATAKANA_KANA_SET_6_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_KANA_SET_6_HANDAKUTEN, false);
        assertDigraphTest(KATAKANA_KANA_SET_6_HANDAKUTEN_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_KANA_SET_7, false);
        assertDigraphTest(KATAKANA_KANA_SET_7_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_KANA_SET_8, false);
        assertDigraphTest(KATAKANA_KANA_SET_8_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_KANA_SET_9, false);
        assertDigraphTest(KATAKANA_KANA_SET_10_W_GROUP, false);
        assertDigraphTest(KATAKANA_KANA_SET_10_N_CONSONANT, false);
    }

    @Test
    public void isDiacriticTest()
    {
        assertDiacriticTest(HIRAGANA_KANA_SET_1, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_2_BASE, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_2_BASE_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_2_DAKUTEN, true);
        assertDiacriticTest(HIRAGANA_KANA_SET_2_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(HIRAGANA_KANA_SET_3_BASE, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_3_BASE_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_3_DAKUTEN, true);
        assertDiacriticTest(HIRAGANA_KANA_SET_3_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(HIRAGANA_KANA_SET_4_BASE, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_4_BASE_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_4_DAKUTEN, true);
        assertDiacriticTest(HIRAGANA_KANA_SET_4_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(HIRAGANA_KANA_SET_5, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_5_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_6_BASE, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_6_BASE_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_6_DAKUTEN, true);
        assertDiacriticTest(HIRAGANA_KANA_SET_6_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(HIRAGANA_KANA_SET_6_HANDAKUTEN, true);
        assertDiacriticTest(HIRAGANA_KANA_SET_6_HANDAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(HIRAGANA_KANA_SET_7, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_7_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_8, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_8_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_9, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_10_W_GROUP, false);
        assertDiacriticTest(HIRAGANA_KANA_SET_10_N_CONSONANT, false);

        assertDiacriticTest(KATAKANA_KANA_SET_1, false);
        assertDiacriticTest(KATAKANA_KANA_SET_2_BASE, false);
        assertDiacriticTest(KATAKANA_KANA_SET_2_BASE_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_KANA_SET_2_DAKUTEN, true);
        assertDiacriticTest(KATAKANA_KANA_SET_2_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(KATAKANA_KANA_SET_3_BASE, false);
        assertDiacriticTest(KATAKANA_KANA_SET_3_BASE_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_KANA_SET_3_DAKUTEN, true);
        assertDiacriticTest(KATAKANA_KANA_SET_3_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(KATAKANA_KANA_SET_4_BASE, false);
        assertDiacriticTest(KATAKANA_KANA_SET_4_BASE_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_KANA_SET_4_DAKUTEN, true);
        assertDiacriticTest(KATAKANA_KANA_SET_4_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(KATAKANA_KANA_SET_5, false);
        assertDiacriticTest(KATAKANA_KANA_SET_5_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_KANA_SET_6_BASE, false);
        assertDiacriticTest(KATAKANA_KANA_SET_6_BASE_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_KANA_SET_6_DAKUTEN, true);
        assertDiacriticTest(KATAKANA_KANA_SET_6_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(KATAKANA_KANA_SET_6_HANDAKUTEN, true);
        assertDiacriticTest(KATAKANA_KANA_SET_6_HANDAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(KATAKANA_KANA_SET_7, false);
        assertDiacriticTest(KATAKANA_KANA_SET_7_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_KANA_SET_8, false);
        assertDiacriticTest(KATAKANA_KANA_SET_8_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_KANA_SET_9, false);
        assertDiacriticTest(KATAKANA_KANA_SET_10_W_GROUP, false);
        assertDiacriticTest(KATAKANA_KANA_SET_10_N_CONSONANT, false);
    }

    private void assertDigraphTest(KanaQuestion[] questions, boolean expected)
    {
        for (KanaQuestion question : questions)
            assertThat(question.isDigraph(), is(expected));
    }

    private void assertDiacriticTest(KanaQuestion[] questions, boolean expected)
    {
        for (KanaQuestion question : questions)
            assertThat(question.isDiacritic(), is(expected));
    }
}