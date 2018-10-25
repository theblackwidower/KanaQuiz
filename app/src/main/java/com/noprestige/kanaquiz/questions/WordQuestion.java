package com.noprestige.kanaquiz.questions;

import android.content.Context;
import android.util.TypedValue;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.reference.ReferenceCell;

public class WordQuestion extends Question
{
    private final String romaji;
    private final String kana;
    private final String kanji;
    private final String answer;

    public WordQuestion(String romaji, String answer, String kana, String kanji)
    {
        this.romaji = romaji.trim();
        this.answer = answer.trim();
        this.kana = (kana != null) ? kana.trim() : null;
        this.kanji = (kanji != null) ? kanji.trim() : null;
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
            return romaji;
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
        return answer.trim().equalsIgnoreCase(response.trim());
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
        cell.setSubjectSize(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 52, context.getResources().getDisplayMetrics()));
        return cell;
    }
}
