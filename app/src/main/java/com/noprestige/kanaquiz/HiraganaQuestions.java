package com.noprestige.kanaquiz;

import android.content.SharedPreferences;

abstract class HiraganaQuestions
{
    private static final KanaQuestion[] KANA_SET_1 = {
            new KanaQuestion('あ', "a"),
            new KanaQuestion('い', "i"),
            new KanaQuestion('う', "u"),
            new KanaQuestion('え', "e"),
            new KanaQuestion('お', "o")};

    private static final KanaQuestion[] KANA_SET_2 = {
            new KanaQuestion('か', "ka"),
            new KanaQuestion('き', "ki"),
            new KanaQuestion('く', "ku"),
            new KanaQuestion('け', "ke"),
            new KanaQuestion('こ', "ko"),
            new KanaQuestion('が', "ga"),
            new KanaQuestion('ぎ', "gi"),
            new KanaQuestion('ぐ', "gu"),
            new KanaQuestion('げ', "ge"),
            new KanaQuestion('ご', "go")};

    private static final KanaQuestion[] KANA_SET_3 = {
            new KanaQuestion('さ', "sa"),
            new KanaQuestion('し', "shi"),
            new KanaQuestion('す', "su"),
            new KanaQuestion('せ', "se"),
            new KanaQuestion('そ', "so"),
            new KanaQuestion('ざ', "za"),
            new KanaQuestion('じ', "ji"),
            new KanaQuestion('ず', "zu"),
            new KanaQuestion('ぜ', "ze"),
            new KanaQuestion('ぞ', "zo")};

    private static final KanaQuestion[] KANA_SET_4 = {
            new KanaQuestion('た', "ta"),
            new KanaQuestion('ち', "chi"),
            new KanaQuestion('つ', "tsu"),
            new KanaQuestion('て', "te"),
            new KanaQuestion('と', "to"),
            new KanaQuestion('だ', "da"),
            new KanaQuestion('ぢ', "ji"),
            new KanaQuestion('づ', "zu"),
            new KanaQuestion('で', "de"),
            new KanaQuestion('ど', "do")};

    private static final KanaQuestion[] KANA_SET_5 = {
            new KanaQuestion('な', "na"),
            new KanaQuestion('に', "ni"),
            new KanaQuestion('ぬ', "nu"),
            new KanaQuestion('ね', "ne"),
            new KanaQuestion('の', "no")};

    private static final KanaQuestion[] KANA_SET_6 = {
            new KanaQuestion('は', "ha"),
            new KanaQuestion('ひ', "hi"),
            new KanaQuestion('ふ', "fu"),
            new KanaQuestion('へ', "he"),
            new KanaQuestion('ほ', "ho"),
            new KanaQuestion('ば', "ba"),
            new KanaQuestion('び', "bi"),
            new KanaQuestion('ぶ', "bu"),
            new KanaQuestion('べ', "be"),
            new KanaQuestion('ぼ', "bo"),
            new KanaQuestion('ぱ', "pa"),
            new KanaQuestion('ぴ', "pi"),
            new KanaQuestion('ぷ', "pu"),
            new KanaQuestion('ぺ', "pe"),
            new KanaQuestion('ぽ', "po")};

    private static final KanaQuestion[] KANA_SET_7 = {
            new KanaQuestion('ま', "ma"),
            new KanaQuestion('み', "mi"),
            new KanaQuestion('む', "mu"),
            new KanaQuestion('め', "me"),
            new KanaQuestion('も', "mo")};

    private static final KanaQuestion[] KANA_SET_8 = {
            new KanaQuestion('ら', "ra"),
            new KanaQuestion('り', "ri"),
            new KanaQuestion('る', "ru"),
            new KanaQuestion('れ', "re"),
            new KanaQuestion('ろ', "ro")};

    private static final KanaQuestion[] KANA_SET_9 = {
            new KanaQuestion('や', "ya"),
            new KanaQuestion('ゆ', "yu"),
            new KanaQuestion('よ', "yo"),
            new KanaQuestion('わ', "wa"),
            new KanaQuestion('を', "wo"),
            new KanaQuestion('ん', "n")};

    static KanaQuestionBank getQuestionBank(SharedPreferences sharedPref)
    {
        KanaQuestionBank questionBank = new KanaQuestionBank();

        if (sharedPref.getBoolean("hiragana_set_1", false))
            questionBank.addQuestions(KANA_SET_1);
        if (sharedPref.getBoolean("hiragana_set_2", false))
            questionBank.addQuestions(KANA_SET_2);
        if (sharedPref.getBoolean("hiragana_set_3", false))
            questionBank.addQuestions(KANA_SET_3);
        if (sharedPref.getBoolean("hiragana_set_4", false))
            questionBank.addQuestions(KANA_SET_4);
        if (sharedPref.getBoolean("hiragana_set_5", false))
            questionBank.addQuestions(KANA_SET_5);
        if (sharedPref.getBoolean("hiragana_set_6", false))
            questionBank.addQuestions(KANA_SET_6);
        if (sharedPref.getBoolean("hiragana_set_7", false))
            questionBank.addQuestions(KANA_SET_7);
        if (sharedPref.getBoolean("hiragana_set_8", false))
            questionBank.addQuestions(KANA_SET_8);
        if (sharedPref.getBoolean("hiragana_set_9", false))
            questionBank.addQuestions(KANA_SET_9);

        return questionBank;
    }
}
