/*
 *    Copyright 2018 T Duke Perry
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

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import androidx.test.platform.app.InstrumentationRegistry;

import static com.noprestige.kanaquiz.questions.KanaQuestion.RomanizationSystem.KUNREI;
import static com.noprestige.kanaquiz.questions.KanaQuestion.RomanizationSystem.NIHON;
import static com.noprestige.kanaquiz.questions.QuestionManagement.Diacritic.CONSONANT;
import static com.noprestige.kanaquiz.questions.QuestionManagement.Diacritic.DAKUTEN;
import static com.noprestige.kanaquiz.questions.QuestionManagement.Diacritic.HANDAKUTEN;
import static com.noprestige.kanaquiz.questions.QuestionManagement.Diacritic.NO_DIACRITIC;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class XmlParserTest
{
    @Test
    public void parsingTest()
    {
        Map<QuestionManagement.SetCode, Question[]> kanaSetList = new TreeMap<>();
        List<String> prefIdList = new ArrayList<>();
        List<String> setTitleList = new ArrayList<>();
        List<String> setNoDiacriticsTitleList = new ArrayList<>();

        XmlParser.parseXmlDocument(com.noprestige.kanaquiz.dev.test.R.xml.test_questions,
                InstrumentationRegistry.getInstrumentation().getContext().getResources(), kanaSetList, prefIdList,
                setTitleList, setNoDiacriticsTitleList);

//        assertThat(kanaSetList.size(), is(3));
        assertThat(prefIdList.size(), is(4));
        assertThat(setTitleList.size(), is(4));
        assertThat(setNoDiacriticsTitleList.size(), is(4));


        assertThat(prefIdList.get(0), is("set1"));
        assertThat(setTitleList.get(0), is("First Set"));
        assertThat(setNoDiacriticsTitleList.get(0), is(nullValue()));

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(1, NO_DIACRITIC, null))[0], "あ", "a");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(1, NO_DIACRITIC, null))[1], "い", "i");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(1, NO_DIACRITIC, null))[2], "う", "u");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(1, NO_DIACRITIC, null))[3], "え", "e");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(1, NO_DIACRITIC, null))[4], "お", "o");


        assertThat(prefIdList.get(1), is("set2"));
        assertThat(setTitleList.get(1), is("Second Set"));
        assertThat(setNoDiacriticsTitleList.get(1), is(nullValue()));

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, null))[0], "な", "na");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, null))[1], "に", "ni");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, null))[2], "ぬ", "nu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, null))[3], "ね", "ne");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, null))[4], "の", "no");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, "y_set"))[0], "にゃ", "nya");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, "y_set"))[1], "にゅ", "nyu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, "y_set"))[2], "にょ", "nyo");


        assertThat(prefIdList.get(2), is("set3"));
        assertThat(setTitleList.get(2), is("Third Set with Diacritics"));
        assertThat(setNoDiacriticsTitleList.get(2), is("Third Set"));

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, null))[0], "は", "ha");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, null))[1], "ひ", "hi");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, null))[2], "ふ", "fu");
        assertThat(((KanaQuestion) kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, null))[2])
                .fetchRomaji(NIHON), is("hu"));
        assertThat(((KanaQuestion) kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, null))[2])
                .fetchRomaji(KUNREI), is("hu"));
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, null))[3], "へ", "he");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, null))[4], "ほ", "ho");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, null))[0], "ば", "ba");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, null))[1], "び", "bi");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, null))[2], "ぶ", "bu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, null))[3], "べ", "be");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, null))[4], "ぼ", "bo");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, null))[0], "ぱ", "pa");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, null))[1], "ぴ", "pi");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, null))[2], "ぷ", "pu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, null))[3], "ぺ", "pe");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, null))[4], "ぽ", "po");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, "y_set"))[0], "ひゃ", "hya");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, "y_set"))[1], "ひゅ", "hyu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, "y_set"))[2], "ひょ", "hyo");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, "y_set"))[0], "びゃ", "bya");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, "y_set"))[1], "びゅ", "byu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, "y_set"))[2], "びょ", "byo");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, "y_set"))[0], "ぴゃ", "pya");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, "y_set"))[1], "ぴゅ", "pyu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, "y_set"))[2], "ぴょ", "pyo");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, CONSONANT, null))[0], "ん", "n");


        assertThat(prefIdList.get(3), is("y_set"));
        assertThat(setTitleList.get(3), is("Y Set"));
        assertThat(setNoDiacriticsTitleList.get(3), is(nullValue()));

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(4, NO_DIACRITIC, null))[0], "や", "ya");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(4, NO_DIACRITIC, null))[1], "ゆ", "yu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(4, NO_DIACRITIC, null))[2], "よ", "yo");
    }

    private void assertKanaQuestion(Question question, String expectedKana, String expectedRomaji)
    {
        assertThat(question.getQuestionText(), is(expectedKana));
        assertThat(((KanaQuestion) question).fetchRomaji(null), is(expectedRomaji));
    }
}
