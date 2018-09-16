package com.noprestige.kanaquiz.questions;

import android.content.Context;
import android.util.TypedValue;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.reference.ReferenceCell;

import java.util.TreeMap;

public class KanaQuestion
{
    private String kana;
    private String defaultRomanji;
    private TreeMap<RomanizationSystem, String> altRomanji = new TreeMap<>();

    KanaQuestion(String kana, String romanji)
    {
        setKana(kana);
        setDefaultRomanji(romanji);
    }

    private void setDefaultRomanji(String romanji)
    {
        this.defaultRomanji = romanji;
    }

    public void addAltRomanji(String romanji, RomanizationSystem system)
    {
        altRomanji.put(system, romanji);
    }

    private void setKana(String kana)
    {
        this.kana = kana.trim();
    }

    String getKana()
    {
        return kana;
    }

    boolean checkAnswer(String response)
    {
        if (defaultRomanji.trim().toLowerCase().equals(response.trim().toLowerCase()))
            return true;
        for (String correctAnswer : altRomanji.values())
            if (correctAnswer.trim().toLowerCase().equals(response.trim().toLowerCase()))
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
        String romanji = altRomanji.get(system);
        return romanji == null ? defaultRomanji : romanji;
    }

    public ReferenceCell generateReference(Context context)
    {
        ReferenceCell cell = new ReferenceCell(context);
        cell.setKana(getKana());
        cell.setRomanji(fetchCorrectAnswer());
        if (kana.length() > 1)
            cell.setKanaSize(TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_SP, 52, context.getResources().getDisplayMetrics()));
        return cell;
    }

    @Override
    public String toString()
    {
        return kana + " = " + defaultRomanji;
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

    public static boolean isDiacritic(int charCode)
    {
        if (charCode < 0x3041 || charCode > 0x30FF)
            return false; //not kana anyway
        else if ((charCode >= 0x30F7 && charCode <= 0x30FA) || //special katakana diacritics
                (charCode >= 0x3099 && charCode <= 0x309C)) //single diacritic characters
            return true;

        charCode %= 0x60; //can now run the calculations once for both hiragana and katakana

        if ((charCode >= 0x40 && charCode <= 0x4B) || //early vowels
                (charCode >= 0x0A && charCode <= 0x0E) || //N-set
                (charCode >= 0x1E && charCode <= 0x33)) //M, Y, R, W-sets and N
            return false;

        else if (charCode >= 0x4C || charCode <= 0x03)
            return (charCode % 2 == 0); //K and S-sets, and first few T-set chars

        else if (charCode <= 0x09) // && charCode >= 0x04
            return (charCode % 2 == 1); //last few T-set chars

        else if (charCode <= 0x1D) // && charCode >= 0x0F
            return (charCode % 3 != 0); //H-set with two types of diacritics

        else
            return (charCode == 0x34 || charCode == 0x3E);
        //two special characters at the end of the block, which I'm counting.
    }
}
