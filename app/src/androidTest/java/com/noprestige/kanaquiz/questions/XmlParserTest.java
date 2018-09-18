package com.noprestige.kanaquiz.questions;

import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class XmlParserTest
{
    @Test
    public void ParsingTest()
    {
        List<KanaQuestion[][][]> kanaSetList = new ArrayList<>();
        List<String> prefIdList = new ArrayList<>();
        List<String> setTitleList = new ArrayList<>();
        List<String> setNoDiacriticsTitleList = new ArrayList<>();

        XmlParser.parseXmlDocument(com.noprestige.kanaquiz.dev.test.R.xml.test_questions,
                InstrumentationRegistry.getContext().getResources(), kanaSetList, prefIdList, setTitleList,
                setNoDiacriticsTitleList);

        assertThat(kanaSetList.size(), is(3));
        assertThat(prefIdList.size(), is(3));
        assertThat(setTitleList.size(), is(3));
        assertThat(setNoDiacriticsTitleList.size(), is(3));

        assertThat(prefIdList.get(0), is("set1"));
        assertThat(setTitleList.get(0), is("First Set"));
        assertThat(setNoDiacriticsTitleList.get(0), is(nullValue()));
        assertThat(kanaSetList.get(0)[0][0][0].getKana(), is("あ"));
        assertThat(kanaSetList.get(0)[0][0][0].fetchRomanji(null), is("a"));
        assertThat(kanaSetList.get(0)[0][0][1].getKana(), is("い"));
        assertThat(kanaSetList.get(0)[0][0][1].fetchRomanji(null), is("i"));
        assertThat(kanaSetList.get(0)[0][0][2].getKana(), is("う"));
        assertThat(kanaSetList.get(0)[0][0][2].fetchRomanji(null), is("u"));
        assertThat(kanaSetList.get(0)[0][0][3].getKana(), is("え"));
        assertThat(kanaSetList.get(0)[0][0][3].fetchRomanji(null), is("e"));
        assertThat(kanaSetList.get(0)[0][0][4].getKana(), is("お"));
        assertThat(kanaSetList.get(0)[0][0][4].fetchRomanji(null), is("o"));

        assertThat(prefIdList.get(1), is("set2"));
        assertThat(setTitleList.get(1), is("Second Set"));
        assertThat(setNoDiacriticsTitleList.get(1), is(nullValue()));
        assertThat(kanaSetList.get(1)[0][0][0].getKana(), is("な"));
        assertThat(kanaSetList.get(1)[0][0][0].fetchRomanji(null), is("na"));
        assertThat(kanaSetList.get(1)[0][0][1].getKana(), is("に"));
        assertThat(kanaSetList.get(1)[0][0][1].fetchRomanji(null), is("ni"));
        assertThat(kanaSetList.get(1)[0][0][2].getKana(), is("ぬ"));
        assertThat(kanaSetList.get(1)[0][0][2].fetchRomanji(null), is("nu"));
        assertThat(kanaSetList.get(1)[0][0][3].getKana(), is("ね"));
        assertThat(kanaSetList.get(1)[0][0][3].fetchRomanji(null), is("ne"));
        assertThat(kanaSetList.get(1)[0][0][4].getKana(), is("の"));
        assertThat(kanaSetList.get(1)[0][0][4].fetchRomanji(null), is("no"));

        assertThat(kanaSetList.get(1)[0][1][0].getKana(), is("にゃ"));
        assertThat(kanaSetList.get(1)[0][1][0].fetchRomanji(null), is("nya"));
        assertThat(kanaSetList.get(1)[0][1][1].getKana(), is("にゅ"));
        assertThat(kanaSetList.get(1)[0][1][1].fetchRomanji(null), is("nyu"));
        assertThat(kanaSetList.get(1)[0][1][2].getKana(), is("にょ"));
        assertThat(kanaSetList.get(1)[0][1][2].fetchRomanji(null), is("nyo"));

    }
}
