package com.noprestige.kanaquiz.questions;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;

import java.util.Map;

public class KanaQuestion extends Question
{
    private final String kana;
    private final String defaultRomanji;
    private final Map<RomanizationSystem, String> altRomanji;

    KanaQuestion(String kana, String romanji)
    {
        this(kana, romanji, null);
    }

    KanaQuestion(String kana, String romanji, Map<RomanizationSystem, String> altRomanji)
    {
        this.kana = kana.trim();
        defaultRomanji = romanji.trim();
        this.altRomanji = altRomanji;
    }

    String getQuestionText()
    {
        return kana;
    }

    boolean checkAnswer(String response)
    {
        if (defaultRomanji.trim().equalsIgnoreCase(response.trim()))
            return true;
        if (altRomanji != null)
            for (String correctAnswer : altRomanji.values())
                if (correctAnswer.trim().equalsIgnoreCase(response.trim()))
                    return true;

        return false;
    }

    String fetchCorrectAnswer()
    {
        return fetchRomanji();
    }

    String fetchRomanji()
    {
        if (OptionsControl.compareStrings(R.string.prefid_romanize_system, R.string.prefid_romanize_system_default))
            return defaultRomanji;
        else if (OptionsControl
                .compareStrings(R.string.prefid_romanize_system, R.string.prefid_romanize_system_hepburn))
            return fetchRomanji(RomanizationSystem.HEPBURN);
        else if (OptionsControl.compareStrings(R.string.prefid_romanize_system, R.string.prefid_romanize_system_nihon))
            return fetchRomanji(RomanizationSystem.NIHON);
        else if (OptionsControl.compareStrings(R.string.prefid_romanize_system, R.string.prefid_romanize_system_kunrei))
            return fetchRomanji(RomanizationSystem.KUNREI);
        else
            return defaultRomanji;
    }

    String fetchRomanji(RomanizationSystem system)
    {
        String romanji = (((system == null) || (altRomanji == null)) ? null : altRomanji.get(system));
        return (romanji == null) ? defaultRomanji : romanji;
    }

    @Override
    public String toString()
    {
        return kana;
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
