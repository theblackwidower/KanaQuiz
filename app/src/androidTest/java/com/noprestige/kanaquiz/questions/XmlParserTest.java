package com.noprestige.kanaquiz.questions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        List<Question[][][]> kanaSetList = new ArrayList<>();
        List<String> prefIdList = new ArrayList<>();
        List<String> setTitleList = new ArrayList<>();
        List<String> setNoDiacriticsTitleList = new ArrayList<>();

        XmlParser.parseXmlDocument(com.noprestige.kanaquiz.dev.test.R.xml.test_questions,
                InstrumentationRegistry.getInstrumentation().getContext().getResources(), kanaSetList, prefIdList,
                setTitleList, setNoDiacriticsTitleList);

        assertThat(kanaSetList.size(), is(3));
        assertThat(prefIdList.size(), is(3));
        assertThat(setTitleList.size(), is(3));
        assertThat(setNoDiacriticsTitleList.size(), is(3));


        assertThat(prefIdList.get(0), is("set1"));
        assertThat(setTitleList.get(0), is("First Set"));
        assertThat(setNoDiacriticsTitleList.get(0), is(nullValue()));

        assertKanaQuestion(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][0], "あ", "a");
        assertKanaQuestion(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][1], "い", "i");
        assertKanaQuestion(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][2], "う", "u");
        assertKanaQuestion(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][3], "え", "e");
        assertKanaQuestion(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][4], "お", "o");


        assertThat(prefIdList.get(1), is("set2"));
        assertThat(setTitleList.get(1), is("Second Set"));
        assertThat(setNoDiacriticsTitleList.get(1), is(nullValue()));

        assertKanaQuestion(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][0], "な", "na");
        assertKanaQuestion(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][1], "に", "ni");
        assertKanaQuestion(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][2], "ぬ", "nu");
        assertKanaQuestion(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][3], "ね", "ne");
        assertKanaQuestion(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][4], "の", "no");

        assertKanaQuestion(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][1][0], "にゃ", "nya");
        assertKanaQuestion(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][1][1], "にゅ", "nyu");
        assertKanaQuestion(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][1][2], "にょ", "nyo");


        assertThat(prefIdList.get(2), is("set3"));
        assertThat(setTitleList.get(2), is("Third Set with Diacritics"));
        assertThat(setNoDiacriticsTitleList.get(2), is("Third Set"));

        assertKanaQuestion(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][0], "は", "ha");
        assertKanaQuestion(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][1], "ひ", "hi");
        assertKanaQuestion(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][2], "ふ", "fu");
        assertThat(((KanaQuestion) kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][2]).fetchRomaji(NIHON), is("hu"));
        assertThat(((KanaQuestion) kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][2]).fetchRomaji(KUNREI), is("hu"));
        assertKanaQuestion(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][3], "へ", "he");
        assertKanaQuestion(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][4], "ほ", "ho");

        assertKanaQuestion(kanaSetList.get(2)[DAKUTEN.ordinal()][0][0], "ば", "ba");
        assertKanaQuestion(kanaSetList.get(2)[DAKUTEN.ordinal()][0][1], "び", "bi");
        assertKanaQuestion(kanaSetList.get(2)[DAKUTEN.ordinal()][0][2], "ぶ", "bu");
        assertKanaQuestion(kanaSetList.get(2)[DAKUTEN.ordinal()][0][3], "べ", "be");
        assertKanaQuestion(kanaSetList.get(2)[DAKUTEN.ordinal()][0][4], "ぼ", "bo");

        assertKanaQuestion(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][0], "ぱ", "pa");
        assertKanaQuestion(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][1], "ぴ", "pi");
        assertKanaQuestion(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][2], "ぷ", "pu");
        assertKanaQuestion(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][3], "ぺ", "pe");
        assertKanaQuestion(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][4], "ぽ", "po");

        assertKanaQuestion(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][1][0], "ひゃ", "hya");
        assertKanaQuestion(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][1][1], "ひゅ", "hyu");
        assertKanaQuestion(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][1][2], "ひょ", "hyo");

        assertKanaQuestion(kanaSetList.get(2)[DAKUTEN.ordinal()][1][0], "びゃ", "bya");
        assertKanaQuestion(kanaSetList.get(2)[DAKUTEN.ordinal()][1][1], "びゅ", "byu");
        assertKanaQuestion(kanaSetList.get(2)[DAKUTEN.ordinal()][1][2], "びょ", "byo");

        assertKanaQuestion(kanaSetList.get(2)[HANDAKUTEN.ordinal()][1][0], "ぴゃ", "pya");
        assertKanaQuestion(kanaSetList.get(2)[HANDAKUTEN.ordinal()][1][1], "ぴゅ", "pyu");
        assertKanaQuestion(kanaSetList.get(2)[HANDAKUTEN.ordinal()][1][2], "ぴょ", "pyo");

        assertKanaQuestion(kanaSetList.get(2)[CONSONANT.ordinal()][0][0], "ん", "n");
    }

    private void assertKanaQuestion(Question question, String expectedKana, String expectedRomaji)
    {
        assertThat(question.getQuestionText(), is(expectedKana));
        assertThat(((KanaQuestion) question).fetchRomaji(null), is(expectedRomaji));
    }
}
