package com.noprestige.kanaquiz.questions;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;

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

    enum QuestionTextType
    {
        ROMANJI,
        KANA,
        KANJI
    }

    @Override
    public String getQuestionText()
    {
        if (OptionsControl.compareStrings(R.string.prefid_vocab_display, R.string.prefid_vocab_display_romanji))
            return fetchText(QuestionTextType.ROMANJI);
        else if (OptionsControl.compareStrings(R.string.prefid_vocab_display, R.string.prefid_vocab_display_kana))
            return fetchText(QuestionTextType.KANA);
        else if (OptionsControl.compareStrings(R.string.prefid_vocab_display, R.string.prefid_vocab_display_kanji))
            return fetchText(QuestionTextType.KANJI);
        else
            return romanji;
    }

    @SuppressWarnings("fallthrough")
    private String fetchText(QuestionTextType type)
    {
        switch (type)
        {
            case KANJI:
                if (kanji != null)
                    return kanji;
            case KANA:
                if (kana != null)
                    return kana;
            case ROMANJI:
            default:
                return romanji;
        }
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
