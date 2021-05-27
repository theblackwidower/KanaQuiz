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

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.reference.ReferenceCell;

public class KanjiQuestion extends Question
{
    public static final String MEANING_DELIMITER = "/";

    final String kanji;
    final String meaning;
    final String kunYomi;
    final String onYomi;

    KanjiQuestion(String kanji, String meaning, String kunYomi, String onYomi)
    {
        this.kanji = kanji;
        this.meaning = meaning;
        this.kunYomi = kunYomi;
        this.onYomi = onYomi;
    }

    KunYomiQuestion getKunYomiQuestion()
    {
        return new KunYomiQuestion(this);
    }

    OnYomiQuestion getOnYomiQuestion()
    {
        return new OnYomiQuestion(this);
    }

    @Override
    String getQuestionText()
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
    public ReferenceCell generateReference(Context context)
    {
        ReferenceCell cell = super.generateReference(context);
        cell.setSubjectSize(context.getResources().getDimension(R.dimen.kanjiReferenceSubjectSize));
        cell.setDescriptionSize(context.getResources().getDimension(R.dimen.kanjiReferenceDescriptionSize));
        return cell;
    }

    @Override
    QuestionType getType()
    {
        return QuestionType.KANJI;
    }
}
