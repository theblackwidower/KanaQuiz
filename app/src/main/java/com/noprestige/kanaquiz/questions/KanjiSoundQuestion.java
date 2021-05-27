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

import static com.noprestige.kanaquiz.questions.KanjiQuestion.MEANING_DELIMITER;

public class KanjiSoundQuestion extends Question
{
    private final String kanji;
    private final QuestionType type;
    private final String answer;

    KanjiSoundQuestion(KanjiQuestion parent, QuestionType type)
    {
        kanji = parent.kanji;

        switch (type)
        {
            case KUN_YOMI:
                answer = parent.kunYomi;
                break;
            case ON_YOMI:
                answer = parent.onYomi;
                break;
            default:
                throw new IllegalArgumentException(type.name() + " is not a valid type for a KanjiSoundQuestion.");
        }
        this.type = type;
    }

    @Override
    String getQuestionText()
    {
        return kanji;
    }

    @Override
    boolean checkAnswer(String response)
    {
        if (answer.trim().equalsIgnoreCase(response.trim()))
            return true;
        if (answer.contains(MEANING_DELIMITER))
            for (String subAnswer : answer.split(MEANING_DELIMITER))
                if (subAnswer.trim().equalsIgnoreCase(response.trim()))
                    return true;

        return false;
    }

    @Override
    String fetchCorrectAnswer()
    {
        return answer;
    }

    @Override
    String getDatabaseKey()
    {
        return kanji;
    }

    @Override
    QuestionType getType()
    {
        return type;
    }
}
