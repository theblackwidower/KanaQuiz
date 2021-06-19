/*
 *    Copyright 2021 T Duke Perry
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.noprestige.kanaquiz.questions;

import android.content.Context;

import com.noprestige.kanaquiz.reference.ReferenceCell;

public abstract class Question
{
    public abstract String getQuestionText();

    abstract boolean checkAnswer(String response);

    public abstract String fetchCorrectAnswer();

    public abstract String getDatabaseKey();

    public ReferenceCell generateReference(Context context)
    {
        ReferenceCell cell = new ReferenceCell(context);
        cell.setSubject(getQuestionText());
        cell.setDescription(fetchCorrectAnswer());
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

    public static KanaSystem whatKanaSystem(String kana)
    {
        int hiraganaCount = 0;
        int katakanaCount = 0;
        int diacriticMarkCount = 0;
        int bothKanaCount = 0;
        int noKanaCount = 0;

        for (int i = 0; i < kana.length(); i++)
            switch (whatKanaSystem(kana.charAt(i)))
            {
                case HIRAGANA:
                    hiraganaCount++;
                    break;
                case KATAKANA:
                    katakanaCount++;
                    break;
                case DIACRITIC_MARK:
                    diacriticMarkCount++;
                    break;
                case BOTH_KANA:
                    bothKanaCount++;
                    break;
                case NO_KANA:
                    noKanaCount++;
            }

        if (noKanaCount > 0)
            return KanaSystem.NO_KANA;
        else if ((hiraganaCount > 0) || (katakanaCount > 0))
            if (hiraganaCount <= 0)
                return KanaSystem.KATAKANA;
            else if (katakanaCount <= 0)
                return KanaSystem.HIRAGANA;
            else
                return KanaSystem.BOTH_KANA;
        else if (diacriticMarkCount > 0)
            return KanaSystem.DIACRITIC_MARK;
        else if (bothKanaCount > 0)
            return KanaSystem.BOTH_KANA;
        else
            return null;
    }
}
