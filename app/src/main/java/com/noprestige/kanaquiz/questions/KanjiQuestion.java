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
        if (meaning.trim().equalsIgnoreCase(response.trim()))
            return true;
        if (meaning.contains("/"))
            for (String subAnswer : meaning.split("/"))
                if (subAnswer.trim().equalsIgnoreCase(response.trim()))
                    return true;

        return false;
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
