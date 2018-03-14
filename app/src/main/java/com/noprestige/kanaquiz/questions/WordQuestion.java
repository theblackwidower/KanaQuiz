package com.noprestige.kanaquiz.questions;

public class WordQuestion extends KanaQuestion
{
    private String romanji;
    private String kana;
    private String kanji;
    private String answer;

    public WordQuestion(String romanji, String answer)
    {
        super(romanji, answer);
    }

    public void setKana(String kana)
    {
        this.kana = kana;
    }

    public void setKanji(String kanji)
    {
        this.kanji = kanji;
    }
}
