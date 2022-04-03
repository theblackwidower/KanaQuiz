/*
 *    Copyright 2022 T Duke Perry
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
import android.content.res.Resources;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.reference.ReferenceCell;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class KanaQuestion extends Question
{
    private final String kana;
    private final String defaultRomaji;
    private final Map<RomanizationSystem, String> altRomaji;
    private final boolean isExtended;

    KanaQuestion(String kana, String romaji)
    {
        this(kana, romaji, null, false);
    }

    KanaQuestion(String kana, String romaji, Map<RomanizationSystem, String> altRomaji, boolean isExtended)
    {
        this.kana = kana.trim();
        defaultRomaji = romaji.trim();
        this.altRomaji = altRomaji;
        this.isExtended = isExtended;
    }

    @Override
    public String getQuestionText()
    {
        return kana;
    }

    @Override
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

    @Override
    public String fetchCorrectAnswer()
    {
        return fetchRomaji();
    }

    enum RomanizationSystem
    {
        HEPBURN,
        NIHON,
        KUNREI,
        UNKNOWN;

        public String toString()
        {
            switch (this)
            {
                case HEPBURN:
                    return "Hepburn";
                case NIHON:
                    return "Nihon-shiki";
                case KUNREI:
                    return "Kunrei-shiki";
                case UNKNOWN:
                    return "Unknown";
                default:
                    return null;
            }
        }
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
    public String getDatabaseKey()
    {
        return kana;
    }

    @Override
    public Map<String, String> getReferenceDetails()
    {
        Map<String, String> details = new HashMap<>();

        StringBuffer romaji = new StringBuffer(defaultRomaji);
        if (altRomaji != null)
        {
            Map<String, String> altDetails = new TreeMap<>();
            //ref: https://stackoverflow.com/a/46908/3582371
            for (Map.Entry<RomanizationSystem, String> entry : altRomaji.entrySet())
                if (!altDetails.containsKey(entry.getValue()))
                    altDetails.put(entry.getValue(), entry.getKey().toString());
                else
                {
                    String list = altDetails.remove(entry.getValue());
                    list += ", ";
                    list += entry.getKey().toString();
                    altDetails.put(entry.getValue(), list);
                }
            for (Map.Entry<String, String> entry : altDetails.entrySet())
            {
                romaji.append(System.getProperty("line.separator"));
                romaji.append(entry.getKey());
                romaji.append(" (");
                romaji.append(entry.getValue());
                romaji.append(")");
            }
        }
        details.put("Romaji", romaji.toString());
        return details;
    }

    @Override
    public String getReferenceHeader(Resources resources)
    {
        switch (whatKanaSystem(kana))
        {
            case HIRAGANA:
                return resources.getString(R.string.hiragana);
            case KATAKANA:
                return resources.getString(isExtended ? R.string.extended_katakana_title : R.string.katakana);
            default:
                return null;
        }
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

    @Override
    QuestionType getType()
    {
        return QuestionType.KANA;
    }
}
