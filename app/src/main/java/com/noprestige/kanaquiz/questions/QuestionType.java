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

public enum QuestionType
{
    KANA,
    VOCABULARY,
    KANJI,
    KUN_YOMI,
    ON_YOMI;

    public static QuestionType getQuestionType(Class klass)
    {
        if (KanaQuestion.class.equals(klass))
            return KANA;
        else if (WordQuestion.class.equals(klass))
            return VOCABULARY;
        else if (KanjiQuestion.class.equals(klass))
            return KANJI;
        else if (KunYomiQuestion.class.equals(klass))
            return KUN_YOMI;
        else if (OnYomiQuestion.class.equals(klass))
            return ON_YOMI;
        else
            throw new IllegalArgumentException(klass.getName() + " is not a valid Question class.");
    }
}
