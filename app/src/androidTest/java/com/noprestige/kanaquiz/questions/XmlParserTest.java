package com.noprestige.kanaquiz.questions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import androidx.test.platform.app.InstrumentationRegistry;

import static com.noprestige.kanaquiz.questions.Diacritic.CONSONANT;
import static com.noprestige.kanaquiz.questions.Diacritic.DAKUTEN;
import static com.noprestige.kanaquiz.questions.Diacritic.HANDAKUTEN;
import static com.noprestige.kanaquiz.questions.Diacritic.NO_DIACRITIC;
import static com.noprestige.kanaquiz.questions.RomanizationSystem.KUNREI;
import static com.noprestige.kanaquiz.questions.RomanizationSystem.NIHON;
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
        assertThat(prefIdList.size(), is(3));
        assertThat(setTitleList.size(), is(3));
        assertThat(setNoDiacriticsTitleList.size(), is(3));


        assertThat(prefIdList.get(0), is("set1"));
        assertThat(setTitleList.get(0), is("First Set"));
        assertThat(setNoDiacriticsTitleList.get(0), is(nullValue()));

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(1, NO_DIACRITIC, false))[0], "あ", "a");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(1, NO_DIACRITIC, false))[1], "い", "i");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(1, NO_DIACRITIC, false))[2], "う", "u");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(1, NO_DIACRITIC, false))[3], "え", "e");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(1, NO_DIACRITIC, false))[4], "お", "o");


        assertThat(prefIdList.get(1), is("set2"));
        assertThat(setTitleList.get(1), is("Second Set"));
        assertThat(setNoDiacriticsTitleList.get(1), is(nullValue()));

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, false))[0], "な", "na");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, false))[1], "に", "ni");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, false))[2], "ぬ", "nu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, false))[3], "ね", "ne");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, false))[4], "の", "no");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, true))[0], "にゃ", "nya");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, true))[1], "にゅ", "nyu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(2, NO_DIACRITIC, true))[2], "にょ", "nyo");


        assertThat(prefIdList.get(2), is("set3"));
        assertThat(setTitleList.get(2), is("Third Set with Diacritics"));
        assertThat(setNoDiacriticsTitleList.get(2), is("Third Set"));

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, false))[0], "は", "ha");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, false))[1], "ひ", "hi");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, false))[2], "ふ", "fu");
        assertThat(((KanaQuestion) kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, false))[2])
                .fetchRomaji(NIHON), is("hu"));
        assertThat(((KanaQuestion) kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, false))[2])
                .fetchRomaji(KUNREI), is("hu"));
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, false))[3], "へ", "he");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, false))[4], "ほ", "ho");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, false))[0], "ば", "ba");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, false))[1], "び", "bi");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, false))[2], "ぶ", "bu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, false))[3], "べ", "be");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, false))[4], "ぼ", "bo");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, false))[0], "ぱ", "pa");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, false))[1], "ぴ", "pi");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, false))[2], "ぷ", "pu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, false))[3], "ぺ", "pe");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, false))[4], "ぽ", "po");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, true))[0], "ひゃ", "hya");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, true))[1], "ひゅ", "hyu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, NO_DIACRITIC, true))[2], "ひょ", "hyo");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, true))[0], "びゃ", "bya");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, true))[1], "びゅ", "byu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, DAKUTEN, true))[2], "びょ", "byo");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, true))[0], "ぴゃ", "pya");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, true))[1], "ぴゅ", "pyu");
        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, HANDAKUTEN, true))[2], "ぴょ", "pyo");

        assertKanaQuestion(kanaSetList.get(new QuestionManagement.SetCode(3, CONSONANT, false))[0], "ん", "n");
    }

    private void assertKanaQuestion(Question question, String expectedKana, String expectedRomaji)
    {
        assertThat(question.getQuestionText(), is(expectedKana));
        assertThat(((KanaQuestion) question).fetchRomaji(null), is(expectedRomaji));
    }
}
