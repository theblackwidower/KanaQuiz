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

public class KunYomiQuestion extends KanjiQuestion
{
    KunYomiQuestion(KanjiQuestion parent)
    {
        super(parent.kanji, parent.meaning, parent.kunYomi, parent.onYomi);
    }

    @Override
    boolean checkAnswer(String response)
    {
        if (kunYomi.trim().equalsIgnoreCase(response.trim()))
            return true;
        if (kunYomi.contains(MEANING_DELIMITER))
            for (String subAnswer : kunYomi.split(MEANING_DELIMITER))
                if (subAnswer.trim().equalsIgnoreCase(response.trim()))
                    return true;

        return false;
    }

    @Override
    String fetchCorrectAnswer()
    {
        return kunYomi;
    }

    @Override
    QuestionType getType()
    {
        return QuestionType.KUN_YOMI;
    }
}
