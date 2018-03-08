package com.noprestige.kanaquiz.questions;

import android.content.Context;
import android.util.TypedValue;

import com.noprestige.kanaquiz.reference.ReferenceCell;

public class KanaQuestion
{
    private String kana;
    private String[] answer;

    KanaQuestion(String kana, String answer)
    {
        setKana(kana);
        setAnswer(answer);
    }

    KanaQuestion(String kana, String[] answer)
    {
        setKana(kana);
        setAnswer(answer);
    }

    KanaQuestion(String kana, String answer, String[] altAnswer)
    {
        setKana(kana);
        setAnswer(answer, altAnswer);
    }

    private void setAnswer(String answer)
    {
        this.answer = new String[]{answer};
    }

    private void setAnswer(String[] answer)
    {
        this.answer = answer;
    }

    private void setAnswer(String answer, String[] altAnswer)
    {
        this.answer = new String[altAnswer.length + 1];
        this.answer[0] = answer;
//        for (int i = 0; i < altAnswer.length; i++)
//            this.answer[i + 1] = altAnswer[i];
        System.arraycopy(altAnswer, 0, this.answer, 1, altAnswer.length);
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
        for (String correctAnswer : answer)
            if (correctAnswer.trim().toLowerCase().equals(response.trim().toLowerCase()))
                return true;

        return false;
    }

    String fetchCorrectAnswer()
    {
        return answer[0];
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
        return kana + " = " + answer[0];
    }

    public boolean isDigraph()
    {
        return kana.length() > 1;
    }

    public boolean isDiacritic()
    {
        int charCode = Character.codePointAt(kana, 0);

        if (charCode < 0x3041 || charCode > 0x30FF)
            return false; //not kana anyway
        else if (charCode >= 0x30F7 && charCode <= 0x30FA)
            return true; //special katakana diacritics

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
