package com.noprestige.kanaquiz.questions;

import android.content.Context;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.reference.ReferenceCell;

import java.util.Map;

public class KanaQuestion extends Question
{
    private final String kana;
    private final String defaultRomaji;
    private final Map<RomanizationSystem, String> altRomaji;

    KanaQuestion(String kana, String romaji)
    {
        this(kana, romaji, null);
    }

    KanaQuestion(String kana, String romaji, Map<RomanizationSystem, String> altRomaji)
    {
        this.kana = kana.trim();
        defaultRomaji = romaji.trim();
        this.altRomaji = altRomaji;
    }

    String getQuestionText()
    {
        return kana;
    }

    boolean checkAnswer(String response)
    {
        if (defaultRomaji.trim().equalsIgnoreCase(response.trim()))
            return true;
        if (altRomaji != null)
            for (String correctAnswer : altRomaji.values())
                if (correctAnswer.trim().equalsIgnoreCase(response.trim()))
                    return true;

        return false;
    }

    String fetchCorrectAnswer()
    {
        return fetchRomaji();
    }

    enum RomanizationSystem
    {
        HEPBURN,
        NIHON,
        KUNREI,
        UNKNOWN
    }

    String fetchRomaji()
    {
        if (OptionsControl.compareStrings(R.string.prefid_romanize_system, R.string.prefid_romanize_system_default))
            return defaultRomaji;
        else if (OptionsControl
                .compareStrings(R.string.prefid_romanize_system, R.string.prefid_romanize_system_hepburn))
            return fetchRomaji(RomanizationSystem.HEPBURN);
        else if (OptionsControl.compareStrings(R.string.prefid_romanize_system, R.string.prefid_romanize_system_nihon))
            return fetchRomaji(RomanizationSystem.NIHON);
        else if (OptionsControl.compareStrings(R.string.prefid_romanize_system, R.string.prefid_romanize_system_kunrei))
            return fetchRomaji(RomanizationSystem.KUNREI);
        else
            return defaultRomaji;
    }

    String fetchRomaji(RomanizationSystem system)
    {
        String romaji = (((system == null) || (altRomaji == null)) ? null : altRomaji.get(system));
        return (romaji == null) ? defaultRomaji : romaji;
    }

    @Override
    String getDatabaseKey()
    {
        return kana;
    }

    @Override
    public ReferenceCell generateReference(Context context)
    {
        ReferenceCell cell = super.generateReference(context);
        if (isDigraph())
            cell.setSubjectSize(context.getResources().getDimension(R.dimen.diacriticReferenceSubjectSize));
        return cell;
    }

    public boolean isDigraph()
    {
        return kana.length() > 1;
    }

    public boolean isDiacritic()
    {
        for (int i = 0; i < kana.length(); i++)
            if (isDiacritic(Character.codePointAt(kana, i)))
                return true;
        return false;
    }
}
