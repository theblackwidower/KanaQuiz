package com.noprestige.kanaquiz.questions;

import android.content.Context;
import android.util.TypedValue;

import com.noprestige.kanaquiz.reference.ReferenceCell;

import java.util.TreeMap;

public class KanaQuestion
{
    private String kana;
    private String defaultAnswer;
    private TreeMap<RomanizationSystem, String> altAnswers = new TreeMap<>();

    KanaQuestion(String kana, String answer)
    {
        setKana(kana);
        setDefaultAnswer(answer);
    }

    private void setDefaultAnswer(String answer)
    {
        this.defaultAnswer = answer;
    }

    public void addAltAnswer(String answer, RomanizationSystem system)
    {
        altAnswers.put(system, answer);
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
        if (defaultAnswer.trim().toLowerCase().equals(response.trim().toLowerCase()))
            return true;
        for (String correctAnswer : altAnswers.values())
            if (correctAnswer.trim().toLowerCase().equals(response.trim().toLowerCase()))
                return true;

        return false;
    }

    String fetchCorrectAnswer()
    {
        return defaultAnswer;
    }

    public ReferenceCell generateReference(Context context)
    {
        ReferenceCell cell = new ReferenceCell(context);
        cell.setKana(getKana());
        cell.setRomanji(fetchCorrectAnswer());
        if (kana.length() > 1)
            cell.setKanaSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 52, context.getResources().getDisplayMetrics()));
        return cell;
    }

    public String toString()
    {
        return kana + " = " + defaultAnswer;
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

    static public boolean isDiacritic(int charCode)
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

        else if (charCode >= 0x04 && charCode <= 0x09)
            return (charCode % 2 == 1); //last few T-set chars

        else if (charCode >= 0x0F && charCode <= 0x1D)
            return (charCode % 3 != 0); //H-set with two types of diacritics

        else
            return (charCode == 0x34 || charCode == 0x3E);
        //two special characters at the end of the block, which I'm counting.
    }
}
