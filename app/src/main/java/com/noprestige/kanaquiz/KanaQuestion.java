package com.noprestige.kanaquiz;

import android.content.Context;
import android.util.TypedValue;

class KanaQuestion
{
    private String kana;
    private String[] romanji;

    KanaQuestion(char kana, String romanji)
    {
        setKana(kana);
        setRomanji(romanji);
    }

    KanaQuestion(String kana, String romanji)
    {
        setKana(kana);
        setRomanji(romanji);
    }

    KanaQuestion(char kana, String[] romanji)
    {
        setKana(kana);
        setRomanji(romanji);
    }

    KanaQuestion(String kana, String[] romanji)
    {
        setKana(kana);
        setRomanji(romanji);
    }

    private void setRomanji(String romanji)
    {
        this.romanji = new String[]{romanji};
    }

    private void setRomanji(String[] romanji)
    {
        this.romanji = romanji;
    }

    private void setKana(char kana)
    {
        setKana(Character.toString(kana));
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
        {
            if (correctAnswer.trim().toLowerCase().equals(response.trim().toLowerCase()))
                return true;
        }
        return false;
    }

    String fetchCorrectAnswer()
    {
        return romanji[0];
    }

    ReferenceCell generateReference(Context context)
    {
        ReferenceCell cell = new ReferenceCell(context);
        cell.setKana(getKana());
        cell.setRomanji(fetchCorrectAnswer());
        if (kana.length() > 1)
            cell.setKanaSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 52, context.getResources().getDisplayMetrics()));
        return cell;
    }
}
