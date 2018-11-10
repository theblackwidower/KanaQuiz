package com.noprestige.kanaquiz.questions;

public class KanjiQuestion extends Question
{
    private final String kanji;
    private final String meaning;

    KanjiQuestion(String kanji, String meaning)
    {
        this.kanji = kanji;
        this.meaning = meaning;
    }

    @Override
    String getQuestionText()
    {
        return kanji;
    }

    @Override
    boolean checkAnswer(String response)
    {
        return meaning.trim().equalsIgnoreCase(response.trim());
    }

    @Override
    String fetchCorrectAnswer()
    {
        return meaning;
    }

    @Override
    String getDatabaseKey()
    {
        return kanji;
    }
}
