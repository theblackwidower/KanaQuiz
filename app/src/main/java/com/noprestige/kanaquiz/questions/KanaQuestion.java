package com.noprestige.kanaquiz.questions;

import android.content.Context;
import android.util.TypedValue;

import com.noprestige.kanaquiz.reference.ReferenceCell;

public class KanaQuestion
{
    private String kana;
    private String[] romanji;

    KanaQuestion(String kana, String romanji)
    {
        setKana(kana);
        setRomanji(romanji);
    }

    KanaQuestion(String kana, String[] romanji)
    {
        setKana(kana);
        setRomanji(romanji);
    }

    KanaQuestion(String kana, String romanji, String[] altRomanji)
    {
        setKana(kana);
        setRomanji(romanji, altRomanji);
    }

    private void setRomanji(String romanji)
    {
        this.romanji = new String[]{romanji};
    }

    private void setRomanji(String[] romanji)
    {
        this.romanji = romanji;
    }

    private void setRomanji(String romanji, String[] altRomanji)
    {
        this.romanji = new String[altRomanji.length + 1];
        this.romanji[0] = romanji;
//        for (int i = 0; i < altRomanji.length; i++)
//            this.romanji[i + 1] = altRomanji[i];
        System.arraycopy(altRomanji, 0, this.romanji, 1, altRomanji.length);
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
        for (String correctAnswer : romanji)
            if (correctAnswer.trim().toLowerCase().equals(response.trim().toLowerCase()))
                return true;

        return false;
    }

    String fetchCorrectAnswer()
    {
        return romanji[0];
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
        return kana + " = " + romanji[0];
    }
}
