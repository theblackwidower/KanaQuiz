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
import java.text.ParseException;
import java.util.ArrayList;

public abstract class QuestionManagement
{
    private boolean isInitialized = false;

    private int categoryCount = 0;

    private KanaQuestion[][][][] kanaSets = new KanaQuestion[][][][]{};

    private String[] prefIds = new String[]{};

    private String[] setTitles = new String[]{};

    private String[] setNoDiacriticsTitles = new String[]{};

    private int getCategoryCount()
    {
        return categoryCount;
    }

    private KanaQuestion[] getKanaSet(int number, Diacritic diacritic, boolean isDigraphs) //TODO: Clean this up
    {
        try
        {
            return kanaSets[number - 1][diacritic.ordinal()][isDigraphs ? 1 : 0];
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
            return prefIds[number - 1];
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
            return OptionsControl.getBoolean(R.string.prefid_diacritics) ? setTitles[number - 1] :
                    setNoDiacriticsTitles[number - 1];
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            return null;
        }
    }

    protected static void parseXml(XmlResourceParser xrp, Resources resources, QuestionManagement singletonObject)
    {
        if (!singletonObject.isInitialized)
        {
            ArrayList<KanaQuestion[][][]> kanaSetList = new ArrayList<>();
            ArrayList<String> prefIdList = new ArrayList<>();
            ArrayList<String> setTitleList = new ArrayList<>();
            ArrayList<String> setNoDiacriticsTitleList = new ArrayList<>();

            try
            {
                for (int eventType = xrp.getEventType(); eventType != XmlPullParser.END_DOCUMENT;
                        eventType = xrp.next())
                {
                    if (eventType == XmlPullParser.START_TAG && xrp.getName().equalsIgnoreCase("KanaSet"))
                    {
                        XmlParser.parseXmlKanaSet(xrp, resources, kanaSetList, prefIdList, setTitleList,
                                setNoDiacriticsTitleList);
                    }
                }

                singletonObject.categoryCount = kanaSetList.size();

                singletonObject.kanaSets = new KanaQuestion[singletonObject.categoryCount][][][];
                kanaSetList.toArray(singletonObject.kanaSets);
                singletonObject.prefIds = new String[singletonObject.categoryCount];
                prefIdList.toArray(singletonObject.prefIds);
                singletonObject.setTitles = new String[singletonObject.categoryCount];
                setTitleList.toArray(singletonObject.setTitles);
                singletonObject.setNoDiacriticsTitles = new String[singletonObject.categoryCount];
                setNoDiacriticsTitleList.toArray(singletonObject.setNoDiacriticsTitles);

                singletonObject.isInitialized = true;
            }
            catch (XmlPullParserException | IOException | ParseException ex)
            {
            }
        }
    }

    public static void initialize(Context context)
    {
        Hiragana.initialize(context);
        Katakana.initialize(context);
    }

    public static KanaQuestionBank getFullQuestionBank()
    {
        KanaQuestionBank bank = new KanaQuestionBank();
        Hiragana.QUESTIONS.buildQuestionBank(bank);
        Katakana.QUESTIONS.buildQuestionBank(bank);
        return bank;
    }

    private boolean getPref(int number)
    {
        return OptionsControl.getBoolean(getPrefId(number));
    }

    public KanaQuestionBank getQuestionBank()
    {
        KanaQuestionBank questionBank = new KanaQuestionBank();
        buildQuestionBank(questionBank);
        return questionBank;
    }

    public void buildQuestionBank(KanaQuestionBank questionBank)
    {
        boolean isDigraphs = OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9);
        boolean isDiacritics = OptionsControl.getBoolean(R.string.prefid_diacritics);

        for (int i = 1; i <= getCategoryCount(); i++)
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
    }

    public boolean anySelected()
    {
        for (int i = 1; i <= getCategoryCount(); i++)
            if (getPref(i))
                return true;

        return false;
    }

    public boolean diacriticsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_diacritics))
            for (int i = 1; i <= getCategoryCount(); i++)
                if (getPref(i) && (getKanaSet(i, Diacritic.DAKUTEN, false) != null ||
                        getKanaSet(i, Diacritic.HANDAKUTEN, false) != null))
                    return true;

        return false;
    }

    public boolean digraphsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9))
            for (int i = 1; i <= getCategoryCount(); i++)
                if (getPref(i) && (getKanaSet(i, Diacritic.NO_DIACRITIC, true) != null))
                    return true;

        return false;
    }

    public boolean diacriticDigraphsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_diacritics) &&
                OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9))
            for (int i = 1; i <= getCategoryCount(); i++)
                if (getPref(i) && (getKanaSet(i, Diacritic.DAKUTEN, true) != null ||
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

        for (int i = 1; i <= getCategoryCount(); i++)
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

        for (int i = 1; i <= getCategoryCount(); i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, Diacritic.NO_DIACRITIC, true)));

        return table;
    }

    public ReferenceTable getDiacriticDigraphsReferenceTable(Context context)
    {
        ReferenceTable table = new ReferenceTable(context);

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= getCategoryCount(); i++)
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

        for (int i = 1; i <= getCategoryCount(); i++)
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
