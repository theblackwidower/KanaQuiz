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
}
