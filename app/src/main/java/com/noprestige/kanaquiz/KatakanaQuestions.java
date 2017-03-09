package com.noprestige.kanaquiz;

import android.content.SharedPreferences;

abstract class KatakanaQuestions
{
    private static final KanaQuestion[] KANA_SET_1 = {
            new KanaQuestion('ア', "a"),
            new KanaQuestion('イ', "i"),
            new KanaQuestion('ウ', "u"),
            new KanaQuestion('エ', "e"),
            new KanaQuestion('オ', "o")};

    private static final KanaQuestion[] KANA_SET_2 = {
            new KanaQuestion('カ', "ka"),
            new KanaQuestion('キ', "ki"),
            new KanaQuestion('ク', "ku"),
            new KanaQuestion('ケ', "ke"),
            new KanaQuestion('コ', "ko"),
            new KanaQuestion('ガ', "ga"),
            new KanaQuestion('ギ', "gi"),
            new KanaQuestion('グ', "gu"),
            new KanaQuestion('ゲ', "ge"),
            new KanaQuestion('ゴ', "go")};

    private static final KanaQuestion[] KANA_SET_3 = {
            new KanaQuestion('サ', "sa"),
            new KanaQuestion('シ', "shi"),
            new KanaQuestion('ス', "su"),
            new KanaQuestion('セ', "se"),
            new KanaQuestion('ソ', "so"),
            new KanaQuestion('ザ', "za"),
            new KanaQuestion('ジ', "ji"),
            new KanaQuestion('ズ', "zu"),
            new KanaQuestion('ゼ', "ze"),
            new KanaQuestion('ゾ', "zo")};

    private static final KanaQuestion[] KANA_SET_4 = {
            new KanaQuestion('タ', "ta"),
            new KanaQuestion('チ', "chi"),
            new KanaQuestion('ツ', "tsu"),
            new KanaQuestion('テ', "te"),
            new KanaQuestion('ト', "to"),
            new KanaQuestion('ダ', "da"),
            new KanaQuestion('ヂ', "ji"),
            new KanaQuestion('ヅ', "zu"),
            new KanaQuestion('デ', "de"),
            new KanaQuestion('ド', "do")};

    private static final KanaQuestion[] KANA_SET_5 = {
            new KanaQuestion('ナ', "na"),
            new KanaQuestion('ニ', "ni"),
            new KanaQuestion('ヌ', "nu"),
            new KanaQuestion('ネ', "ne"),
            new KanaQuestion('ノ', "no")};

    private static final KanaQuestion[] KANA_SET_6 = {
            new KanaQuestion('ハ', "ha"),
            new KanaQuestion('ヒ', "hi"),
            new KanaQuestion('フ', "fu"),
            new KanaQuestion('ヘ', "he"),
            new KanaQuestion('ホ', "ho"),
            new KanaQuestion('バ', "ba"),
            new KanaQuestion('ビ', "bi"),
            new KanaQuestion('ブ', "bu"),
            new KanaQuestion('ベ', "be"),
            new KanaQuestion('ボ', "bo"),
            new KanaQuestion('パ', "pa"),
            new KanaQuestion('ピ', "pi"),
            new KanaQuestion('プ', "pu"),
            new KanaQuestion('ペ', "pe"),
            new KanaQuestion('ポ', "po")};

    private static final KanaQuestion[] KANA_SET_7 = {
            new KanaQuestion('マ', "ma"),
            new KanaQuestion('ミ', "mi"),
            new KanaQuestion('ム', "mu"),
            new KanaQuestion('メ', "me"),
            new KanaQuestion('モ', "mo")};

    private static final KanaQuestion[] KANA_SET_8 = {
            new KanaQuestion('ラ', "ra"),
            new KanaQuestion('リ', "ri"),
            new KanaQuestion('ル', "ru"),
            new KanaQuestion('レ', "re"),
            new KanaQuestion('ロ', "ro")};

    private static final KanaQuestion[] KANA_SET_9 = {
            new KanaQuestion('ヤ', "ya"),
            new KanaQuestion('ユ', "yu"),
            new KanaQuestion('ヨ', "yo")};

    private static final KanaQuestion[] KANA_SET_10 = {
            new KanaQuestion('ワ', "wa"),
            new KanaQuestion('ヲ', "wo"),
            new KanaQuestion('ン', "n")};

    static KanaQuestionBank getQuestionBank(SharedPreferences sharedPref)
    {
        KanaQuestionBank questionBank = new KanaQuestionBank();

        if (sharedPref.getBoolean("katakana_set_1", false))
            questionBank.addQuestions(KANA_SET_1);
        if (sharedPref.getBoolean("katakana_set_2", false))
            questionBank.addQuestions(KANA_SET_2);
        if (sharedPref.getBoolean("katakana_set_3", false))
            questionBank.addQuestions(KANA_SET_3);
        if (sharedPref.getBoolean("katakana_set_4", false))
            questionBank.addQuestions(KANA_SET_4);
        if (sharedPref.getBoolean("katakana_set_5", false))
            questionBank.addQuestions(KANA_SET_5);
        if (sharedPref.getBoolean("katakana_set_6", false))
            questionBank.addQuestions(KANA_SET_6);
        if (sharedPref.getBoolean("katakana_set_7", false))
            questionBank.addQuestions(KANA_SET_7);
        if (sharedPref.getBoolean("katakana_set_8", false))
            questionBank.addQuestions(KANA_SET_8);
        if (sharedPref.getBoolean("katakana_set_9", false))
            questionBank.addQuestions(KANA_SET_9);
        if (sharedPref.getBoolean("katakana_set_10", false))
            questionBank.addQuestions(KANA_SET_10);

        return questionBank;
    }
}
