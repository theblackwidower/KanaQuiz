package com.noprestige.kanaquiz.questions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class KanaQuestionTest
{
    private static final KanaQuestion[] HIRAGANA_1 = {
            new KanaQuestion("あ", "a"), new KanaQuestion("い", "i"), new KanaQuestion("う", "u"),
            new KanaQuestion("え", "e"), new KanaQuestion("お", "o")
    };

    private static final KanaQuestion[] HIRAGANA_2_BASE = {
            new KanaQuestion("か", "ka"), new KanaQuestion("き", "ki"), new KanaQuestion("く", "ku"),
            new KanaQuestion("け", "ke"), new KanaQuestion("こ", "ko")
    };

    private static final KanaQuestion[] HIRAGANA_2_DAKUTEN = {
            new KanaQuestion("が", "ga"), new KanaQuestion("ぎ", "gi"), new KanaQuestion("ぐ", "gu"),
            new KanaQuestion("げ", "ge"), new KanaQuestion("ご", "go")
    };

    private static final KanaQuestion[] HIRAGANA_2_BASE_DIGRAPHS = {
            new KanaQuestion("きゃ", "kya"), new KanaQuestion("きゅ", "kyu"), new KanaQuestion("きょ", "kyo")
    };

    private static final KanaQuestion[] HIRAGANA_2_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ぎゃ", "gya"), new KanaQuestion("ぎゅ", "gyu"), new KanaQuestion("ぎょ", "gyo")
    };

    private static final KanaQuestion[] HIRAGANA_3_BASE = {
            new KanaQuestion("さ", "sa"), new KanaQuestion("し", "shi"), new KanaQuestion("す", "su"),
            new KanaQuestion("せ", "se"), new KanaQuestion("そ", "so")
    };

    private static final KanaQuestion[] HIRAGANA_3_DAKUTEN = {
            new KanaQuestion("ざ", "za"), new KanaQuestion("じ", "ji"), new KanaQuestion("ず", "zu"),
            new KanaQuestion("ぜ", "ze"), new KanaQuestion("ぞ", "zo")
    };

    private static final KanaQuestion[] HIRAGANA_3_BASE_DIGRAPHS = {
            new KanaQuestion("しゃ", "sha"), new KanaQuestion("しゅ", "shu"), new KanaQuestion("しょ", "sho")
    };

    private static final KanaQuestion[] HIRAGANA_3_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("じゃ", "ja"), new KanaQuestion("じゅ", "ju"), new KanaQuestion("じょ", "jo")
    };

    private static final KanaQuestion[] HIRAGANA_4_BASE = {
            new KanaQuestion("た", "ta"), new KanaQuestion("ち", "chi"), new KanaQuestion("つ", "tsu"),
            new KanaQuestion("て", "te"), new KanaQuestion("と", "to")
    };

    private static final KanaQuestion[] HIRAGANA_4_DAKUTEN = {
            new KanaQuestion("だ", "da"), new KanaQuestion("ぢ", "ji"), new KanaQuestion("づ", "zu"),
            new KanaQuestion("で", "de"), new KanaQuestion("ど", "do")
    };

    private static final KanaQuestion[] HIRAGANA_4_BASE_DIGRAPHS = {
            new KanaQuestion("ちゃ", "cha"), new KanaQuestion("ちゅ", "chu"), new KanaQuestion("ちょ", "cho")
    };

    private static final KanaQuestion[] HIRAGANA_4_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ぢゃ", "ja"), new KanaQuestion("ぢゅ", "ju"), new KanaQuestion("ぢょ", "jo")
    };

    private static final KanaQuestion[] HIRAGANA_5 = {
            new KanaQuestion("な", "na"), new KanaQuestion("に", "ni"), new KanaQuestion("ぬ", "nu"),
            new KanaQuestion("ね", "ne"), new KanaQuestion("の", "no")
    };

    private static final KanaQuestion[] HIRAGANA_5_DIGRAPHS = {
            new KanaQuestion("にゃ", "nya"), new KanaQuestion("にゅ", "nyu"), new KanaQuestion("にょ", "nyo")
    };

    private static final KanaQuestion[] HIRAGANA_6_BASE = {
            new KanaQuestion("は", "ha"), new KanaQuestion("ひ", "hi"), new KanaQuestion("ふ", "fu"),
            new KanaQuestion("へ", "he"), new KanaQuestion("ほ", "ho")
    };

    private static final KanaQuestion[] HIRAGANA_6_DAKUTEN = {
            new KanaQuestion("ば", "ba"), new KanaQuestion("び", "bi"), new KanaQuestion("ぶ", "bu"),
            new KanaQuestion("べ", "be"), new KanaQuestion("ぼ", "bo")
    };

    private static final KanaQuestion[] HIRAGANA_6_HANDAKUTEN = {
            new KanaQuestion("ぱ", "pa"), new KanaQuestion("ぴ", "pi"), new KanaQuestion("ぷ", "pu"),
            new KanaQuestion("ぺ", "pe"), new KanaQuestion("ぽ", "po")
    };

    private static final KanaQuestion[] HIRAGANA_6_BASE_DIGRAPHS = {
            new KanaQuestion("ひゃ", "hya"), new KanaQuestion("ひゅ", "hyu"), new KanaQuestion("ひょ", "hyo")
    };

    private static final KanaQuestion[] HIRAGANA_6_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("びゃ", "bya"), new KanaQuestion("びゅ", "byu"), new KanaQuestion("びょ", "byo")
    };

    private static final KanaQuestion[] HIRAGANA_6_HANDAKUTEN_DIGRAPHS = {
            new KanaQuestion("ぴゃ", "pya"), new KanaQuestion("ぴゅ", "pyu"), new KanaQuestion("ぴょ", "pyo")
    };

    private static final KanaQuestion[] HIRAGANA_7 = {
            new KanaQuestion("ま", "ma"), new KanaQuestion("み", "mi"), new KanaQuestion("む", "mu"),
            new KanaQuestion("め", "me"), new KanaQuestion("も", "mo")
    };

    private static final KanaQuestion[] HIRAGANA_7_DIGRAPHS = {
            new KanaQuestion("みゃ", "mya"), new KanaQuestion("みゅ", "myu"), new KanaQuestion("みょ", "myo")
    };

    private static final KanaQuestion[] HIRAGANA_8 = {
            new KanaQuestion("ら", "ra"), new KanaQuestion("り", "ri"), new KanaQuestion("る", "ru"),
            new KanaQuestion("れ", "re"), new KanaQuestion("ろ", "ro")
    };

    private static final KanaQuestion[] HIRAGANA_8_DIGRAPHS = {
            new KanaQuestion("りゃ", "rya"), new KanaQuestion("りゅ", "ryu"), new KanaQuestion("りょ", "ryo")
    };

    private static final KanaQuestion[] HIRAGANA_9 = {
            new KanaQuestion("や", "ya"), new KanaQuestion("ゆ", "yu"), new KanaQuestion("よ", "yo")
    };

    private static final KanaQuestion[] HIRAGANA_10_W_GROUP = {
            new KanaQuestion("わ", "wa"), new KanaQuestion("を", "wo")
    };

    private static final KanaQuestion[] HIRAGANA_10_N_CONSONANT = {
            new KanaQuestion("ん", "n")
    };


    private static final KanaQuestion[] KATAKANA_1 = {
            new KanaQuestion("ア", "a"), new KanaQuestion("イ", "i"), new KanaQuestion("ウ", "u"),
            new KanaQuestion("エ", "e"), new KanaQuestion("オ", "o")
    };

    private static final KanaQuestion[] KATAKANA_2_BASE = {
            new KanaQuestion("カ", "ka"), new KanaQuestion("キ", "ki"), new KanaQuestion("ク", "ku"),
            new KanaQuestion("ケ", "ke"), new KanaQuestion("コ", "ko")
    };

    private static final KanaQuestion[] KATAKANA_2_DAKUTEN = {
            new KanaQuestion("ガ", "ga"), new KanaQuestion("ギ", "gi"), new KanaQuestion("グ", "gu"),
            new KanaQuestion("ゲ", "ge"), new KanaQuestion("ゴ", "go")
    };

    private static final KanaQuestion[] KATAKANA_2_BASE_DIGRAPHS = {
            new KanaQuestion("キャ", "kya"), new KanaQuestion("キュ", "kyu"), new KanaQuestion("キョ", "kyo")
    };

    private static final KanaQuestion[] KATAKANA_2_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ギャ", "gya"), new KanaQuestion("ギュ", "gyu"), new KanaQuestion("ギョ", "gyo")
    };

    private static final KanaQuestion[] KATAKANA_3_BASE = {
            new KanaQuestion("サ", "sa"), new KanaQuestion("シ", "shi"), new KanaQuestion("ス", "su"),
            new KanaQuestion("セ", "se"), new KanaQuestion("ソ", "so")
    };

    private static final KanaQuestion[] KATAKANA_3_DAKUTEN = {
            new KanaQuestion("ザ", "za"), new KanaQuestion("ジ", "ji"), new KanaQuestion("ズ", "zu"),
            new KanaQuestion("ゼ", "ze"), new KanaQuestion("ゾ", "zo")
    };

    private static final KanaQuestion[] KATAKANA_3_BASE_DIGRAPHS = {
            new KanaQuestion("シャ", "sha"), new KanaQuestion("シュ", "shu"), new KanaQuestion("ショ", "sho")
    };

    private static final KanaQuestion[] KATAKANA_3_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ジャ", "ja"), new KanaQuestion("ジュ", "ju"), new KanaQuestion("ジョ", "jo")
    };

    private static final KanaQuestion[] KATAKANA_4_BASE = {
            new KanaQuestion("タ", "ta"), new KanaQuestion("チ", "chi"), new KanaQuestion("ツ", "tsu"),
            new KanaQuestion("テ", "te"), new KanaQuestion("ト", "to")
    };

    private static final KanaQuestion[] KATAKANA_4_DAKUTEN = {
            new KanaQuestion("ダ", "da"), new KanaQuestion("ヂ", "ji"), new KanaQuestion("ヅ", "zu"),
            new KanaQuestion("デ", "de"), new KanaQuestion("ド", "do")
    };

    private static final KanaQuestion[] KATAKANA_4_BASE_DIGRAPHS = {
            new KanaQuestion("チャ", "cha"), new KanaQuestion("チュ", "chu"), new KanaQuestion("チョ", "cho")
    };

    private static final KanaQuestion[] KATAKANA_4_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ヂャ", "ja"), new KanaQuestion("ヂュ", "ju"), new KanaQuestion("ヂョ", "jo")
    };

    private static final KanaQuestion[] KATAKANA_5 = {
            new KanaQuestion("ナ", "na"), new KanaQuestion("ニ", "ni"), new KanaQuestion("ヌ", "nu"),
            new KanaQuestion("ネ", "ne"), new KanaQuestion("ノ", "no")
    };

    private static final KanaQuestion[] KATAKANA_5_DIGRAPHS = {
            new KanaQuestion("ニャ", "nya"), new KanaQuestion("ニュ", "nyu"), new KanaQuestion("ニョ", "nyo")
    };

    private static final KanaQuestion[] KATAKANA_6_BASE = {
            new KanaQuestion("ハ", "ha"), new KanaQuestion("ヒ", "hi"), new KanaQuestion("フ", "fu"),
            new KanaQuestion("ヘ", "he"), new KanaQuestion("ホ", "ho")
    };

    private static final KanaQuestion[] KATAKANA_6_DAKUTEN = {
            new KanaQuestion("バ", "ba"), new KanaQuestion("ビ", "bi"), new KanaQuestion("ブ", "bu"),
            new KanaQuestion("ベ", "be"), new KanaQuestion("ボ", "bo")
    };

    private static final KanaQuestion[] KATAKANA_6_HANDAKUTEN = {
            new KanaQuestion("パ", "pa"), new KanaQuestion("ピ", "pi"), new KanaQuestion("プ", "pu"),
            new KanaQuestion("ペ", "pe"), new KanaQuestion("ポ", "po")
    };

    private static final KanaQuestion[] KATAKANA_6_BASE_DIGRAPHS = {
            new KanaQuestion("ヒャ", "hya"), new KanaQuestion("ヒュ", "hyu"), new KanaQuestion("ヒョ", "hyo")
    };

    private static final KanaQuestion[] KATAKANA_6_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ビャ", "bya"), new KanaQuestion("ビュ", "byu"), new KanaQuestion("ビョ", "byo")
    };

    private static final KanaQuestion[] KATAKANA_6_HANDAKUTEN_DIGRAPHS = {
            new KanaQuestion("ピャ", "pya"), new KanaQuestion("ピュ", "pyu"), new KanaQuestion("ピョ", "pyo")
    };

    private static final KanaQuestion[] KATAKANA_7 = {
            new KanaQuestion("マ", "ma"), new KanaQuestion("ミ", "mi"), new KanaQuestion("ム", "mu"),
            new KanaQuestion("メ", "me"), new KanaQuestion("モ", "mo")
    };

    private static final KanaQuestion[] KATAKANA_7_DIGRAPHS = {
            new KanaQuestion("ミャ", "mya"), new KanaQuestion("ミュ", "myu"), new KanaQuestion("ミョ", "myo")
    };

    private static final KanaQuestion[] KATAKANA_8 = {
            new KanaQuestion("ラ", "ra"), new KanaQuestion("リ", "ri"), new KanaQuestion("ル", "ru"),
            new KanaQuestion("レ", "re"), new KanaQuestion("ロ", "ro")
    };

    private static final KanaQuestion[] KATAKANA_8_DIGRAPHS = {
            new KanaQuestion("リャ", "rya"), new KanaQuestion("リュ", "ryu"), new KanaQuestion("リョ", "ryo")
    };

    private static final KanaQuestion[] KATAKANA_9 = {
            new KanaQuestion("ヤ", "ya"), new KanaQuestion("ユ", "yu"), new KanaQuestion("ヨ", "yo")
    };

    private static final KanaQuestion[] KATAKANA_10_W_GROUP = {
            new KanaQuestion("ワ", "wa"), new KanaQuestion("ヲ", "wo")
    };

    private static final KanaQuestion[] KATAKANA_10_N_CONSONANT = {
            new KanaQuestion("ン", "n")
    };

    @Test
    public void isDigraphTest()
    {
        assertDigraphTest(HIRAGANA_1, false);
        assertDigraphTest(HIRAGANA_2_BASE, false);
        assertDigraphTest(HIRAGANA_2_BASE_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_2_DAKUTEN, false);
        assertDigraphTest(HIRAGANA_2_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_3_BASE, false);
        assertDigraphTest(HIRAGANA_3_BASE_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_3_DAKUTEN, false);
        assertDigraphTest(HIRAGANA_3_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_4_BASE, false);
        assertDigraphTest(HIRAGANA_4_BASE_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_4_DAKUTEN, false);
        assertDigraphTest(HIRAGANA_4_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_5, false);
        assertDigraphTest(HIRAGANA_5_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_6_BASE, false);
        assertDigraphTest(HIRAGANA_6_BASE_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_6_DAKUTEN, false);
        assertDigraphTest(HIRAGANA_6_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_6_HANDAKUTEN, false);
        assertDigraphTest(HIRAGANA_6_HANDAKUTEN_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_7, false);
        assertDigraphTest(HIRAGANA_7_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_8, false);
        assertDigraphTest(HIRAGANA_8_DIGRAPHS, true);
        assertDigraphTest(HIRAGANA_9, false);
        assertDigraphTest(HIRAGANA_10_W_GROUP, false);
        assertDigraphTest(HIRAGANA_10_N_CONSONANT, false);

        assertDigraphTest(KATAKANA_1, false);
        assertDigraphTest(KATAKANA_2_BASE, false);
        assertDigraphTest(KATAKANA_2_BASE_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_2_DAKUTEN, false);
        assertDigraphTest(KATAKANA_2_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_3_BASE, false);
        assertDigraphTest(KATAKANA_3_BASE_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_3_DAKUTEN, false);
        assertDigraphTest(KATAKANA_3_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_4_BASE, false);
        assertDigraphTest(KATAKANA_4_BASE_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_4_DAKUTEN, false);
        assertDigraphTest(KATAKANA_4_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_5, false);
        assertDigraphTest(KATAKANA_5_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_6_BASE, false);
        assertDigraphTest(KATAKANA_6_BASE_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_6_DAKUTEN, false);
        assertDigraphTest(KATAKANA_6_DAKUTEN_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_6_HANDAKUTEN, false);
        assertDigraphTest(KATAKANA_6_HANDAKUTEN_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_7, false);
        assertDigraphTest(KATAKANA_7_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_8, false);
        assertDigraphTest(KATAKANA_8_DIGRAPHS, true);
        assertDigraphTest(KATAKANA_9, false);
        assertDigraphTest(KATAKANA_10_W_GROUP, false);
        assertDigraphTest(KATAKANA_10_N_CONSONANT, false);
    }

    @Test
    public void isDiacriticTest()
    {
        assertDiacriticTest(HIRAGANA_1, false);
        assertDiacriticTest(HIRAGANA_2_BASE, false);
        assertDiacriticTest(HIRAGANA_2_BASE_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_2_DAKUTEN, true);
        assertDiacriticTest(HIRAGANA_2_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(HIRAGANA_3_BASE, false);
        assertDiacriticTest(HIRAGANA_3_BASE_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_3_DAKUTEN, true);
        assertDiacriticTest(HIRAGANA_3_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(HIRAGANA_4_BASE, false);
        assertDiacriticTest(HIRAGANA_4_BASE_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_4_DAKUTEN, true);
        assertDiacriticTest(HIRAGANA_4_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(HIRAGANA_5, false);
        assertDiacriticTest(HIRAGANA_5_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_6_BASE, false);
        assertDiacriticTest(HIRAGANA_6_BASE_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_6_DAKUTEN, true);
        assertDiacriticTest(HIRAGANA_6_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(HIRAGANA_6_HANDAKUTEN, true);
        assertDiacriticTest(HIRAGANA_6_HANDAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(HIRAGANA_7, false);
        assertDiacriticTest(HIRAGANA_7_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_8, false);
        assertDiacriticTest(HIRAGANA_8_DIGRAPHS, false);
        assertDiacriticTest(HIRAGANA_9, false);
        assertDiacriticTest(HIRAGANA_10_W_GROUP, false);
        assertDiacriticTest(HIRAGANA_10_N_CONSONANT, false);

        assertDiacriticTest(KATAKANA_1, false);
        assertDiacriticTest(KATAKANA_2_BASE, false);
        assertDiacriticTest(KATAKANA_2_BASE_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_2_DAKUTEN, true);
        assertDiacriticTest(KATAKANA_2_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(KATAKANA_3_BASE, false);
        assertDiacriticTest(KATAKANA_3_BASE_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_3_DAKUTEN, true);
        assertDiacriticTest(KATAKANA_3_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(KATAKANA_4_BASE, false);
        assertDiacriticTest(KATAKANA_4_BASE_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_4_DAKUTEN, true);
        assertDiacriticTest(KATAKANA_4_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(KATAKANA_5, false);
        assertDiacriticTest(KATAKANA_5_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_6_BASE, false);
        assertDiacriticTest(KATAKANA_6_BASE_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_6_DAKUTEN, true);
        assertDiacriticTest(KATAKANA_6_DAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(KATAKANA_6_HANDAKUTEN, true);
        assertDiacriticTest(KATAKANA_6_HANDAKUTEN_DIGRAPHS, true);
        assertDiacriticTest(KATAKANA_7, false);
        assertDiacriticTest(KATAKANA_7_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_8, false);
        assertDiacriticTest(KATAKANA_8_DIGRAPHS, false);
        assertDiacriticTest(KATAKANA_9, false);
        assertDiacriticTest(KATAKANA_10_W_GROUP, false);
        assertDiacriticTest(KATAKANA_10_N_CONSONANT, false);
    }

    @Test
    public void isDiacriticCharTest()
    {
        /*
        Unicode chart:
        	ぁ	あ	ぃ	い	ぅ	う	ぇ	え	ぉ	お	か	が	き	ぎ	く
        ぐ	け	げ	こ	ご	さ	ざ	し	じ	す	ず	せ	ぜ	そ	ぞ	た
        だ	ち	ぢ	っ	つ	づ	て	で	と	ど	な	に	ぬ	ね	の	は
        ば	ぱ	ひ	び	ぴ	ふ	ぶ	ぷ	へ	べ	ぺ	ほ	ぼ	ぽ	ま	み
        む	め	も	ゃ	や	ゅ	ゆ	ょ	よ	ら	り	る	れ	ろ	ゎ	わ
        ゐ	ゑ	を	ん	ゔ	ゕ	ゖ			゙	゚	゛	゜	ゝ	ゞ	ゟ
        ゠	ァ	ア	ィ	イ	ゥ	ウ	ェ	エ	ォ	オ	カ	ガ	キ	ギ	ク
        グ	ケ	ゲ	コ	ゴ	サ	ザ	シ	ジ	ス	ズ	セ	ゼ	ソ	ゾ	タ
        ダ	チ	ヂ	ッ	ツ	ヅ	テ	デ	ト	ド	ナ	ニ	ヌ	ネ	ノ	ハ
        バ	パ	ヒ	ビ	ピ	フ	ブ	プ	ヘ	ベ	ペ	ホ	ボ	ポ	マ	ミ
        ム	メ	モ	ャ	ヤ	ュ	ユ	ョ	ヨ	ラ	リ	ル	レ	ロ	ヮ	ワ
        ヰ	ヱ	ヲ	ン	ヴ	ヵ	ヶ	ヷ	ヸ	ヹ	ヺ	・	ー	ヽ	ヾ	ヿ
        */

        assertThat(KanaQuestion.isDiacritic('ぁ'), is(false));
        assertThat(KanaQuestion.isDiacritic('あ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ぃ'), is(false));
        assertThat(KanaQuestion.isDiacritic('い'), is(false));
        assertThat(KanaQuestion.isDiacritic('ぅ'), is(false));
        assertThat(KanaQuestion.isDiacritic('う'), is(false));
        assertThat(KanaQuestion.isDiacritic('ぇ'), is(false));
        assertThat(KanaQuestion.isDiacritic('え'), is(false));
        assertThat(KanaQuestion.isDiacritic('ぉ'), is(false));
        assertThat(KanaQuestion.isDiacritic('お'), is(false));
        assertThat(KanaQuestion.isDiacritic('か'), is(false));
        assertThat(KanaQuestion.isDiacritic('が'), is(true));
        assertThat(KanaQuestion.isDiacritic('き'), is(false));
        assertThat(KanaQuestion.isDiacritic('ぎ'), is(true));
        assertThat(KanaQuestion.isDiacritic('く'), is(false));
        assertThat(KanaQuestion.isDiacritic('ぐ'), is(true));
        assertThat(KanaQuestion.isDiacritic('け'), is(false));
        assertThat(KanaQuestion.isDiacritic('げ'), is(true));
        assertThat(KanaQuestion.isDiacritic('こ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ご'), is(true));
        assertThat(KanaQuestion.isDiacritic('さ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ざ'), is(true));
        assertThat(KanaQuestion.isDiacritic('し'), is(false));
        assertThat(KanaQuestion.isDiacritic('じ'), is(true));
        assertThat(KanaQuestion.isDiacritic('す'), is(false));
        assertThat(KanaQuestion.isDiacritic('ず'), is(true));
        assertThat(KanaQuestion.isDiacritic('せ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ぜ'), is(true));
        assertThat(KanaQuestion.isDiacritic('そ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ぞ'), is(true));
        assertThat(KanaQuestion.isDiacritic('た'), is(false));
        assertThat(KanaQuestion.isDiacritic('だ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ち'), is(false));
        assertThat(KanaQuestion.isDiacritic('ぢ'), is(true));
        assertThat(KanaQuestion.isDiacritic('っ'), is(false));
        assertThat(KanaQuestion.isDiacritic('つ'), is(false));
        assertThat(KanaQuestion.isDiacritic('づ'), is(true));
        assertThat(KanaQuestion.isDiacritic('て'), is(false));
        assertThat(KanaQuestion.isDiacritic('で'), is(true));
        assertThat(KanaQuestion.isDiacritic('と'), is(false));
        assertThat(KanaQuestion.isDiacritic('ど'), is(true));
        assertThat(KanaQuestion.isDiacritic('な'), is(false));
        assertThat(KanaQuestion.isDiacritic('に'), is(false));
        assertThat(KanaQuestion.isDiacritic('ぬ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ね'), is(false));
        assertThat(KanaQuestion.isDiacritic('の'), is(false));
        assertThat(KanaQuestion.isDiacritic('は'), is(false));
        assertThat(KanaQuestion.isDiacritic('ば'), is(true));
        assertThat(KanaQuestion.isDiacritic('ぱ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ひ'), is(false));
        assertThat(KanaQuestion.isDiacritic('び'), is(true));
        assertThat(KanaQuestion.isDiacritic('ぴ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ふ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ぶ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ぷ'), is(true));
        assertThat(KanaQuestion.isDiacritic('へ'), is(false));
        assertThat(KanaQuestion.isDiacritic('べ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ぺ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ほ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ぼ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ぽ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ま'), is(false));
        assertThat(KanaQuestion.isDiacritic('み'), is(false));
        assertThat(KanaQuestion.isDiacritic('む'), is(false));
        assertThat(KanaQuestion.isDiacritic('め'), is(false));
        assertThat(KanaQuestion.isDiacritic('も'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゃ'), is(false));
        assertThat(KanaQuestion.isDiacritic('や'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゅ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゆ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ょ'), is(false));
        assertThat(KanaQuestion.isDiacritic('よ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ら'), is(false));
        assertThat(KanaQuestion.isDiacritic('り'), is(false));
        assertThat(KanaQuestion.isDiacritic('る'), is(false));
        assertThat(KanaQuestion.isDiacritic('れ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ろ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゎ'), is(false));
        assertThat(KanaQuestion.isDiacritic('わ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゐ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゑ'), is(false));
        assertThat(KanaQuestion.isDiacritic('を'), is(false));
        assertThat(KanaQuestion.isDiacritic('ん'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゔ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ゕ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゖ'), is(false));
        assertThat(KanaQuestion.isDiacritic('゙'), is(true));
        assertThat(KanaQuestion.isDiacritic('゚'), is(true));
        assertThat(KanaQuestion.isDiacritic('゛'), is(true));
        assertThat(KanaQuestion.isDiacritic('゜'), is(true));
        assertThat(KanaQuestion.isDiacritic('ゝ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゞ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ゟ'), is(false));
        assertThat(KanaQuestion.isDiacritic('゠'), is(false));
        assertThat(KanaQuestion.isDiacritic('ァ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ア'), is(false));
        assertThat(KanaQuestion.isDiacritic('ィ'), is(false));
        assertThat(KanaQuestion.isDiacritic('イ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゥ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ウ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ェ'), is(false));
        assertThat(KanaQuestion.isDiacritic('エ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ォ'), is(false));
        assertThat(KanaQuestion.isDiacritic('オ'), is(false));
        assertThat(KanaQuestion.isDiacritic('カ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ガ'), is(true));
        assertThat(KanaQuestion.isDiacritic('キ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ギ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ク'), is(false));
        assertThat(KanaQuestion.isDiacritic('グ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ケ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゲ'), is(true));
        assertThat(KanaQuestion.isDiacritic('コ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゴ'), is(true));
        assertThat(KanaQuestion.isDiacritic('サ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ザ'), is(true));
        assertThat(KanaQuestion.isDiacritic('シ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ジ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ス'), is(false));
        assertThat(KanaQuestion.isDiacritic('ズ'), is(true));
        assertThat(KanaQuestion.isDiacritic('セ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゼ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ソ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ゾ'), is(true));
        assertThat(KanaQuestion.isDiacritic('タ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ダ'), is(true));
        assertThat(KanaQuestion.isDiacritic('チ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヂ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ッ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ツ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヅ'), is(true));
        assertThat(KanaQuestion.isDiacritic('テ'), is(false));
        assertThat(KanaQuestion.isDiacritic('デ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ト'), is(false));
        assertThat(KanaQuestion.isDiacritic('ド'), is(true));
        assertThat(KanaQuestion.isDiacritic('ナ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ニ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヌ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ネ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ノ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ハ'), is(false));
        assertThat(KanaQuestion.isDiacritic('バ'), is(true));
        assertThat(KanaQuestion.isDiacritic('パ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ヒ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ビ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ピ'), is(true));
        assertThat(KanaQuestion.isDiacritic('フ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ブ'), is(true));
        assertThat(KanaQuestion.isDiacritic('プ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ヘ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ベ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ペ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ホ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ボ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ポ'), is(true));
        assertThat(KanaQuestion.isDiacritic('マ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ミ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ム'), is(false));
        assertThat(KanaQuestion.isDiacritic('メ'), is(false));
        assertThat(KanaQuestion.isDiacritic('モ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ャ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヤ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ュ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ユ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ョ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヨ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ラ'), is(false));
        assertThat(KanaQuestion.isDiacritic('リ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ル'), is(false));
        assertThat(KanaQuestion.isDiacritic('レ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ロ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヮ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ワ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヰ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヱ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヲ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ン'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヴ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ヵ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヶ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヷ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ヸ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ヹ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ヺ'), is(true));
        assertThat(KanaQuestion.isDiacritic('・'), is(false));
        assertThat(KanaQuestion.isDiacritic('ー'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヽ'), is(false));
        assertThat(KanaQuestion.isDiacritic('ヾ'), is(true));
        assertThat(KanaQuestion.isDiacritic('ヿ'), is(false));
    }

    @Test
    public void whatKanaSystemTest()
    {
        assertThat(KanaQuestion.whatKanaSystem('〺'), is(KanaQuestion.KanaSystem.NO_KANA));
        assertThat(KanaQuestion.whatKanaSystem('ぁ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('あ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぃ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('い'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぅ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('う'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぇ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('え'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぉ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('お'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('か'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('が'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('き'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぎ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('く'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぐ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('け'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('げ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('こ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ご'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('さ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ざ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('し'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('じ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('す'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ず'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('せ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぜ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('そ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぞ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('た'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('だ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ち'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぢ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('っ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('つ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('づ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('て'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('で'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('と'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ど'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('な'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('に'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぬ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ね'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('の'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('は'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ば'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぱ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ひ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('び'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぴ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ふ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぶ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぷ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('へ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('べ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぺ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ほ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぼ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ぽ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ま'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('み'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('む'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('め'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('も'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ゃ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('や'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ゅ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ゆ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ょ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('よ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ら'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('り'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('る'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('れ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ろ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ゎ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('わ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ゐ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ゑ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('を'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ん'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ゔ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ゕ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ゖ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('゙'), is(KanaQuestion.KanaSystem.DIACRITIC_MARK));
        assertThat(KanaQuestion.whatKanaSystem('゚'), is(KanaQuestion.KanaSystem.DIACRITIC_MARK));
        assertThat(KanaQuestion.whatKanaSystem('゛'), is(KanaQuestion.KanaSystem.DIACRITIC_MARK));
        assertThat(KanaQuestion.whatKanaSystem('゜'), is(KanaQuestion.KanaSystem.DIACRITIC_MARK));
        assertThat(KanaQuestion.whatKanaSystem('ゝ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ゞ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('ゟ'), is(KanaQuestion.KanaSystem.HIRAGANA));
        assertThat(KanaQuestion.whatKanaSystem('゠'), is(KanaQuestion.KanaSystem.BOTH_KANA));
        assertThat(KanaQuestion.whatKanaSystem('ァ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ア'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ィ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('イ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ゥ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ウ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ェ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('エ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ォ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('オ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('カ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ガ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('キ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ギ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ク'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('グ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ケ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ゲ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('コ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ゴ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('サ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ザ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('シ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ジ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ス'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ズ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('セ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ゼ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ソ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ゾ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('タ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ダ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('チ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヂ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ッ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ツ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヅ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('テ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('デ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ト'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ド'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ナ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ニ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヌ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ネ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ノ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ハ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('バ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('パ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヒ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ビ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ピ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('フ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ブ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('プ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヘ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ベ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ペ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ホ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ボ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ポ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('マ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ミ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ム'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('メ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('モ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ャ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヤ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ュ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ユ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ョ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヨ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ラ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('リ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ル'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('レ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ロ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヮ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ワ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヰ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヱ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヲ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ン'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヴ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヵ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヶ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヷ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヸ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヹ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヺ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('・'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ー'), is(KanaQuestion.KanaSystem.BOTH_KANA));
        assertThat(KanaQuestion.whatKanaSystem('ヽ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヾ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ヿ'), is(KanaQuestion.KanaSystem.KATAKANA));
        assertThat(KanaQuestion.whatKanaSystem('ㄅ'), is(KanaQuestion.KanaSystem.NO_KANA));
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