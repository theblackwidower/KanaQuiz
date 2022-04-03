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

import java.util.Map;
import java.util.TreeMap;

import static com.noprestige.kanaquiz.questions.KanjiQuestion.MEANING_DELIMITER;

public class WordQuestion extends Question
{
    private final String romaji;
    private final String kana;
    private final String kanji;
    private final String answer;
    private final String[] altAnswers;
    private final String setTitle;

    public WordQuestion(String romaji, String answer, String kana, String kanji, String[] altAnswers, String setTitle)
    {
        this.romaji = romaji.trim();
        this.answer = answer.trim();
        this.kana = (kana != null) ? kana.trim() : null;
        this.kanji = (kanji != null) ? kanji.trim() : null;
        this.altAnswers = altAnswers;
        this.setTitle = setTitle;
    }

    enum QuestionTextType
    {
        ROMAJI,
        KANA,
        KANJI
    }

    @Override
    public String getQuestionText()
    {
        if (OptionsControl.compareStrings(R.string.prefid_vocab_display, R.string.prefid_vocab_display_romaji))
            return fetchText(QuestionTextType.ROMAJI);
        else if (OptionsControl.compareStrings(R.string.prefid_vocab_display, R.string.prefid_vocab_display_kana))
            return fetchText(QuestionTextType.KANA);
        else if (OptionsControl.compareStrings(R.string.prefid_vocab_display, R.string.prefid_vocab_display_kanji))
            return fetchText(QuestionTextType.KANJI);
        else
            return fetchText(QuestionTextType.KANA);
    }

    @SuppressWarnings("fallthrough")
    private String fetchText(QuestionTextType type)
    {
        switch (type)
        {
            case KANJI:
                if (kanji != null)
                    return kanji;
            case KANA:
                if (kana != null)
                    return kana;
            case ROMAJI:
            default:
                return romaji;
        }
    }

    @Override
    boolean checkAnswer(String response)
    {
        if (answer.trim().equalsIgnoreCase(response.trim()))
            return true;
        else if (altAnswers != null)
            for (String answer : altAnswers)
                if (answer.trim().equalsIgnoreCase(response.trim()))
                    return true;
        if (answer.contains(MEANING_DELIMITER))
            for (String subAnswer : answer.split(MEANING_DELIMITER))
                if (subAnswer.trim().equalsIgnoreCase(response.trim()))
                    return true;
        return false;
    }

    @Override
    public String fetchCorrectAnswer()
    {
        return answer;
    }

    @Override
    public String getDatabaseKey()
    {
        return romaji;
    }

    @Override
    public Map<String, String> getReferenceDetails()
    {
        String currentText = getQuestionText();

        Map<String, String> details = new TreeMap<>();
        if ((romaji != null) && !romaji.equals(currentText))
            details.put('\u0000' + "Romaji", romaji);
        if ((kana != null) && !kana.equals(currentText))
            details.put("Kana", kana);
        if ((kanji != null) && !kanji.equals(currentText))
            details.put("Kanji", kanji);

        StringBuffer answer = new StringBuffer(this.answer);
        if (altAnswers != null)
        {
            answer.append(System.getProperty("line.separator"));
            answer.append("(");
            for (String thisSynonym : altAnswers)
            {
                answer.append(thisSynonym);
                answer.append(", ");
            }
            if (answer.length() > 2)
                answer.delete(answer.length() - 2, answer.length());
            answer.append(")");
        }
        details.put("Translation", answer.toString());

        return details;
    }

    @Override
    public String getReferenceHeader(Resources resources)
    {
        return resources.getString(R.string.vocabulary) + " - " + setTitle;
    }

    @Override
    public ReferenceCell generateReference(Context context)
    {
        ReferenceCell cell = super.generateReference(context);
        cell.setSubjectSize(context.getResources().getDimension(R.dimen.vocabReferenceSubjectSize));
        return cell;
    }

    @Override
    QuestionType getType()
    {
        return QuestionType.VOCABULARY;
    }
}
