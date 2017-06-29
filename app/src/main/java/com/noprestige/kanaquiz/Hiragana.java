package com.noprestige.kanaquiz;

class Hiragana extends QuestionManagement
{
    static final Hiragana QUESTIONS = new Hiragana();

    private Hiragana()
    {
        KANA_SET_1 = new KanaQuestion[] {
                new KanaQuestion('あ', "a"),
                new KanaQuestion('い', "i"),
                new KanaQuestion('う', "u"),
                new KanaQuestion('え', "e"),
                new KanaQuestion('お', "o")};

        KANA_SET_2_BASE = new KanaQuestion[] {
                new KanaQuestion('か', "ka"),
                new KanaQuestion('き', "ki"),
                new KanaQuestion('く', "ku"),
                new KanaQuestion('け', "ke"),
                new KanaQuestion('こ', "ko")};

        KANA_SET_2_DAKUTEN = new KanaQuestion[] {
                new KanaQuestion('が', "ga"),
                new KanaQuestion('ぎ', "gi"),
                new KanaQuestion('ぐ', "gu"),
                new KanaQuestion('げ', "ge"),
                new KanaQuestion('ご', "go")};

        KANA_SET_2_BASE_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("きゃ", "kya"),
                new KanaQuestion("きゅ", "kyu"),
                new KanaQuestion("きょ", "kyo")};

        KANA_SET_2_DAKUTEN_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ぎゃ", "gya"),
                new KanaQuestion("ぎゅ", "gyu"),
                new KanaQuestion("ぎょ", "gyo")};

        KANA_SET_3_BASE = new KanaQuestion[] {
                new KanaQuestion('さ', "sa"),
                new KanaQuestion('し', "shi"),
                new KanaQuestion('す', "su"),
                new KanaQuestion('せ', "se"),
                new KanaQuestion('そ', "so")};

        KANA_SET_3_DAKUTEN = new KanaQuestion[] {
                new KanaQuestion('ざ', "za"),
                new KanaQuestion('じ', "ji"),
                new KanaQuestion('ず', "zu"),
                new KanaQuestion('ぜ', "ze"),
                new KanaQuestion('ぞ', "zo")};

        KANA_SET_3_BASE_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("しゃ", "sha"),
                new KanaQuestion("しゅ", "shu"),
                new KanaQuestion("しょ", "sho")};

        KANA_SET_3_DAKUTEN_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("じゃ", new String[] {"ja", "jya"}),
                new KanaQuestion("じゅ", new String[] {"ju", "jyu"}),
                new KanaQuestion("じょ", new String[] {"jo", "jyo"})};

        KANA_SET_4_BASE = new KanaQuestion[] {
                new KanaQuestion('た', "ta"),
                new KanaQuestion('ち', "chi"),
                new KanaQuestion('つ', "tsu"),
                new KanaQuestion('て', "te"),
                new KanaQuestion('と', "to")};

        KANA_SET_4_DAKUTEN = new KanaQuestion[] {
                new KanaQuestion('だ', "da"),
                new KanaQuestion('ぢ', "ji"),
                new KanaQuestion('づ', "zu"),
                new KanaQuestion('で', "de"),
                new KanaQuestion('ど', "do")};

        KANA_SET_4_BASE_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ちゃ", "cha"),
                new KanaQuestion("ちゅ", "chu"),
                new KanaQuestion("ちょ", "cho")};

        KANA_SET_4_DAKUTEN_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ぢゃ", new String[] {"ja", "dzya"}),
                new KanaQuestion("ぢゅ", new String[] {"ju", "dzyu"}),
                new KanaQuestion("ぢょ", new String[] {"jo", "dzyo"})};

        KANA_SET_5 = new KanaQuestion[] {
                new KanaQuestion('な', "na"),
                new KanaQuestion('に', "ni"),
                new KanaQuestion('ぬ', "nu"),
                new KanaQuestion('ね', "ne"),
                new KanaQuestion('の', "no")};

        KANA_SET_5_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("にゃ", "nya"),
                new KanaQuestion("にゅ", "nyu"),
                new KanaQuestion("にょ", "nyo")};

        KANA_SET_6_BASE = new KanaQuestion[] {
                new KanaQuestion('は', "ha"),
                new KanaQuestion('ひ', "hi"),
                new KanaQuestion('ふ', new String[] {"fu", "hu"}),
                new KanaQuestion('へ', "he"),
                new KanaQuestion('ほ', "ho")};

        KANA_SET_6_DAKUTEN = new KanaQuestion[] {
                new KanaQuestion('ば', "ba"),
                new KanaQuestion('び', "bi"),
                new KanaQuestion('ぶ', "bu"),
                new KanaQuestion('べ', "be"),
                new KanaQuestion('ぼ', "bo")};

        KANA_SET_6_HANDAKETEN = new KanaQuestion[] {
                new KanaQuestion('ぱ', "pa"),
                new KanaQuestion('ぴ', "pi"),
                new KanaQuestion('ぷ', "pu"),
                new KanaQuestion('ぺ', "pe"),
                new KanaQuestion('ぽ', "po")};

        KANA_SET_6_BASE_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ひゃ", "hya"),
                new KanaQuestion("ひゅ", "hyu"),
                new KanaQuestion("ひょ", "hyo")};

        KANA_SET_6_DAKUTEN_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("びゃ", "bya"),
                new KanaQuestion("びゅ", "byu"),
                new KanaQuestion("びょ", "byo")};

        KANA_SET_6_HANDAKETEN_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ぴゃ", "pya"),
                new KanaQuestion("ぴゅ", "pyu"),
                new KanaQuestion("ぴょ", "pyo")};

        KANA_SET_7 = new KanaQuestion[] {
                new KanaQuestion('ま', "ma"),
                new KanaQuestion('み', "mi"),
                new KanaQuestion('む', "mu"),
                new KanaQuestion('め', "me"),
                new KanaQuestion('も', "mo")};

        KANA_SET_7_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("みゃ", "mya"),
                new KanaQuestion("みゅ", "myu"),
                new KanaQuestion("みょ", "myo")};

        KANA_SET_8 = new KanaQuestion[] {
                new KanaQuestion('ら', "ra"),
                new KanaQuestion('り', "ri"),
                new KanaQuestion('る', "ru"),
                new KanaQuestion('れ', "re"),
                new KanaQuestion('ろ', "ro")};

        KANA_SET_8_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("りゃ", "rya"),
                new KanaQuestion("りゅ", "ryu"),
                new KanaQuestion("りょ", "ryo")};

        KANA_SET_9 = new KanaQuestion[] {
                new KanaQuestion('や', "ya"),
                new KanaQuestion('ゆ', "yu"),
                new KanaQuestion('よ', "yo")};

        KANA_SET_10_W_GROUP = new KanaQuestion[] {
                new KanaQuestion('わ', "wa"),
                new KanaQuestion('を', "wo")};

        KANA_SET_10_N_CONSONANT = new KanaQuestion[] {
                new KanaQuestion('ん', "n")};

        PREFID_1 = R.string.prefid_hiragana_1;
        PREFID_2 = R.string.prefid_hiragana_2;
        PREFID_3 = R.string.prefid_hiragana_3;
        PREFID_4 = R.string.prefid_hiragana_4;
        PREFID_5 = R.string.prefid_hiragana_5;
        PREFID_6 = R.string.prefid_hiragana_6;
        PREFID_7 = R.string.prefid_hiragana_7;
        PREFID_8 = R.string.prefid_hiragana_8;
        PREFID_9 = R.string.prefid_hiragana_9;
        PREFID_10 = R.string.prefid_hiragana_10;
    }
}
