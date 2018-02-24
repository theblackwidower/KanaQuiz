package com.noprestige.kanaquiz.questions;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.noprestige.kanaquiz.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

abstract class XmlParser
{
    static void parseXmlKanaSet(XmlResourceParser parser, Resources resources,
                                ArrayList<KanaQuestion[][][]> kanaSetList, ArrayList<String> prefIdList,
                                ArrayList<String> setTitleList, ArrayList<String> setNoDiacriticsTitleList)
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
                    setTitle = parseXmlValue(parser, i, resources, R.string.set);
                    break;
                case "setNoDiacriticsTitle":
                    setNoDiacriticsTitle = parseXmlValue(parser, i, resources, R.string.set);
            }
        }

        if (prefId == null || setTitle == null)
            throw new ParseException("Missing attribute in KanaQuestion", parser.getLineNumber());

        if (setNoDiacriticsTitle == null)
            setNoDiacriticsTitle = setTitle;

        prefIdList.add(prefId);
        setTitleList.add(setTitle);
        setNoDiacriticsTitleList.add(setNoDiacriticsTitle);

        int indexPoint = kanaSetList.size();

        kanaSetList.add(new KanaQuestion[Diacritic.values().length][2][]);

        ArrayList<KanaQuestion> currentSet = new ArrayList<>();

        int lineNumber = parser.getLineNumber();

        for (int eventType = parser.getEventType(); true; eventType = parser.next())
        {
            if (eventType == XmlPullParser.START_TAG)
            {
                if (parser.getName().equalsIgnoreCase("Section"))
                    parseXmlKanaSubsection(parser, resources, kanaSetList, indexPoint);

                else if (parser.getName().equalsIgnoreCase("KanaQuestion"))
                    currentSet.add(parseXmlKanaQuestion(parser, resources));
            }

            else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("KanaSet"))
            {
                parseXmlStoreSet(currentSet, kanaSetList, indexPoint, Diacritic.NO_DIACRITIC, false);
                break;
            }

            else if (eventType == XmlPullParser.END_DOCUMENT)
                throw new ParseException("Missing KanaSet closing tag", lineNumber);
        }
    }

    static private void parseXmlKanaSubsection(XmlResourceParser parser, Resources resources,
                                               ArrayList<KanaQuestion[][][]> kanaSetList,
                                               int indexPoint)
            throws XmlPullParserException, IOException, ParseException
    {
        Diacritic diacritics = null;
        boolean isDigraphs = false;
        boolean isDigraphsSet = false;

        ArrayList<KanaQuestion> currentSet = new ArrayList<>();

        for (int i = 0; i < parser.getAttributeCount(); i++)
        {
            switch (parser.getAttributeName(i))
            {
                case "diacritics":
                    diacritics = Diacritic.valueOf(parser.getAttributeValue(i));
                    break;
                case "digraphs":
                    isDigraphs = parser.getAttributeBooleanValue(i, false);
                    isDigraphsSet = true;
            }
        }

        if (diacritics == null || !isDigraphsSet)
            throw new ParseException("Missing attribute in Section tag", parser.getLineNumber());

        int lineNumber = parser.getLineNumber();

        for (int eventType = parser.getEventType(); true; eventType = parser.next())
        {
            if (eventType == XmlPullParser.START_TAG && parser.getName().equalsIgnoreCase("KanaQuestion"))
                currentSet.add(parseXmlKanaQuestion(parser, resources));

            else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("Section"))
            {
                parseXmlStoreSet(currentSet, kanaSetList, indexPoint, diacritics, isDigraphs);
                break;
            }
            else if (eventType == XmlPullParser.END_DOCUMENT)
                throw new ParseException("Missing Section closing tag", lineNumber);
        }
    }

    static private void parseXmlStoreSet(ArrayList<KanaQuestion> currentSet, ArrayList<KanaQuestion[][][]> kanaSetList,
                                         int indexPoint, Diacritic diacritics, boolean isDigraphs)
    {
        KanaQuestion[] currentSetArray = new KanaQuestion[currentSet.size()];
        currentSet.toArray(currentSetArray);

        KanaQuestion[][][] pulledArray = kanaSetList.get(indexPoint);
        pulledArray[diacritics.ordinal()][isDigraphs ? 1 : 0] = currentSetArray;
        kanaSetList.set(indexPoint, pulledArray);
    }

    static private KanaQuestion parseXmlKanaQuestion(XmlResourceParser parser, Resources resources)
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

        if (thisQuestion == null || thisAnswer == null)
            throw new ParseException("Missing attribute in KanaQuestion", parser.getLineNumber());

        if (parser.next() == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("KanaQuestion"))
            return new KanaQuestion(thisQuestion, thisAnswer);
        else
            return new KanaQuestion(thisQuestion, thisAnswer, parseXmlAltAnswers(parser));
    }

    static private String[] parseXmlAltAnswers(XmlResourceParser parser)
            throws XmlPullParserException, IOException, ParseException
    {
        ArrayList<String> altAnswers = new ArrayList<>();

        int lineNumber = parser.getLineNumber();

        for (int eventType = parser.getEventType(); true; eventType = parser.next())
        {
            if (eventType == XmlPullParser.START_TAG && parser.getName().equalsIgnoreCase("AltAnswer"))
            {
                eventType = parser.next();
                if (eventType == XmlPullParser.TEXT)
                    altAnswers.add(parser.getText());
                else
                    throw new ParseException("Empty AltAnswer tag", parser.getLineNumber());
            }
            else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("KanaQuestion"))
            {
                if (altAnswers.isEmpty())
                    return null;
                else
                {
                    String[] altAnswersArray = new String[altAnswers.size()];
                    altAnswers.toArray(altAnswersArray);
                    return altAnswersArray;
                }
            }
            else if (eventType == XmlPullParser.END_DOCUMENT)
                throw new ParseException("Missing KanaQuestion closing tag", lineNumber);
        }
    }

    static private String parseXmlValue(XmlResourceParser parser, int index, Resources resources)
    {
        return parseXmlValue(parser, index, resources, 0);
    }

    static private String parseXmlValue(XmlResourceParser parser, int index, Resources resources, int stringResource)
    {
        int refId = parser.getAttributeResourceValue(index, 0);
        return (refId == 0) ? parser.getAttributeValue(index) :
                (stringResource == 0) ? resources.getString(refId) :
                        resources.getString(refId, resources.getString(stringResource));
    }
}
