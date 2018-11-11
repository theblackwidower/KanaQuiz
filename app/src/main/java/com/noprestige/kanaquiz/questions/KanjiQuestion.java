package com.noprestige.kanaquiz.questions;

import android.content.Context;
import android.util.TypedValue;

import com.noprestige.kanaquiz.reference.ReferenceCell;

public class KanjiQuestion extends Question
{
    public static final String MEANING_DELIMITER = "/";

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
        if (meaning.contains(MEANING_DELIMITER))
            for (String subAnswer : meaning.split(MEANING_DELIMITER))
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

    @Override
    public ReferenceCell generateReference(Context context)
    {
        ReferenceCell cell = super.generateReference(context);
        cell.setSubjectSize(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 48, context.getResources().getDisplayMetrics()));
        cell.setDescriptionSize(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, context.getResources().getDisplayMetrics()));
        return cell;
    }
}
