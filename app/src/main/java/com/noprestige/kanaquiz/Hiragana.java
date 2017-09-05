package com.noprestige.kanaquiz;

import static com.noprestige.kanaquiz.Diacritic.CONSONANT;
import static com.noprestige.kanaquiz.Diacritic.DAKUTEN;
import static com.noprestige.kanaquiz.Diacritic.HANDAKUTEN;
import static com.noprestige.kanaquiz.Diacritic.NO_DIACRITIC;

class Hiragana extends QuestionManagement
{
    private static final KanaQuestion[] KANA_SET_1 = {
            new KanaQuestion('あ', "a"),
            new KanaQuestion('い', "i"),
            new KanaQuestion('う', "u"),
            new KanaQuestion('え', "e"),
            new KanaQuestion('お', "o")};

    private static final KanaQuestion[] KANA_SET_2_BASE = {
            new KanaQuestion('か', "ka"),
            new KanaQuestion('き', "ki"),
            new KanaQuestion('く', "ku"),
            new KanaQuestion('け', "ke"),
            new KanaQuestion('こ', "ko")};

    private static final KanaQuestion[] KANA_SET_2_DAKUTEN = {
            new KanaQuestion('が', "ga"),
            new KanaQuestion('ぎ', "gi"),
            new KanaQuestion('ぐ', "gu"),
            new KanaQuestion('げ', "ge"),
            new KanaQuestion('ご', "go")};

    private static final KanaQuestion[] KANA_SET_2_BASE_DIGRAPHS = {
            new KanaQuestion("きゃ", "kya"),
            new KanaQuestion("きゅ", "kyu"),
            new KanaQuestion("きょ", "kyo")};

    private static final KanaQuestion[] KANA_SET_2_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ぎゃ", "gya"),
            new KanaQuestion("ぎゅ", "gyu"),
            new KanaQuestion("ぎょ", "gyo")};

    private static final KanaQuestion[] KANA_SET_3_BASE = {
            new KanaQuestion('さ', "sa"),
            new KanaQuestion('し', "shi"),
            new KanaQuestion('す', "su"),
            new KanaQuestion('せ', "se"),
            new KanaQuestion('そ', "so")};

    private static final KanaQuestion[] KANA_SET_3_DAKUTEN = {
            new KanaQuestion('ざ', "za"),
            new KanaQuestion('じ', "ji"),
            new KanaQuestion('ず', "zu"),
            new KanaQuestion('ぜ', "ze"),
            new KanaQuestion('ぞ', "zo")};

    private static final KanaQuestion[] KANA_SET_3_BASE_DIGRAPHS = {
            new KanaQuestion("しゃ", "sha"),
            new KanaQuestion("しゅ", "shu"),
            new KanaQuestion("しょ", "sho")};

    private static final KanaQuestion[] KANA_SET_3_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("じゃ", new String[]{"ja", "jya"}),
            new KanaQuestion("じゅ", new String[]{"ju", "jyu"}),
            new KanaQuestion("じょ", new String[]{"jo", "jyo"})};

    private static final KanaQuestion[] KANA_SET_4_BASE = {
            new KanaQuestion('た', "ta"),
            new KanaQuestion('ち', "chi"),
            new KanaQuestion('つ', "tsu"),
            new KanaQuestion('て', "te"),
            new KanaQuestion('と', "to")};

    private static final KanaQuestion[] KANA_SET_4_DAKUTEN = {
            new KanaQuestion('だ', "da"),
            new KanaQuestion('ぢ', "ji"),
            new KanaQuestion('づ', "zu"),
            new KanaQuestion('で', "de"),
            new KanaQuestion('ど', "do")};

    private static final KanaQuestion[] KANA_SET_4_BASE_DIGRAPHS = {
            new KanaQuestion("ちゃ", "cha"),
            new KanaQuestion("ちゅ", "chu"),
            new KanaQuestion("ちょ", "cho")};

    private static final KanaQuestion[] KANA_SET_4_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("ぢゃ", new String[]{"ja", "dzya"}),
            new KanaQuestion("ぢゅ", new String[]{"ju", "dzyu"}),
            new KanaQuestion("ぢょ", new String[]{"jo", "dzyo"})};

    private static final KanaQuestion[] KANA_SET_5 = {
            new KanaQuestion('な', "na"),
            new KanaQuestion('に', "ni"),
            new KanaQuestion('ぬ', "nu"),
            new KanaQuestion('ね', "ne"),
            new KanaQuestion('の', "no")};

    private static final KanaQuestion[] KANA_SET_5_DIGRAPHS = {
            new KanaQuestion("にゃ", "nya"),
            new KanaQuestion("にゅ", "nyu"),
            new KanaQuestion("にょ", "nyo")};

    private static final KanaQuestion[] KANA_SET_6_BASE = {
            new KanaQuestion('は', "ha"),
            new KanaQuestion('ひ', "hi"),
            new KanaQuestion('ふ', new String[]{"fu", "hu"}),
            new KanaQuestion('へ', "he"),
            new KanaQuestion('ほ', "ho")};

    private static final KanaQuestion[] KANA_SET_6_DAKUTEN = {
            new KanaQuestion('ば', "ba"),
            new KanaQuestion('び', "bi"),
            new KanaQuestion('ぶ', "bu"),
            new KanaQuestion('べ', "be"),
            new KanaQuestion('ぼ', "bo")};

    private static final KanaQuestion[] KANA_SET_6_HANDAKETEN = {
            new KanaQuestion('ぱ', "pa"),
            new KanaQuestion('ぴ', "pi"),
            new KanaQuestion('ぷ', "pu"),
            new KanaQuestion('ぺ', "pe"),
            new KanaQuestion('ぽ', "po")};

    private static final KanaQuestion[] KANA_SET_6_BASE_DIGRAPHS = {
            new KanaQuestion("ひゃ", "hya"),
            new KanaQuestion("ひゅ", "hyu"),
            new KanaQuestion("ひょ", "hyo")};

    private static final KanaQuestion[] KANA_SET_6_DAKUTEN_DIGRAPHS = {
            new KanaQuestion("びゃ", "bya"),
            new KanaQuestion("びゅ", "byu"),
            new KanaQuestion("びょ", "byo")};

    private static final KanaQuestion[] KANA_SET_6_HANDAKETEN_DIGRAPHS = {
            new KanaQuestion("ぴゃ", "pya"),
            new KanaQuestion("ぴゅ", "pyu"),
            new KanaQuestion("ぴょ", "pyo")};

    private static final KanaQuestion[] KANA_SET_7 = {
            new KanaQuestion('ま', "ma"),
            new KanaQuestion('み', "mi"),
            new KanaQuestion('む', "mu"),
            new KanaQuestion('め', "me"),
            new KanaQuestion('も', "mo")};

    private static final KanaQuestion[] KANA_SET_7_DIGRAPHS = {
            new KanaQuestion("みゃ", "mya"),
            new KanaQuestion("みゅ", "myu"),
            new KanaQuestion("みょ", "myo")};

    private static final KanaQuestion[] KANA_SET_8 = {
            new KanaQuestion('ら', "ra"),
            new KanaQuestion('り', "ri"),
            new KanaQuestion('る', "ru"),
            new KanaQuestion('れ', "re"),
            new KanaQuestion('ろ', "ro")};

    private static final KanaQuestion[] KANA_SET_8_DIGRAPHS = {
            new KanaQuestion("りゃ", "rya"),
            new KanaQuestion("りゅ", "ryu"),
            new KanaQuestion("りょ", "ryo")};

    private static final KanaQuestion[] KANA_SET_9 = {
            new KanaQuestion('や', "ya"),
            new KanaQuestion('ゆ', "yu"),
            new KanaQuestion('よ', "yo")};

    private static final KanaQuestion[] KANA_SET_10_W_GROUP = {
            new KanaQuestion('わ', "wa"),
            new KanaQuestion('を', "wo")};

    private static final KanaQuestion[] KANA_SET_10_N_CONSONANT = {
            new KanaQuestion('ん', "n")};

    private static final int PREFID_1 = R.string.prefid_hiragana_1;
    private static final int PREFID_2 = R.string.prefid_hiragana_2;
    private static final int PREFID_3 = R.string.prefid_hiragana_3;
    private static final int PREFID_4 = R.string.prefid_hiragana_4;
    private static final int PREFID_5 = R.string.prefid_hiragana_5;
    private static final int PREFID_6 = R.string.prefid_hiragana_6;
    private static final int PREFID_7 = R.string.prefid_hiragana_7;
    private static final int PREFID_8 = R.string.prefid_hiragana_8;
    private static final int PREFID_9 = R.string.prefid_hiragana_9;
    private static final int PREFID_10 = R.string.prefid_hiragana_10;

    static final Hiragana QUESTIONS = new Hiragana(); //Must remain the last constant generated

    private Hiragana()
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
        addKanaSet(KANA_SET_6_HANDAKETEN, 6, HANDAKUTEN);
        addKanaSet(KANA_SET_6_BASE_DIGRAPHS, 6, NO_DIACRITIC, true);
        addKanaSet(KANA_SET_6_DAKUTEN_DIGRAPHS, 6, DAKUTEN, true);
        addKanaSet(KANA_SET_6_HANDAKETEN_DIGRAPHS, 6, HANDAKUTEN, true);

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
