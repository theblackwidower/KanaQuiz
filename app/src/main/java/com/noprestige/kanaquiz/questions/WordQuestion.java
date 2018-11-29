package com.noprestige.kanaquiz.questions;

import android.content.Context;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.reference.ReferenceCell;

public class WordQuestion extends Question
{
    private final String romaji;
    private final String kana;
    private final String kanji;
    private final String answer;
    private final String[] altAnswers;

    public WordQuestion(String romaji, String answer, String kana, String kanji, String[] altAnswers)
    {
        this.romaji = romaji.trim();
        this.answer = answer.trim();
        this.kana = (kana != null) ? kana.trim() : null;
        this.kanji = (kanji != null) ? kanji.trim() : null;
        this.altAnswers = altAnswers;
    }

    enum QuestionTextType
    {
        ROMAJI,
        KANA,
        KANJI
    }

    @Override
    String getQuestionText()
    {
        if (OptionsControl.compareStrings(R.string.prefid_vocab_display, R.string.prefid_vocab_display_romaji))
            return fetchText(QuestionTextType.ROMAJI);
        else if (OptionsControl.compareStrings(R.string.prefid_vocab_display, R.string.prefid_vocab_display_kana))
            return fetchText(QuestionTextType.KANA);
        else if (OptionsControl.compareStrings(R.string.prefid_vocab_display, R.string.prefid_vocab_display_kanji))
            return fetchText(QuestionTextType.KANJI);
        else
            return fetchText(QuestionTextType.KANA);
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
            case ROMAJI:
            default:
                return romaji;
        }
    }

    @Override
    boolean checkAnswer(String response)
    {
        if (answer.trim().equalsIgnoreCase(response.trim()))
            return true;
        else if (altAnswers != null)
            for (String answer : altAnswers)
                if (answer.trim().equalsIgnoreCase(response.trim()))
                    return true;
        return false;
    }

    @Override
    String fetchCorrectAnswer()
    {
        return answer;
    }

    @Override
    String getDatabaseKey()
    {
        return romaji;
    }

    @Override
    public ReferenceCell generateReference(Context context)
    {
        ReferenceCell cell = super.generateReference(context);
        cell.setSubjectSize(context.getResources().getDimension(R.dimen.vocabReferenceSubjectSize));
        return cell;
    }
}
