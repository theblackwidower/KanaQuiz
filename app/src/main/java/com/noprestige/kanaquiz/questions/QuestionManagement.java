package com.noprestige.kanaquiz.questions;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.options.QuestionSelectionItem;
import com.noprestige.kanaquiz.reference.ReferenceCell;
import com.noprestige.kanaquiz.reference.ReferenceTable;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuestionManagement
{
    public static QuestionManagement HIRAGANA;
    public static QuestionManagement KATAKANA;
    public static QuestionManagement KANJI_1;
    public static QuestionManagement KANJI_2;
    public static QuestionManagement KANJI_3;
    public static QuestionManagement VOCABULARY;

    private final int categoryCount;

    private final Map<SetCode, Question[]> questionSets;

    private final String[] prefIds;

    private final String[] setTitles;

    private final String[] setNoDiacriticsTitles;

    public int getCategoryCount()
    {
        return categoryCount;
    }

    static class SetCode implements Comparable<SetCode>
    {
        final int number;
        final Diacritic diacritic;
        final boolean isDigraphs;

        SetCode(int number, Diacritic diacritic, boolean isDigraphs)
        {
            this.number = number;
            this.diacritic = diacritic;
            this.isDigraphs = isDigraphs;
        }

        @Override
        public int compareTo(SetCode o)
        {
            int returnValue = number - o.number;
            if (returnValue == 0)
            {
                returnValue = diacritic.ordinal() - o.diacritic.ordinal();
                if (returnValue == 0)
                {
                    if (isDigraphs && !o.isDigraphs)
                        returnValue = 1;
                    else if (!isDigraphs && o.isDigraphs)
                        returnValue = -1;
                }
            }
            return returnValue;
        }
    }

    private Question[] getQuestionSet(int number, Diacritic diacritic, boolean isDigraphs)
    {
        return questionSets.get(new SetCode(number, diacritic, isDigraphs));
    }

    public String getPrefId(int number)
    {
        return prefIds[number - 1];
    }

    public CharSequence getSetTitle(int number)
    {
        return getSetTitle(number, OptionsControl.getBoolean(R.string.prefid_diacritics));
    }

    private CharSequence getSetTitle(int number, boolean isDiacriticsActive)
    {
        return (isDiacriticsActive || (setNoDiacriticsTitles[number - 1] == null)) ? setTitles[number - 1] :
                setNoDiacriticsTitles[number - 1];
    }

    public QuestionManagement(int xmlRefId, Resources resources)
    {
        Map<SetCode, Question[]> questionSetList = new TreeMap<>();
        List<String> prefIdList = new ArrayList<>();
        List<String> setTitleList = new ArrayList<>();
        List<String> setNoDiacriticsTitleList = new ArrayList<>();

        XmlParser.parseXmlDocument(xmlRefId, resources, questionSetList, prefIdList, setTitleList,
                setNoDiacriticsTitleList);

        categoryCount = prefIdList.size();

        questionSets = questionSetList;
        prefIds = prefIdList.toArray(new String[0]);
        setTitles = setTitleList.toArray(new String[0]);
        setNoDiacriticsTitles = setNoDiacriticsTitleList.toArray(new String[0]);
    }

    public static void initialize(Context context)
    {
        if (HIRAGANA == null)
            HIRAGANA = new QuestionManagement(R.xml.hiragana, context.getApplicationContext().getResources());

        if (KATAKANA == null)
            KATAKANA = new QuestionManagement(R.xml.katakana, context.getApplicationContext().getResources());

        if (KANJI_1 == null)
            KANJI_1 = new QuestionManagement(R.xml.kanji_1, context.getApplicationContext().getResources());

        if (KANJI_2 == null)
            KANJI_2 = new QuestionManagement(R.xml.kanji_2, context.getApplicationContext().getResources());

        if (KANJI_3 == null)
            KANJI_3 = new QuestionManagement(R.xml.kanji_3, context.getApplicationContext().getResources());

        if (VOCABULARY == null)
            VOCABULARY = new QuestionManagement(R.xml.vocabulary, context.getApplicationContext().getResources());
    }

    public static QuestionBank getFullQuestionBank()
    {
        QuestionBank bank = new QuestionBank();
        HIRAGANA.buildQuestionBank(bank);
        KATAKANA.buildQuestionBank(bank);
        KANJI_1.buildQuestionBank(bank);
        KANJI_2.buildQuestionBank(bank);
        KANJI_3.buildQuestionBank(bank);
        VOCABULARY.buildQuestionBank(bank);
        return bank;
    }

    public boolean getPref(int number)
    {
        return OptionsControl.getBoolean(getPrefId(number));
    }

    public QuestionBank getQuestionBank()
    {
        QuestionBank questionBank = new QuestionBank();
        buildQuestionBank(questionBank);
        return questionBank;
    }

    public void buildQuestionBank(QuestionBank questionBank)
    {
        boolean isDigraphs = OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9);
        boolean isDiacritics = OptionsControl.getBoolean(R.string.prefid_diacritics);

        for (int i = 1; i <= getCategoryCount(); i++)
            if (getPref(i))
            {
                questionBank.addQuestions(getQuestionSet(i, Diacritic.NO_DIACRITIC, false));
                if (isDiacritics)
                {
                    questionBank.addQuestions(getQuestionSet(i, Diacritic.DAKUTEN, false));
                    questionBank.addQuestions(getQuestionSet(i, Diacritic.HANDAKUTEN, false));
                }
                if (isDigraphs)
                {
                    questionBank.addQuestions(getQuestionSet(i, Diacritic.NO_DIACRITIC, true));
                    if (isDiacritics)
                    {
                        questionBank.addQuestions(getQuestionSet(i, Diacritic.DAKUTEN, true));
                        questionBank.addQuestions(getQuestionSet(i, Diacritic.HANDAKUTEN, true));
                    }
                }
                questionBank.addQuestions(getQuestionSet(i, Diacritic.CONSONANT, false));
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
                if (getPref(i) && ((getQuestionSet(i, Diacritic.DAKUTEN, false) != null) ||
                        (getQuestionSet(i, Diacritic.HANDAKUTEN, false) != null)))
                    return true;

        return false;
    }

    public boolean digraphsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9))
            for (int i = 1; i <= getCategoryCount(); i++)
                if (getPref(i) && (getQuestionSet(i, Diacritic.NO_DIACRITIC, true) != null))
                    return true;

        return false;
    }

    public boolean diacriticDigraphsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_diacritics) &&
                OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9))
            for (int i = 1; i <= getCategoryCount(); i++)
                if (getPref(i) && ((getQuestionSet(i, Diacritic.DAKUTEN, true) != null) ||
                        (getQuestionSet(i, Diacritic.HANDAKUTEN, true) != null)))
                    return true;

        return false;
    }

    private String getQuestionSetDisplay(int setNumber, Diacritic diacritic)
    {
        StringBuilder returnValue = new StringBuilder();
        Question[] questionSet = getQuestionSet(setNumber, diacritic, false);
        if (questionSet != null)
            for (Question question : questionSet)
                if (question.getClass().equals(KanaQuestion.class) || question.getClass().equals(KanjiQuestion.class))
                {
                    returnValue.append(question.getQuestionText());
                    returnValue.append(' ');
                }
                else if (question.getClass().equals(WordQuestion.class))
                {
                    returnValue.append(question.fetchCorrectAnswer().replace(' ', '\u00A0'));
                    returnValue.append(", ");
                }
        return returnValue.toString();
    }

    private CharSequence displayContents(int setNumber)
    {
        boolean isDiacritics = OptionsControl.getBoolean(R.string.prefid_diacritics);

        StringBuilder returnValue = new StringBuilder(getQuestionSetDisplay(setNumber, Diacritic.NO_DIACRITIC));

        if (isDiacritics)
        {
            returnValue.append(getQuestionSetDisplay(setNumber, Diacritic.DAKUTEN));
            returnValue.append(getQuestionSetDisplay(setNumber, Diacritic.HANDAKUTEN));
        }

        returnValue.append(getQuestionSetDisplay(setNumber, Diacritic.CONSONANT));

        if (returnValue.codePointAt(returnValue.length() - 2) == ',')
            returnValue.deleteCharAt(returnValue.length() - 2);

        return returnValue.toString();
    }

    public ReferenceTable getMainReferenceTable(Context context)
    {
        ReferenceTable table = new ReferenceTable(context);

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= 7; i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getQuestionSet(i, Diacritic.NO_DIACRITIC, false)));

        if (isFullReference || getPref(9))
            table.addView(ReferenceCell.buildSpecialRow(context, getQuestionSet(9, Diacritic.NO_DIACRITIC, false)));
        if (isFullReference || getPref(8)) //fits gojūon ordering
            table.addView(ReferenceCell.buildRow(context, getQuestionSet(8, Diacritic.NO_DIACRITIC, false)));
        if (isFullReference || getPref(10))
        {
            table.addView(ReferenceCell.buildSpecialRow(context, getQuestionSet(10, Diacritic.NO_DIACRITIC, false)));
            table.addView(ReferenceCell.buildSpecialRow(context, getQuestionSet(10, Diacritic.CONSONANT, false)));
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
                table.addView(ReferenceCell.buildRow(context, getQuestionSet(i, Diacritic.DAKUTEN, false)));
                table.addView(ReferenceCell.buildRow(context, getQuestionSet(i, Diacritic.HANDAKUTEN, false)));
            }

        return table;
    }

    public ReferenceTable getMainDigraphsReferenceTable(Context context)
    {
        ReferenceTable table = new ReferenceTable(context);

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= getCategoryCount(); i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getQuestionSet(i, Diacritic.NO_DIACRITIC, true)));

        return table;
    }

    public ReferenceTable getDiacriticDigraphsReferenceTable(Context context)
    {
        ReferenceTable table = new ReferenceTable(context);

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= getCategoryCount(); i++)
            if (isFullReference || getPref(i))
            {
                table.addView(ReferenceCell.buildRow(context, getQuestionSet(i, Diacritic.DAKUTEN, true)));
                table.addView(ReferenceCell.buildRow(context, getQuestionSet(i, Diacritic.HANDAKUTEN, true)));
            }

        return table;
    }

    public View getKanjiReferenceTable(Context context)
    {
        LinearLayout masterLayout = new LinearLayout(context);
        masterLayout.setOrientation(LinearLayout.VERTICAL);
        masterLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

        ReferenceTable currentTable = null;
        int currentSize = 0;

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= getCategoryCount(); i++)
            if (isFullReference || getPref(i))
            {
                Question[] questionSet = getQuestionSet(i, Diacritic.NO_DIACRITIC, false);
                if (questionSet.length != currentSize)
                {
                    currentSize = questionSet.length;
                    if (currentTable != null)
                        masterLayout.addView(currentTable);
                    currentTable = new ReferenceTable(context);
                }
                currentTable.addView(ReferenceCell.buildRow(context, questionSet));
            }
        masterLayout.addView(currentTable);

        return masterLayout;
    }

    public View getVocabReferenceTable(Context context, int setNumber)
    {
        FlowLayout layout = new FlowLayout(context);
        layout.setGravity(Gravity.FILL);

        Question[] questionSet = getQuestionSet(setNumber, Diacritic.NO_DIACRITIC, false);

        int padding = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, context.getResources().getDisplayMetrics());

        for (Question question : questionSet)
        {
            ReferenceCell cell = question.generateReference(context);
            cell.setPadding(padding, 0, padding, 0);
            layout.addView(cell);
        }

        return layout;
    }

    public LinearLayout getSelectionScreen(Context context)
    {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        populateSelectionScreen(layout);

        return layout;
    }

    public void populateSelectionScreen(LinearLayout layout)
    {
        for (int i = 1; i <= getCategoryCount(); i++)
        {
            QuestionSelectionItem item = new QuestionSelectionItem(layout.getContext());
            item.setTitle(getSetTitle(i));
            item.setContents(displayContents(i));
            item.setPrefId(getPrefId(i));
            layout.addView(item);
        }
    }
}
