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

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;

import com.noprestige.kanaquiz.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static androidx.room.util.StringUtil.EMPTY_STRING_ARRAY;
import static com.noprestige.kanaquiz.questions.Question.EMPTY_QUESTION_ARRAY;

final class XmlParser
{
    private XmlParser() {}

    public static void parseXmlFileSetDocument(int xmlRefId, Resources resources,
            Collection<QuestionManagement> fileSetList, Collection<String> titleList)
    {
        XmlResourceParser parser = resources.getXml(xmlRefId);

        try
        {
            for (int eventType = parser.getEventType(); eventType != XmlPullParser.END_DOCUMENT;
                    eventType = parser.next())
            {
                if ((eventType == XmlPullParser.START_TAG) && "QuestionFile".equalsIgnoreCase(parser.getName()))
                {
                    int resourceFileId = 0;
                    String title = null;

                    for (int i = 0; i < parser.getAttributeCount(); i++)
                    {
                        switch (parser.getAttributeName(i))
                        {
                            case "resId":
                                resourceFileId = parser.getAttributeResourceValue(i, 0);
                                break;
                            case "title":
                                title = parseXmlValue(parser, i, resources);
                        }
                    }

                    if ((resourceFileId == 0) || (title == null))
                        throw new ParseException("Missing attribute in QuestionFile", 0);

                    fileSetList.add(new QuestionManagement(resourceFileId, resources));
                    titleList.add(title);
                }
            }
        }
        catch (XmlPullParserException | IOException | ParseException ex)
        {
            //if this happens, we have bigger problems
            //noinspection ProhibitedExceptionThrown
            throw new RuntimeException(ex);
        }
    }

    static void parseXmlDocument(int xmlRefId, Resources resources,
            Map<QuestionManagement.SetCode, Question[]> questionSetList, Collection<String> prefIdList,
            Collection<String> setTitleList, Collection<String> setNoDiacriticsTitleList)
    {
        XmlResourceParser parser = resources.getXml(xmlRefId);

        try
        {
            for (int eventType = parser.getEventType(); eventType != XmlPullParser.END_DOCUMENT;
                    eventType = parser.next())
            {
                if ((eventType == XmlPullParser.START_TAG) && "QuestionSet".equalsIgnoreCase(parser.getName()))
                {
                    parseXmlQuestionSet(parser, resources, questionSetList, prefIdList, setTitleList,
                            setNoDiacriticsTitleList);
                }
            }
        }
        catch (XmlPullParserException | IOException | ParseException ex)
        {
            //if this happens, we have bigger problems
            //noinspection ProhibitedExceptionThrown
            throw new RuntimeException(ex);
        }
    }

    private static void parseXmlQuestionSet(XmlPullParser parser, Resources resources,
            Map<QuestionManagement.SetCode, Question[]> questionSetList, Collection<String> prefIdList,
            Collection<String> setTitleList, Collection<String> setNoDiacriticsTitleList)
            throws XmlPullParserException, IOException, ParseException
    {
        String prefId = null;
        String setTitle = null;
        String setNoDiacriticsTitle = null;

        for (int i = 0; i < parser.getAttributeCount(); i++)
        {
            switch (parser.getAttributeName(i))
            {
                case "prefId":
                    prefId = parseXmlValue(parser, i, resources);
                    break;
                case "setTitle":
                    setTitle = parseXmlValue(parser, i, resources, R.string.set, R.string.extended_katakana_title);
                    break;
                case "setNoDiacriticsTitle":
                    setNoDiacriticsTitle =
                            parseXmlValue(parser, i, resources, R.string.set, R.string.extended_katakana_title);
            }
        }

        if ((prefId == null) || (setTitle == null))
            throw new ParseException("Missing attribute in QuestionSet", parser.getLineNumber());

        prefIdList.add(prefId);
        setTitleList.add(setTitle);
        setNoDiacriticsTitleList.add(setNoDiacriticsTitle);

        int indexPoint = prefIdList.size();

        List<Question> currentSet = new ArrayList<>();

        int lineNumber = parser.getLineNumber();

        for (int eventType = parser.getEventType();
                !((eventType == XmlPullParser.END_TAG) && "QuestionSet".equalsIgnoreCase(parser.getName()));
                eventType = parser.next())
        {
            if (eventType == XmlPullParser.START_TAG)
            {
                if ("Section".equalsIgnoreCase(parser.getName()))
                    parseXmlKanaSubsection(parser, resources, questionSetList, indexPoint);

                else if ("KanaQuestion".equalsIgnoreCase(parser.getName()))
                    currentSet.add(parseXmlKanaQuestion(parser, resources));

                else if ("KanjiQuestion".equalsIgnoreCase(parser.getName()))
                    currentSet.add(parseXmlKanjiQuestion(parser, resources));

                else if ("WordQuestion".equalsIgnoreCase(parser.getName()))
                    currentSet.add(parseXmlWordQuestion(parser, resources));
            }

            else if (eventType == XmlPullParser.END_DOCUMENT)
                throw new ParseException("Missing QuestionSet closing tag", lineNumber);
        }

        questionSetList.put(new QuestionManagement.SetCode(indexPoint, QuestionManagement.Diacritic.NO_DIACRITIC, null),
                currentSet.toArray(EMPTY_QUESTION_ARRAY));
    }

    private static void parseXmlKanaSubsection(XmlPullParser parser, Resources resources,
            Map<QuestionManagement.SetCode, Question[]> questionSetList, int indexPoint)
            throws XmlPullParserException, IOException, ParseException
    {
        QuestionManagement.Diacritic diacritics = null;
        String digraphs = null;

        for (int i = 0; i < parser.getAttributeCount(); i++)
        {
            switch (parser.getAttributeName(i))
            {
                case "diacritics":
                    diacritics = QuestionManagement.Diacritic.valueOf(parser.getAttributeValue(i));
                    break;
                case "digraphs":
                    digraphs = parser.getAttributeValue(i);
            }
        }

        if (diacritics == null)
            throw new ParseException("Missing diacritics attribute in Section tag", parser.getLineNumber());

        int lineNumber = parser.getLineNumber();

        List<Question> currentSet = new ArrayList<>();

        for (int eventType = parser.getEventType();
                !((eventType == XmlPullParser.END_TAG) && "Section".equalsIgnoreCase(parser.getName()));
                eventType = parser.next())
        {
            if ((eventType == XmlPullParser.START_TAG) && "KanaQuestion".equalsIgnoreCase(parser.getName()))
                currentSet.add(parseXmlKanaQuestion(parser, resources));

            else if (eventType == XmlPullParser.END_DOCUMENT)
                throw new ParseException("Missing Section closing tag", lineNumber);
        }

        questionSetList.put(new QuestionManagement.SetCode(indexPoint, diacritics, digraphs),
                currentSet.toArray(EMPTY_QUESTION_ARRAY));
    }

    private static KanaQuestion parseXmlKanaQuestion(XmlPullParser parser, Resources resources)
            throws ParseException, XmlPullParserException, IOException
    {
        String thisQuestion = null;
        String thisAnswer = null;

        for (int i = 0; i < parser.getAttributeCount(); i++)
        {
            switch (parser.getAttributeName(i))
            {
                case "question":
                    thisQuestion = parseXmlValue(parser, i, resources);
                    break;
                case "answer":
                    thisAnswer = parseXmlValue(parser, i, resources);
            }
        }

        if ((thisQuestion == null) || (thisAnswer == null))
            throw new ParseException("Missing attribute in KanaQuestion", parser.getLineNumber());

        TreeMap<KanaQuestion.RomanizationSystem, String> thisAltAnswers = null;

        if (!((parser.next() == XmlPullParser.END_TAG) && "KanaQuestion".equalsIgnoreCase(parser.getName())))
            thisAltAnswers = parseXmlKanaAltAnswers(parser);

        return new KanaQuestion(thisQuestion, thisAnswer, thisAltAnswers);
    }

    private static KanjiQuestion parseXmlKanjiQuestion(XmlPullParser parser, Resources resources) throws ParseException
    {
        String thisQuestion = null;
        String thisMeaning = null;
        String thisKunYomi = null;
        String thisOnYomi = null;

        for (int i = 0; i < parser.getAttributeCount(); i++)
        {
            switch (parser.getAttributeName(i))
            {
                case "question":
                    thisQuestion = parseXmlValue(parser, i, resources);
                    break;
                case "meaning":
                    thisMeaning = parseXmlValue(parser, i, resources);
                    break;
                case "kunYomi":
                    thisKunYomi = parseXmlValue(parser, i, resources);
                    break;
                case "onYomi":
                    thisOnYomi = parseXmlValue(parser, i, resources);
            }
        }

        if ((thisQuestion == null) || (thisMeaning == null) || ((thisKunYomi == null) && (thisOnYomi == null)))
            throw new ParseException("Missing attribute in KanjiQuestion", parser.getLineNumber());

        return new KanjiQuestion(thisQuestion, thisMeaning, thisKunYomi, thisOnYomi);
    }

    private static WordQuestion parseXmlWordQuestion(XmlPullParser parser, Resources resources)
            throws ParseException, IOException, XmlPullParserException
    {
        String thisRomaji = null;
        String thisKana = null;
        String thisKanji = null;
        String thisAnswer = null;
        for (int i = 0; i < parser.getAttributeCount(); i++)
        {
            switch (parser.getAttributeName(i))
            {
                case "romaji":
                    thisRomaji = parseXmlValue(parser, i, resources);
                    break;
                case "kana":
                    thisKana = parseXmlValue(parser, i, resources);
                    break;
                case "kanji":
                    thisKanji = parseXmlValue(parser, i, resources);
                    break;
                case "answer":
                    thisAnswer = parseXmlValue(parser, i, resources);
            }
        }
        if ((thisRomaji == null) || (thisAnswer == null))
            throw new ParseException("Missing attribute in WordQuestion", 0);

        String[] thisAltAnswers = null;

        if (!((parser.next() == XmlPullParser.END_TAG) && "WordQuestion".equalsIgnoreCase(parser.getName())))
            thisAltAnswers = parseXmlWordAltAnswers(parser);

        return new WordQuestion(thisRomaji, thisAnswer, thisKana, thisKanji, thisAltAnswers);
    }

    private static TreeMap<KanaQuestion.RomanizationSystem, String> parseXmlKanaAltAnswers(XmlPullParser parser)
            throws XmlPullParserException, IOException, ParseException
    {
        int lineNumber = parser.getLineNumber();

        TreeMap<KanaQuestion.RomanizationSystem, String> thisAltAnswers = new TreeMap<>();

        for (int eventType = parser.getEventType();
                !((eventType == XmlPullParser.END_TAG) && "KanaQuestion".equalsIgnoreCase(parser.getName()));
                eventType = parser.next())
        {
            if ((eventType == XmlPullParser.START_TAG) && "AltAnswer".equalsIgnoreCase(parser.getName()))
            {
                KanaQuestion.RomanizationSystem[] systemsList = null;

                for (int i = 0; i < parser.getAttributeCount(); i++)
                {
                    switch (parser.getAttributeName(i))
                    {
                        case "system":
                            systemsList = parseRomanizationSystemList(parser.getAttributeValue(i));
                    }
                }

                if (parser.next() == XmlPullParser.TEXT)
                    for (KanaQuestion.RomanizationSystem system : systemsList)
                        thisAltAnswers.put(system, parser.getText());
                else
                    throw new ParseException("Empty AltAnswer tag", parser.getLineNumber());
            }

            else if (eventType == XmlPullParser.END_DOCUMENT)
                throw new ParseException("Missing KanaQuestion closing tag", lineNumber);
        }

        return thisAltAnswers;
    }

    private static String[] parseXmlWordAltAnswers(XmlPullParser parser)
            throws XmlPullParserException, IOException, ParseException
    {
        int lineNumber = parser.getLineNumber();

        List<String> thisAltAnswers = new ArrayList<>();

        for (int eventType = parser.getEventType();
                !((eventType == XmlPullParser.END_TAG) && "WordQuestion".equalsIgnoreCase(parser.getName()));
                eventType = parser.next())
        {
            if ((eventType == XmlPullParser.START_TAG) && "AltAnswer".equalsIgnoreCase(parser.getName()))
            {
                if (parser.next() == XmlPullParser.TEXT)
                    thisAltAnswers.add(parser.getText());
                else
                    throw new ParseException("Empty AltAnswer tag", parser.getLineNumber());
            }

            else if (eventType == XmlPullParser.END_DOCUMENT)
                throw new ParseException("Missing KanaQuestion closing tag", lineNumber);
        }

        return thisAltAnswers.toArray(EMPTY_STRING_ARRAY);
    }

    private static KanaQuestion.RomanizationSystem[] parseRomanizationSystemList(String attributeString)
    {
        String[] stringList = attributeString.trim().split("\\s+");
        KanaQuestion.RomanizationSystem[] systemList = new KanaQuestion.RomanizationSystem[stringList.length];

        for (int i = 0; i < stringList.length; i++)
            systemList[i] = KanaQuestion.RomanizationSystem.valueOf(stringList[i]);

        return systemList;
    }

    private static String parseXmlValue(XmlPullParser parser, int index, Resources resources)
    {
        return parseXmlValue(parser, index, resources, 0, 0);
    }

    private static String parseXmlValue(XmlPullParser parser, int index, Resources resources, int stringResource1,
            int stringResource2)
    {
        int refId = 0;
        if (parser instanceof AttributeSet)
            refId = ((AttributeSet) parser).getAttributeResourceValue(index, 0);

        if (refId == 0)
            return parser.getAttributeValue(index);
        else if ((stringResource1 == 0) && (stringResource2 == 0))
            return resources.getString(refId);
        else
            return resources
                    .getString(refId, resources.getString(stringResource1), resources.getString(stringResource2));
    }
}
