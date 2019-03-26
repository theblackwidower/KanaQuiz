package com.noprestige.kanaquiz.questions;

import android.content.Context;

import com.noprestige.kanaquiz.R;
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
        cell.setSubjectSize(context.getResources().getDimension(R.dimen.kanjiReferenceSubjectSize));
        cell.setDescriptionSize(context.getResources().getDimension(R.dimen.kanjiReferenceDescriptionSize));
        return cell;
    }
}
