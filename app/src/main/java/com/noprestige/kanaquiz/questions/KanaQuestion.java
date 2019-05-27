/*
 *    Copyright 2019 T Duke Perry
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

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.reference.ReferenceCell;

import java.util.HashMap;
import java.util.Map;

public class KanaQuestion extends Question
{
    private final String kana;
    private final String defaultRomaji;
    private final Map<RomanizationSystem, String> altRomaji;

    KanaQuestion(String kana, String romaji)
    {
        this(kana, romaji, null);
    }

    KanaQuestion(String kana, String romaji, Map<RomanizationSystem, String> altRomaji)
    {
        this.kana = kana.trim();
        defaultRomaji = romaji.trim();
        this.altRomaji = altRomaji;
    }

    public String getQuestionText()
    {
        return kana;
    }

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

    String fetchCorrectAnswer()
    {
        return fetchRomaji();
    }

    enum RomanizationSystem
    {
        HEPBURN,
        NIHON,
        KUNREI,
        UNKNOWN
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
    String getDatabaseKey()
    {
        return kana;
    }

    @Override
    public Map<String, String> getReferenceDetails()
    {
        Map<String, String> details = new HashMap<>();

        StringBuffer romaji = new StringBuffer(defaultRomaji);
        if (altRomaji != null)
            //ref: https://stackoverflow.com/a/46908/3582371
            for (Map.Entry<RomanizationSystem, String> entry : altRomaji.entrySet())
            {
                romaji.append(System.getProperty("line.separator"));
                romaji.append(entry.getValue());
                romaji.append(" (");
                switch (entry.getKey())
                {
                    case HEPBURN:
                        romaji.append("Hepburn");
                        break;
                    case NIHON:
                        romaji.append("Nihon-shiki");
                        break;
                    case KUNREI:
                        romaji.append("Kunrei-shiki");
                        break;
                    case UNKNOWN:
                        romaji.append("Unknown");
                        break;
                }
                romaji.append(")");
            }
        details.put("Romaji", romaji.toString());
        return details;
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
}
