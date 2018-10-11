package com.noprestige.kanaquiz.questions;

import android.content.Context;
import android.util.TypedValue;

import com.noprestige.kanaquiz.reference.ReferenceCell;

public abstract class Question
{
    abstract String getQuestionText();

    abstract boolean checkAnswer(String response);

    abstract String fetchCorrectAnswer();

    public abstract String toString();

    public ReferenceCell generateReference(Context context)
    {
        ReferenceCell cell = new ReferenceCell(context);
        cell.setSubject(getQuestionText());
        cell.setDescription(fetchCorrectAnswer());
        if (getQuestionText().length() > 1)
            cell.setSubjectSize(TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_SP, 52, context.getResources().getDisplayMetrics()));
        return cell;
    }


    public static boolean isDiacritic(int charCode)
    {
        if ((charCode < 0x3041) || (charCode > 0x30FF))
            return false; //not kana anyway
        else if (((charCode >= 0x30F7) && (charCode <= 0x30FA)) || //special katakana diacritics
                ((charCode >= 0x3099) && (charCode <= 0x309C))) //single diacritic characters
            return true;

        charCode %= 0x60; //can now run the calculations once for both hiragana and katakana

        if (((charCode >= 0x40) && (charCode <= 0x4B)) || //early vowels
                ((charCode >= 0x0A) && (charCode <= 0x0E)) || //N-set
                ((charCode >= 0x1E) && (charCode <= 0x33))) //M, Y, R, W-sets and N
            return false;

        else if ((charCode >= 0x4C) || (charCode <= 0x03))
            return ((charCode % 2) == 0); //K and S-sets, and first few T-set chars

        else if (charCode <= 0x09) // && charCode >= 0x04
            return ((charCode % 2) == 1); //last few T-set chars

        else if (charCode <= 0x1D) // && charCode >= 0x0F
            return ((charCode % 3) != 0); //H-set with two types of diacritics

        else
            return ((charCode == 0x34) || (charCode == 0x3E));
        //two special characters at the end of the block, which I'm counting.
    }

    enum KanaSystem
    {
        HIRAGANA,
        KATAKANA,
        DIACRITIC_MARK,
        BOTH_KANA,
        NO_KANA
    }

    public static KanaSystem whatKanaSystem(int charCode)
    {
        if ((charCode < 0x3041) || (charCode > 0x30FF))
            return KanaSystem.NO_KANA; //not kana anyway
        else if ((charCode <= 0x3096) || ((charCode >= 0x309D) && (charCode <= 0x309F)))
            return KanaSystem.HIRAGANA;
        else if (((charCode >= 0x30A1) && (charCode <= 0x30FA)) || (charCode >= 0x30FD))
            return KanaSystem.KATAKANA;
        else if ((charCode >= 0x3099) && (charCode <= 0x309C))
            return KanaSystem.DIACRITIC_MARK;
        else if (charCode == 0x30A0) // Double hyphen
            return KanaSystem.BOTH_KANA;
        else if (charCode == 0x30FC) // Prolonged sound mark
            return KanaSystem.BOTH_KANA;
        else if (charCode == 0x30FB) // Middle Dot
            return KanaSystem.KATAKANA;
        else
            return KanaSystem.NO_KANA; //possibly a reserved character in the block
    }
}
