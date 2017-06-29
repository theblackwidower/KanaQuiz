package com.noprestige.kanaquiz;

class Katakana extends QuestionManagement
{
    static final Katakana QUESTIONS = new Katakana();

    private Katakana()
    {
        KANA_SET_1 = new KanaQuestion[] {
                new KanaQuestion('ア', "a"),
                new KanaQuestion('イ', "i"),
                new KanaQuestion('ウ', "u"),
                new KanaQuestion('エ', "e"),
                new KanaQuestion('オ', "o")};

        KANA_SET_2_BASE = new KanaQuestion[] {
                new KanaQuestion('カ', "ka"),
                new KanaQuestion('キ', "ki"),
                new KanaQuestion('ク', "ku"),
                new KanaQuestion('ケ', "ke"),
                new KanaQuestion('コ', "ko")};

        KANA_SET_2_DAKUTEN = new KanaQuestion[] {
                new KanaQuestion('ガ', "ga"),
                new KanaQuestion('ギ', "gi"),
                new KanaQuestion('グ', "gu"),
                new KanaQuestion('ゲ', "ge"),
                new KanaQuestion('ゴ', "go")};

        KANA_SET_2_BASE_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("キャ", "kya"),
                new KanaQuestion("キュ", "kyu"),
                new KanaQuestion("キョ", "kyo")};

        KANA_SET_2_DAKUTEN_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ギャ", "gya"),
                new KanaQuestion("ギュ", "gyu"),
                new KanaQuestion("ギョ", "gyo")};

        KANA_SET_3_BASE = new KanaQuestion[] {
                new KanaQuestion('サ', "sa"),
                new KanaQuestion('シ', "shi"),
                new KanaQuestion('ス', "su"),
                new KanaQuestion('セ', "se"),
                new KanaQuestion('ソ', "so")};

        KANA_SET_3_DAKUTEN = new KanaQuestion[] {
                new KanaQuestion('ザ', "za"),
                new KanaQuestion('ジ', "ji"),
                new KanaQuestion('ズ', "zu"),
                new KanaQuestion('ゼ', "ze"),
                new KanaQuestion('ゾ', "zo")};

        KANA_SET_3_BASE_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("シャ", "sha"),
                new KanaQuestion("シュ", "shu"),
                new KanaQuestion("ショ", "sho")};

        KANA_SET_3_DAKUTEN_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ジャ", new String[] {"ja", "jya"}),
                new KanaQuestion("ジュ", new String[] {"ju", "jyu"}),
                new KanaQuestion("ジョ", new String[] {"jo", "jyo"})};

        KANA_SET_4_BASE = new KanaQuestion[] {
                new KanaQuestion('タ', "ta"),
                new KanaQuestion('チ', "chi"),
                new KanaQuestion('ツ', "tsu"),
                new KanaQuestion('テ', "te"),
                new KanaQuestion('ト', "to")};

        KANA_SET_4_DAKUTEN = new KanaQuestion[] {
                new KanaQuestion('ダ', "da"),
                new KanaQuestion('ヂ', "ji"),
                new KanaQuestion('ヅ', "zu"),
                new KanaQuestion('デ', "de"),
                new KanaQuestion('ド', "do")};

        KANA_SET_4_BASE_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("チャ", "cha"),
                new KanaQuestion("チュ", "chu"),
                new KanaQuestion("チョ", "cho")};

        KANA_SET_4_DAKUTEN_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ヂャ", new String[] {"ja", "dzya"}),
                new KanaQuestion("ヂュ", new String[] {"ju", "dzyu"}),
                new KanaQuestion("ヂョ", new String[] {"jo", "dzyo"})};

        KANA_SET_5 = new KanaQuestion[] {
                new KanaQuestion('ナ', "na"),
                new KanaQuestion('ニ', "ni"),
                new KanaQuestion('ヌ', "nu"),
                new KanaQuestion('ネ', "ne"),
                new KanaQuestion('ノ', "no")};

        KANA_SET_5_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ニャ", "nya"),
                new KanaQuestion("ニュ", "nyu"),
                new KanaQuestion("ニョ", "nyo")};

        KANA_SET_6_BASE = new KanaQuestion[] {
                new KanaQuestion('ハ', "ha"),
                new KanaQuestion('ヒ', "hi"),
                new KanaQuestion('フ', new String[] {"fu", "hu"}),
                new KanaQuestion('ヘ', "he"),
                new KanaQuestion('ホ', "ho")};

        KANA_SET_6_DAKUTEN = new KanaQuestion[] {
                new KanaQuestion('バ', "ba"),
                new KanaQuestion('ビ', "bi"),
                new KanaQuestion('ブ', "bu"),
                new KanaQuestion('ベ', "be"),
                new KanaQuestion('ボ', "bo")};

        KANA_SET_6_HANDAKETEN = new KanaQuestion[] {
                new KanaQuestion('パ', "pa"),
                new KanaQuestion('ピ', "pi"),
                new KanaQuestion('プ', "pu"),
                new KanaQuestion('ペ', "pe"),
                new KanaQuestion('ポ', "po")};

        KANA_SET_6_BASE_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ヒャ", "hya"),
                new KanaQuestion("ヒュ", "hyu"),
                new KanaQuestion("ヒョ", "hyo")};

        KANA_SET_6_DAKUTEN_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ビャ", "bya"),
                new KanaQuestion("ビュ", "byu"),
                new KanaQuestion("ビョ", "byo")};

        KANA_SET_6_HANDAKETEN_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ピャ", "pya"),
                new KanaQuestion("ピュ", "pyu"),
                new KanaQuestion("ピョ", "pyo")};

        KANA_SET_7 = new KanaQuestion[] {
                new KanaQuestion('マ', "ma"),
                new KanaQuestion('ミ', "mi"),
                new KanaQuestion('ム', "mu"),
                new KanaQuestion('メ', "me"),
                new KanaQuestion('モ', "mo")};

        KANA_SET_7_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("ミャ", "mya"),
                new KanaQuestion("ミュ", "myu"),
                new KanaQuestion("ミョ", "myo")};

        KANA_SET_8 = new KanaQuestion[] {
                new KanaQuestion('ラ', "ra"),
                new KanaQuestion('リ', "ri"),
                new KanaQuestion('ル', "ru"),
                new KanaQuestion('レ', "re"),
                new KanaQuestion('ロ', "ro")};

        KANA_SET_8_DIGRAPHS = new KanaQuestion[] {
                new KanaQuestion("リャ", "rya"),
                new KanaQuestion("リュ", "ryu"),
                new KanaQuestion("リョ", "ryo")};

        KANA_SET_9 = new KanaQuestion[] {
                new KanaQuestion('ヤ', "ya"),
                new KanaQuestion('ユ', "yu"),
                new KanaQuestion('ヨ', "yo")};

        KANA_SET_10_W_GROUP = new KanaQuestion[] {
                new KanaQuestion('ワ', "wa"),
                new KanaQuestion('ヲ', "wo")};

        KANA_SET_10_N_CONSONANT = new KanaQuestion[] {
                new KanaQuestion('ン', "n")};

        PREFID_1 = R.string.prefid_katakana_1;
        PREFID_2 = R.string.prefid_katakana_2;
        PREFID_3 = R.string.prefid_katakana_3;
        PREFID_4 = R.string.prefid_katakana_4;
        PREFID_5 = R.string.prefid_katakana_5;
        PREFID_6 = R.string.prefid_katakana_6;
        PREFID_7 = R.string.prefid_katakana_7;
        PREFID_8 = R.string.prefid_katakana_8;
        PREFID_9 = R.string.prefid_katakana_9;
        PREFID_10 = R.string.prefid_katakana_10;
    }
}
