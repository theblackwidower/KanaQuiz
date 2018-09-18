package com.noprestige.kanaquiz.questions;

import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

        assertThat(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][0].getKana(), is("あ"));
        assertThat(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][0].fetchRomanji(null), is("a"));
        assertThat(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][1].getKana(), is("い"));
        assertThat(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][1].fetchRomanji(null), is("i"));
        assertThat(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][2].getKana(), is("う"));
        assertThat(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][2].fetchRomanji(null), is("u"));
        assertThat(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][3].getKana(), is("え"));
        assertThat(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][3].fetchRomanji(null), is("e"));
        assertThat(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][4].getKana(), is("お"));
        assertThat(kanaSetList.get(0)[NO_DIACRITIC.ordinal()][0][4].fetchRomanji(null), is("o"));


        assertThat(prefIdList.get(1), is("set2"));
        assertThat(setTitleList.get(1), is("Second Set"));
        assertThat(setNoDiacriticsTitleList.get(1), is(nullValue()));

        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][0].getKana(), is("な"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][0].fetchRomanji(null), is("na"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][1].getKana(), is("に"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][1].fetchRomanji(null), is("ni"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][2].getKana(), is("ぬ"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][2].fetchRomanji(null), is("nu"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][3].getKana(), is("ね"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][3].fetchRomanji(null), is("ne"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][4].getKana(), is("の"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][0][4].fetchRomanji(null), is("no"));

        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][1][0].getKana(), is("にゃ"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][1][0].fetchRomanji(null), is("nya"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][1][1].getKana(), is("にゅ"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][1][1].fetchRomanji(null), is("nyu"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][1][2].getKana(), is("にょ"));
        assertThat(kanaSetList.get(1)[NO_DIACRITIC.ordinal()][1][2].fetchRomanji(null), is("nyo"));


        assertThat(prefIdList.get(2), is("set3"));
        assertThat(setTitleList.get(2), is("Third Set with Diacritics"));
        assertThat(setNoDiacriticsTitleList.get(2), is("Third Set"));

        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][0].getKana(), is("は"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][0].fetchRomanji(null), is("ha"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][1].getKana(), is("ひ"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][1].fetchRomanji(null), is("hi"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][2].getKana(), is("ふ"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][2].fetchRomanji(null), is("fu"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][2].fetchRomanji(NIHON), is("hu"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][2].fetchRomanji(KUNREI), is("hu"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][3].getKana(), is("へ"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][3].fetchRomanji(null), is("he"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][4].getKana(), is("ほ"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][0][4].fetchRomanji(null), is("ho"));

        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][0][0].getKana(), is("ば"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][0][0].fetchRomanji(null), is("ba"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][0][1].getKana(), is("び"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][0][1].fetchRomanji(null), is("bi"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][0][2].getKana(), is("ぶ"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][0][2].fetchRomanji(null), is("bu"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][0][3].getKana(), is("べ"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][0][3].fetchRomanji(null), is("be"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][0][4].getKana(), is("ぼ"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][0][4].fetchRomanji(null), is("bo"));

        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][0].getKana(), is("ぱ"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][0].fetchRomanji(null), is("pa"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][1].getKana(), is("ぴ"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][1].fetchRomanji(null), is("pi"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][2].getKana(), is("ぷ"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][2].fetchRomanji(null), is("pu"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][3].getKana(), is("ぺ"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][3].fetchRomanji(null), is("pe"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][4].getKana(), is("ぽ"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][0][4].fetchRomanji(null), is("po"));

        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][1][0].getKana(), is("ひゃ"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][1][0].fetchRomanji(null), is("hya"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][1][1].getKana(), is("ひゅ"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][1][1].fetchRomanji(null), is("hyu"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][1][2].getKana(), is("ひょ"));
        assertThat(kanaSetList.get(2)[NO_DIACRITIC.ordinal()][1][2].fetchRomanji(null), is("hyo"));

        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][1][0].getKana(), is("びゃ"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][1][0].fetchRomanji(null), is("bya"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][1][1].getKana(), is("びゅ"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][1][1].fetchRomanji(null), is("byu"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][1][2].getKana(), is("びょ"));
        assertThat(kanaSetList.get(2)[DAKUTEN.ordinal()][1][2].fetchRomanji(null), is("byo"));

        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][1][0].getKana(), is("ぴゃ"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][1][0].fetchRomanji(null), is("pya"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][1][1].getKana(), is("ぴゅ"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][1][1].fetchRomanji(null), is("pyu"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][1][2].getKana(), is("ぴょ"));
        assertThat(kanaSetList.get(2)[HANDAKUTEN.ordinal()][1][2].fetchRomanji(null), is("pyo"));

        assertThat(kanaSetList.get(2)[CONSONANT.ordinal()][0][0].getKana(), is("ん"));
        assertThat(kanaSetList.get(2)[CONSONANT.ordinal()][0][0].fetchRomanji(null), is("n"));
    }
}
