package com.noprestige.kanaquiz.questions;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.KanaSelectionItem;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.reference.ReferenceCell;
import com.noprestige.kanaquiz.reference.ReferenceTable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public abstract class QuestionManagement
{
    private static final int CATEGORY_COUNT = 10;

    private KanaQuestion[][][][] kanaSets = new KanaQuestion[][][][]{};

    private String[] prefIds = new String[]{};

    private String[] setTitles = new String[]{};

    private String[] setNoDiacriticsTitles = new String[]{};

    private KanaQuestion[] getKanaSet(int number, Diacritic diacritic, boolean isDigraphs) //TODO: Clean this up
    {
        try
        {
            return kanaSets[number][diacritic.ordinal()][isDigraphs ? 1 : 0];
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            return null;
        }
    }

    private String getPrefId(int number)
    {
        try
        {
            return prefIds[number];
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            return null;
        }
    }

    private String getSetTitle(int number)
    {
        try
        {
            return OptionsControl.getBoolean(R.string.prefid_diacritics) ?
                    setTitles[number] : setNoDiacriticsTitles[number];
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            return null;
        }
    }

    protected static void parseXml(XmlResourceParser xrp, Resources resources, QuestionManagement singletonObject)
    {
        ArrayList<KanaQuestion[][][]> kanaSetList = new ArrayList<>();
        ArrayList<String> prefIdList = new ArrayList<>();
        ArrayList<String> setTitleList = new ArrayList<>();
        ArrayList<String> setNoDiacriticsTitleList = new ArrayList<>();

        int currentSetNumber = -1;
        Diacritic currentDiacritics = null;
        boolean currentIsDigraphs = false;
        String currentPrefId = "";
        String currentSetTitle = "";
        String currentSetNoDiacriticsTitle = null;
        int refId;

        ArrayList<KanaQuestion> currentSet = new ArrayList<>();
        ArrayList<KanaQuestion> backupSet = null;
        try
        {
            for (int eventType = xrp.getEventType(); eventType != XmlPullParser.END_DOCUMENT; eventType = xrp.next())
            {
                if (eventType == XmlPullParser.START_TAG && xrp.getName().equalsIgnoreCase("KanaSet"))
                {
                    currentDiacritics = Diacritic.NO_DIACRITIC;
                    currentIsDigraphs = false;
                    for (int i = 0; i < xrp.getAttributeCount(); i++)
                    {
                        switch (xrp.getAttributeName(i))
                        {
                            case "number":
                                currentSetNumber = xrp.getAttributeIntValue(i, -1);
                                break;
                            case "prefId":
                                refId = xrp.getAttributeResourceValue(i, 0);
                                currentPrefId = (refId == 0) ? xrp.getAttributeValue(i) : resources.getString(refId);
                                break;
                            case "setTitle":
                                refId = xrp.getAttributeResourceValue(i, 0);
                                currentSetTitle = (refId == 0) ? xrp.getAttributeValue(i) : resources.getString(refId, resources.getString(R.string.set));
                                break;
                            case "setNoDiacriticsTitle":
                                refId = xrp.getAttributeResourceValue(i, 0);
                                currentSetNoDiacriticsTitle = (refId == 0) ? xrp.getAttributeValue(i) : resources.getString(refId, resources.getString(R.string.set));
                        }
                    }
                }

                else if (eventType == XmlPullParser.START_TAG && xrp.getName().equalsIgnoreCase("Section"))
                {
                    backupSet = currentSet;
                    currentSet = new ArrayList<>();
                    for (int i = 0; i < xrp.getAttributeCount(); i++)
                    {
                        switch (xrp.getAttributeName(i))
                        {
                            case "diacritics":
                                currentDiacritics = Diacritic.valueOf(xrp.getAttributeValue(i));
                                break;
                            case "digraphs":
                                currentIsDigraphs = xrp.getAttributeBooleanValue(i, false);
                        }
                    }
                }

                else if (eventType == XmlPullParser.START_TAG && xrp.getName().equalsIgnoreCase("KanaQuestion"))
                {
                    String thisQuestion = "";
                    String thisAnswer = "";
                    String thisAltAnswer = null;
                    for (int i = 0; i < xrp.getAttributeCount(); i++)
                    {
                        switch (xrp.getAttributeName(i))
                        {
                            case "question":
                                thisQuestion = xrp.getAttributeValue(i);
                                break;
                            case "answer":
                                thisAnswer = xrp.getAttributeValue(i);
                                break;
                            case "altAnswer":
                                thisAltAnswer = xrp.getAttributeValue(i);
                        }
                    }
                    if (thisAltAnswer == null)
                        currentSet.add(new KanaQuestion(thisQuestion, thisAnswer));
                    else
                        currentSet.add(new KanaQuestion(thisQuestion, new String[]{thisAnswer, thisAltAnswer}));
                }

                else if (eventType == XmlPullParser.END_TAG &&
                        (xrp.getName().equalsIgnoreCase("Section") || xrp.getName().equalsIgnoreCase("KanaSet")))
                {
                    KanaQuestion[] currentSetArray = new KanaQuestion[currentSet.size()];
                    currentSet.toArray(currentSetArray);

                    boolean isSuccess = false;
                    while (!isSuccess)
                    {
                        try
                        {
                            KanaQuestion[][][] pulledArray = kanaSetList.get(currentSetNumber);
                            pulledArray[currentDiacritics.ordinal()][currentIsDigraphs ? 1 : 0] = currentSetArray;
                            kanaSetList.set(currentSetNumber, pulledArray);
                            isSuccess = true;
                        }
                        catch (IndexOutOfBoundsException ex)
                        {
                            kanaSetList.add(new KanaQuestion[Diacritic.values().length][2][]);
                        }
                    }
                    if (xrp.getName().equalsIgnoreCase("Section"))
                    {
                        currentSet = backupSet;
                        backupSet = null;
                        currentDiacritics = Diacritic.NO_DIACRITIC;
                        currentIsDigraphs = false;
                    }
                    else if (xrp.getName().equalsIgnoreCase("KanaSet"))
                    {
                        if (currentSetNumber >= 0)
                        {
                            if (currentSetNoDiacriticsTitle == null)
                                currentSetNoDiacriticsTitle = currentSetTitle;
                            isSuccess = false;
                            while (!isSuccess)
                            {
                                try
                                {
                                    prefIdList.set(currentSetNumber, currentPrefId);
                                    setTitleList.set(currentSetNumber, currentSetTitle);
                                    setNoDiacriticsTitleList.set(currentSetNumber, currentSetNoDiacriticsTitle);
                                    isSuccess = true;
                                }
                                catch (IndexOutOfBoundsException ex)
                                {
                                    prefIdList.add("");
                                    setTitleList.add("");
                                    setNoDiacriticsTitleList.add("");
                                }
                            }
                        }

                        currentSetNumber = -1;
                        currentDiacritics = null;
                        currentIsDigraphs = false;
                        currentPrefId = "";
                        currentSetTitle = "";
                        currentSetNoDiacriticsTitle = null;
                        currentSet = new ArrayList<>();
                    }
                }
            }
            singletonObject.kanaSets = new KanaQuestion[kanaSetList.size()][][][];
            kanaSetList.toArray(singletonObject.kanaSets);
            singletonObject.prefIds = new String[prefIdList.size()];
            prefIdList.toArray(singletonObject.prefIds);
            singletonObject.setTitles = new String[setTitleList.size()];
            setTitleList.toArray(singletonObject.setTitles);
            singletonObject.setNoDiacriticsTitles = new String[setNoDiacriticsTitleList.size()];
            setNoDiacriticsTitleList.toArray(singletonObject.setNoDiacriticsTitles);
        }
        catch (XmlPullParserException | IOException ex)
        {
        }
    }

    private boolean getPref(int number)
    {
        return OptionsControl.getBoolean(getPrefId(number));
    }

    public KanaQuestionBank getQuestionBank()
    {
        KanaQuestionBank questionBank = new KanaQuestionBank();

        boolean isDigraphs = OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9);
        boolean isDiacritics = OptionsControl.getBoolean(R.string.prefid_diacritics);

        for (int i = 1; i <= CATEGORY_COUNT; i++)
            if (getPref(i))
            {
                questionBank.addQuestions(getKanaSet(i, Diacritic.NO_DIACRITIC, false));
                if (isDiacritics)
                {
                    questionBank.addQuestions(getKanaSet(i, Diacritic.DAKUTEN, false));
                    questionBank.addQuestions(getKanaSet(i, Diacritic.HANDAKUTEN, false));
                }
                if (isDigraphs)
                {
                    questionBank.addQuestions(getKanaSet(i, Diacritic.NO_DIACRITIC, true));
                    if (isDiacritics)
                    {
                        questionBank.addQuestions(getKanaSet(i, Diacritic.DAKUTEN, true));
                        questionBank.addQuestions(getKanaSet(i, Diacritic.HANDAKUTEN, true));
                    }
                }
                questionBank.addQuestions(getKanaSet(i, Diacritic.CONSONANT, false));
            }

        return questionBank;
    }

    public boolean anySelected()
    {
        for (int i = 1; i <= CATEGORY_COUNT; i++)
            if (getPref(i))
                return true;

        return false;
    }

    public boolean diacriticsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_diacritics))
            for (int i = 1; i <= CATEGORY_COUNT; i++)
                if (getPref(i) &&
                        (getKanaSet(i, Diacritic.DAKUTEN, false) != null ||
                                getKanaSet(i, Diacritic.HANDAKUTEN, false) != null))
                    return true;

        return false;
    }

    public boolean digraphsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9))
            for (int i = 1; i <= CATEGORY_COUNT; i++)
                if (getPref(i) &&
                        (getKanaSet(i, Diacritic.NO_DIACRITIC, true) != null))
                    return true;

        return false;
    }

    public boolean diacriticDigraphsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_diacritics) &&
                OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9))
            for (int i = 1; i <= CATEGORY_COUNT; i++)
                if (getPref(i) &&
                        (getKanaSet(i, Diacritic.DAKUTEN, true) != null ||
                                getKanaSet(i, Diacritic.HANDAKUTEN, true) != null))
                    return true;

        return false;
    }

    private String getKanaSetDisplay(int setNumber, Diacritic diacritic)
    {
        StringBuilder returnValue = new StringBuilder();
        KanaQuestion[] kanaSet = getKanaSet(setNumber, diacritic, false);
        if (kanaSet != null)
            for (KanaQuestion question : kanaSet)
                returnValue.append(question.getKana() + " ");
        return returnValue.toString();
    }

    private String displayContents(int setNumber)
    {
        boolean isDiacritics = OptionsControl.getBoolean(R.string.prefid_diacritics);

        StringBuilder returnValue = new StringBuilder(getKanaSetDisplay(setNumber, Diacritic.NO_DIACRITIC));

        if (isDiacritics)
        {
            returnValue.append(getKanaSetDisplay(setNumber, Diacritic.DAKUTEN));
            returnValue.append(getKanaSetDisplay(setNumber, Diacritic.HANDAKUTEN));
        }

        returnValue.append(getKanaSetDisplay(setNumber, Diacritic.CONSONANT));

        return returnValue.toString();
    }

    public ReferenceTable getMainReferenceTable(Context context)
    {
        ReferenceTable table = new ReferenceTable(context);

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= 7; i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, Diacritic.NO_DIACRITIC, false)));

        if (isFullReference || getPref(9))
            table.addView(ReferenceCell.buildSpecialRow(context, getKanaSet(9, Diacritic.NO_DIACRITIC, false)));
        if (isFullReference || getPref(8)) //fits gojūon ordering
            table.addView(ReferenceCell.buildRow(context, getKanaSet(8, Diacritic.NO_DIACRITIC, false)));
        if (isFullReference || getPref(10))
        {
            table.addView(ReferenceCell.buildSpecialRow(context, getKanaSet(10, Diacritic.NO_DIACRITIC, false)));
            table.addView(ReferenceCell.buildSpecialRow(context, getKanaSet(10, Diacritic.CONSONANT, false)));
        }

        return table;
    }

    public ReferenceTable getDiacriticReferenceTable(Context context)
    {
        ReferenceTable table = new ReferenceTable(context);

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= CATEGORY_COUNT; i++)
            if (isFullReference || getPref(i))
            {
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, Diacritic.DAKUTEN, false)));
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, Diacritic.HANDAKUTEN, false)));
            }

        return table;
    }

    public ReferenceTable getMainDigraphsReferenceTable(Context context)
    {
        ReferenceTable table = new ReferenceTable(context);

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= CATEGORY_COUNT; i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, Diacritic.NO_DIACRITIC, true)));

        return table;
    }

    public ReferenceTable getDiacriticDigraphsReferenceTable(Context context)
    {
        ReferenceTable table = new ReferenceTable(context);

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= CATEGORY_COUNT; i++)
            if (isFullReference || getPref(i))
            {
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, Diacritic.DAKUTEN, true)));
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, Diacritic.HANDAKUTEN, true)));
            }

        return table;
    }

    public LinearLayout getSelectionScreen(Context context)
    {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 1; i <= CATEGORY_COUNT; i++)
        {
            KanaSelectionItem item = new KanaSelectionItem(context);
            item.setTitle(getSetTitle(i));
            item.setContents(displayContents(i));
            item.setPrefId(getPrefId(i));
            layout.addView(item);
        }

        return layout;
    }
}
