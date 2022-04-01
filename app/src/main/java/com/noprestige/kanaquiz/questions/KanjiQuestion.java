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
import com.noprestige.kanaquiz.reference.ReferenceCell;

import java.util.Map;

import androidx.collection.ArrayMap;

public class KanjiQuestion extends Question
{
    public static final String MEANING_DELIMITER = "/";

    private final String kanji;
    private final String meaning;
    private final String kunYomi;
    private final String onYomi;
    private final String setTitle;

    KanjiQuestion(String kanji, String meaning, String kunYomi, String onYomi, String setTitle)
    {
        this.kanji = kanji;
        this.meaning = meaning;
        this.kunYomi = kunYomi;
        this.onYomi = onYomi;
        this.setTitle = setTitle;
    }

    @Override
    public String getQuestionText()
    {
        return kanji;
    }

    @Override
    boolean checkAnswer(String response)
    {
        if (meaning.trim().equalsIgnoreCase(response.trim()))
            return true;
        if (meaning.contains(MEANING_DELIMITER))
            for (String subAnswer : meaning.split(MEANING_DELIMITER))
                if (subAnswer.trim().equalsIgnoreCase(response.trim()))
                    return true;

        return false;
    }

    @Override
    String fetchCorrectAnswer()
    {
        return meaning;
    }

    @Override
    String getDatabaseKey()
    {
        return kanji;
    }

    @Override
    public Map<String, String> getReferenceDetails()
    {
        Map<String, String> details = new ArrayMap<>();

        if (meaning != null)
            details.put("Meaning", meaning);
        if (kunYomi != null)
            details.put("Kun'yomi", kunYomi);
        if (onYomi != null)
            details.put('\u200D' + "On'yomi", onYomi);

        return details;
    }

    @Override
    public String getReferenceHeader(Resources resources)
    {
        return resources.getString(R.string.kanji) + " - " + setTitle;
    }

    @Override
    public ReferenceCell generateReference(Context context)
    {
        ReferenceCell cell = super.generateReference(context);
        cell.setSubjectSize(context.getResources().getDimension(R.dimen.kanjiReferenceSubjectSize));
        cell.setDescriptionSize(context.getResources().getDimension(R.dimen.kanjiReferenceDescriptionSize));
        return cell;
    }
}
