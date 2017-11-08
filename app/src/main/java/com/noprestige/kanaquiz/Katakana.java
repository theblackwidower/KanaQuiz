package com.noprestige.kanaquiz;

import static com.noprestige.kanaquiz.Diacritic.CONSONANT;
import static com.noprestige.kanaquiz.Diacritic.DAKUTEN;
import static com.noprestige.kanaquiz.Diacritic.HANDAKUTEN;
import static com.noprestige.kanaquiz.Diacritic.NO_DIACRITIC;

class Katakana extends QuestionManagement
{
    private static final KanaQuestion[] KANA_SET_1 = {
            new KanaQuestion('ア', "a"),
            new KanaQuestion('イ', "i"),
            new KanaQuestion('ウ', "u"),
            new KanaQuestion('エ', "e"),
            new KanaQuestion('オ', "o")};

    private static final KanaQuestion[] KANA_SET_2_BASE = {
            new KanaQuestion('カ', "ka"),
            new KanaQuestion('キ', "ki"),
            new KanaQuestion('ク', "ku"),
            new KanaQuestion('ケ', "ke"),
            new KanaQuestion('コ', "ko")};

    private static final KanaQuestion[] KANA_SET_2_DAKUTEN = {
            new KanaQuestion('ガ', "ga"),
            new KanaQuestion('ギ', "gi"),
            new KanaQuestion('グ', "gu"),
            new KanaQuestion('ゲ', "ge"),
            new KanaQuestion('ゴ', "go")};

    private static final KanaQuestion[] KANA_SET_2_BASE_DIGRAPHS = {
            new KanaQuestion("キャ", "kya"),
            new KanaQuestion("キュ", "kyu"),
            new KanaQuestion("キョ", "kyo")};

    private static final KanaQuestion[] KANA_SET_2_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ギャ", "gya"),
            new KanaQuestion("ギュ", "gyu"),
            new KanaQuestion("ギョ", "gyo")};

    private static final KanaQuestion[] KANA_SET_3_BASE = {
            new KanaQuestion('サ', "sa"),
            new KanaQuestion('シ', "shi"),
            new KanaQuestion('ス', "su"),
            new KanaQuestion('セ', "se"),
            new KanaQuestion('ソ', "so")};

    private static final KanaQuestion[] KANA_SET_3_DAKUTEN = {
            new KanaQuestion('ザ', "za"),
            new KanaQuestion('ジ', "ji"),
            new KanaQuestion('ズ', "zu"),
            new KanaQuestion('ゼ', "ze"),
            new KanaQuestion('ゾ', "zo")};

    private static final KanaQuestion[] KANA_SET_3_BASE_DIGRAPHS = {
            new KanaQuestion("シャ", "sha"),
            new KanaQuestion("シュ", "shu"),
            new KanaQuestion("ショ", "sho")};

    private static final KanaQuestion[] KANA_SET_3_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ジャ", new String[]{"ja", "jya"}),
            new KanaQuestion("ジュ", new String[]{"ju", "jyu"}),
            new KanaQuestion("ジョ", new String[]{"jo", "jyo"})};

    private static final KanaQuestion[] KANA_SET_4_BASE = {
            new KanaQuestion('タ', "ta"),
            new KanaQuestion('チ', "chi"),
            new KanaQuestion('ツ', "tsu"),
            new KanaQuestion('テ', "te"),
            new KanaQuestion('ト', "to")};

    private static final KanaQuestion[] KANA_SET_4_DAKUTEN = {
            new KanaQuestion('ダ', "da"),
            new KanaQuestion('ヂ', "ji"),
            new KanaQuestion('ヅ', "zu"),
            new KanaQuestion('デ', "de"),
            new KanaQuestion('ド', "do")};

    private static final KanaQuestion[] KANA_SET_4_BASE_DIGRAPHS = {
            new KanaQuestion("チャ", "cha"),
            new KanaQuestion("チュ", "chu"),
            new KanaQuestion("チョ", "cho")};

    private static final KanaQuestion[] KANA_SET_4_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ヂャ", new String[]{"ja", "dzya"}),
            new KanaQuestion("ヂュ", new String[]{"ju", "dzyu"}),
            new KanaQuestion("ヂョ", new String[]{"jo", "dzyo"})};

    private static final KanaQuestion[] KANA_SET_5 = {
            new KanaQuestion('ナ', "na"),
            new KanaQuestion('ニ', "ni"),
            new KanaQuestion('ヌ', "nu"),
            new KanaQuestion('ネ', "ne"),
            new KanaQuestion('ノ', "no")};

    private static final KanaQuestion[] KANA_SET_5_DIGRAPHS = {
            new KanaQuestion("ニャ", "nya"),
            new KanaQuestion("ニュ", "nyu"),
            new KanaQuestion("ニョ", "nyo")};

    private static final KanaQuestion[] KANA_SET_6_BASE = {
            new KanaQuestion('ハ', "ha"),
            new KanaQuestion('ヒ', "hi"),
            new KanaQuestion('フ', new String[]{"fu", "hu"}),
            new KanaQuestion('ヘ', "he"),
            new KanaQuestion('ホ', "ho")};

    private static final KanaQuestion[] KANA_SET_6_DAKUTEN = {
            new KanaQuestion('バ', "ba"),
            new KanaQuestion('ビ', "bi"),
            new KanaQuestion('ブ', "bu"),
            new KanaQuestion('ベ', "be"),
            new KanaQuestion('ボ', "bo")};

    private static final KanaQuestion[] KANA_SET_6_HANDAKUTEN = {
            new KanaQuestion('パ', "pa"),
            new KanaQuestion('ピ', "pi"),
            new KanaQuestion('プ', "pu"),
            new KanaQuestion('ペ', "pe"),
            new KanaQuestion('ポ', "po")};

    private static final KanaQuestion[] KANA_SET_6_BASE_DIGRAPHS = {
            new KanaQuestion("ヒャ", "hya"),
            new KanaQuestion("ヒュ", "hyu"),
            new KanaQuestion("ヒョ", "hyo")};

    private static final KanaQuestion[] KANA_SET_6_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ビャ", "bya"),
            new KanaQuestion("ビュ", "byu"),
            new KanaQuestion("ビョ", "byo")};

    private static final KanaQuestion[] KANA_SET_6_HANDAKUTEN_DIGRAPHS = {
            new KanaQuestion("ピャ", "pya"),
            new KanaQuestion("ピュ", "pyu"),
            new KanaQuestion("ピョ", "pyo")};

    private static final KanaQuestion[] KANA_SET_7 = {
            new KanaQuestion('マ', "ma"),
            new KanaQuestion('ミ', "mi"),
            new KanaQuestion('ム', "mu"),
            new KanaQuestion('メ', "me"),
            new KanaQuestion('モ', "mo")};

    private static final KanaQuestion[] KANA_SET_7_DIGRAPHS = {
            new KanaQuestion("ミャ", "mya"),
            new KanaQuestion("ミュ", "myu"),
            new KanaQuestion("ミョ", "myo")};

    private static final KanaQuestion[] KANA_SET_8 = {
            new KanaQuestion('ラ', "ra"),
            new KanaQuestion('リ', "ri"),
            new KanaQuestion('ル', "ru"),
            new KanaQuestion('レ', "re"),
            new KanaQuestion('ロ', "ro")};

    private static final KanaQuestion[] KANA_SET_8_DIGRAPHS = {
            new KanaQuestion("リャ", "rya"),
            new KanaQuestion("リュ", "ryu"),
            new KanaQuestion("リョ", "ryo")};

    private static final KanaQuestion[] KANA_SET_9 = {
            new KanaQuestion('ヤ', "ya"),
            new KanaQuestion('ユ', "yu"),
            new KanaQuestion('ヨ', "yo")};

    private static final KanaQuestion[] KANA_SET_10_W_GROUP = {
            new KanaQuestion('ワ', "wa"),
            new KanaQuestion('ヲ', "wo")};

    private static final KanaQuestion[] KANA_SET_10_N_CONSONANT = {
            new KanaQuestion('ン', "n")};

    private static final int PREFID_1 = R.string.prefid_katakana_1;
    private static final int PREFID_2 = R.string.prefid_katakana_2;
    private static final int PREFID_3 = R.string.prefid_katakana_3;
    private static final int PREFID_4 = R.string.prefid_katakana_4;
    private static final int PREFID_5 = R.string.prefid_katakana_5;
    private static final int PREFID_6 = R.string.prefid_katakana_6;
    private static final int PREFID_7 = R.string.prefid_katakana_7;
    private static final int PREFID_8 = R.string.prefid_katakana_8;
    private static final int PREFID_9 = R.string.prefid_katakana_9;
    private static final int PREFID_10 = R.string.prefid_katakana_10;

    static final Katakana QUESTIONS = new Katakana(); //Must remain the last constant generated

    private Katakana()
    {
        addKanaSet(KANA_SET_1, 1);

        addKanaSet(KANA_SET_2_BASE, 2);
        addKanaSet(KANA_SET_2_DAKUTEN, 2, DAKUTEN);
        addKanaSet(KANA_SET_2_BASE_DIGRAPHS, 2, NO_DIACRITIC, true);
        addKanaSet(KANA_SET_2_DAKUTEN_DIGRAPHS, 2, DAKUTEN, true);

        addKanaSet(KANA_SET_3_BASE, 3);
        addKanaSet(KANA_SET_3_DAKUTEN, 3, DAKUTEN);
        addKanaSet(KANA_SET_3_BASE_DIGRAPHS, 3, NO_DIACRITIC, true);
        addKanaSet(KANA_SET_3_DAKUTEN_DIGRAPHS, 3, DAKUTEN, true);

        addKanaSet(KANA_SET_4_BASE, 4);
        addKanaSet(KANA_SET_4_DAKUTEN, 4, DAKUTEN);
        addKanaSet(KANA_SET_4_BASE_DIGRAPHS, 4, NO_DIACRITIC, true);
        addKanaSet(KANA_SET_4_DAKUTEN_DIGRAPHS, 4, DAKUTEN, true);

        addKanaSet(KANA_SET_5, 5);
        addKanaSet(KANA_SET_5_DIGRAPHS, 5, NO_DIACRITIC, true);

        addKanaSet(KANA_SET_6_BASE, 6);
        addKanaSet(KANA_SET_6_DAKUTEN, 6, DAKUTEN);
        addKanaSet(KANA_SET_6_HANDAKUTEN, 6, HANDAKUTEN);
        addKanaSet(KANA_SET_6_BASE_DIGRAPHS, 6, NO_DIACRITIC, true);
        addKanaSet(KANA_SET_6_DAKUTEN_DIGRAPHS, 6, DAKUTEN, true);
        addKanaSet(KANA_SET_6_HANDAKUTEN_DIGRAPHS, 6, HANDAKUTEN, true);

        addKanaSet(KANA_SET_7, 7);
        addKanaSet(KANA_SET_7_DIGRAPHS, 7, NO_DIACRITIC, true);

        addKanaSet(KANA_SET_8, 8);
        addKanaSet(KANA_SET_8_DIGRAPHS, 8, NO_DIACRITIC, true);

        addKanaSet(KANA_SET_9, 9);

        addKanaSet(KANA_SET_10_W_GROUP, 10);
        addKanaSet(KANA_SET_10_N_CONSONANT, 10, CONSONANT);

        addPrefIds(new int[]{PREFID_1, PREFID_2, PREFID_3, PREFID_4, PREFID_5,
                PREFID_6, PREFID_7, PREFID_8, PREFID_9, PREFID_10});
    }
}
