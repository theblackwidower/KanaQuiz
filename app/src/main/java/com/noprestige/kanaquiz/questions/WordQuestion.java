package com.noprestige.kanaquiz.questions;

public class WordQuestion extends Question
{
    private final String romanji;
    private final String kana;
    private final String kanji;
    private final String answer;

    public WordQuestion(String romanji, String answer, String kana, String kanji)
    {
        this.romanji = romanji.trim();
        this.answer = answer.trim();
        this.kana = (kana != null) ? kana.trim() : null;
        this.kanji = (kanji != null) ? kanji.trim() : null;
    }

    @Override
    public String getQuestionText()
    {
        return romanji;
    }

    @Override
    boolean checkAnswer(String response)
    {
        return answer.trim().equalsIgnoreCase(response.trim());
    }

    @Override
    String fetchCorrectAnswer()
    {
        return answer;
    }

    @Override
    public String toString()
    {
        return romanji;
    }
}
