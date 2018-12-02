package com.noprestige.kanaquiz.questions;

import android.content.Context;
import android.content.res.Resources;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuestionManagement
{
    public static QuestionManagement HIRAGANA;
    public static QuestionManagement KATAKANA;
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
        final String digraphs;

        SetCode(int number, Diacritic diacritic, String digraphs)
        {
            this.number = number;
            this.diacritic = diacritic;
            this.digraphs = digraphs;
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
                    if ((digraphs == null) && (o.digraphs != null))
                        returnValue = 1;
                    else if ((digraphs != null) && (o.digraphs == null))
                        returnValue = -1;
                    else if ((digraphs != null) && (o.digraphs != null))
                        returnValue = digraphs.compareTo(o.digraphs);
                }
            }
            return returnValue;
        }
    }

    private Question[] getQuestionSet(int number, Diacritic diacritic, String digraphs)
    {
        return questionSets.get(new SetCode(number, diacritic, digraphs));
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
        HIRAGANA = new QuestionManagement(R.xml.hiragana, context.getResources());
        KATAKANA = new QuestionManagement(R.xml.katakana, context.getResources());
        VOCABULARY = new QuestionManagement(R.xml.vocabulary, context.getResources());

        if (questionBank != null)
            currentQuestionBackup = questionBank.getCurrentQuestionKey();

        prefRecord = null;
        questionBank = null;
    }

    private static QuestionBank questionBank;
    private static boolean[] prefRecord;
    private static String currentQuestionBackup;

    public static void refreshStaticQuestionBank()
    {
        if ((prefRecord == null) || (questionBank == null))
        {
            prefRecord = getCurrentPrefRecord();
            questionBank = getFullQuestionBank();
            if ((currentQuestionBackup == null) || !questionBank.loadQuestion(currentQuestionBackup))
                questionBank.newQuestion();
            currentQuestionBackup = null;
        }
        else
        {
            boolean[] currentPrefRecord = getCurrentPrefRecord();

            if (!Arrays.equals(prefRecord, currentPrefRecord))
            {
                prefRecord = currentPrefRecord;
                questionBank = getFullQuestionBank();
                questionBank.newQuestion();
            }
        }
    }

    private static boolean[] getCurrentPrefRecord()
    {
        boolean[] currentPrefRecord =
                new boolean[HIRAGANA.getCategoryCount() + KATAKANA.getCategoryCount() + VOCABULARY.getCategoryCount() +
                        2];

        currentPrefRecord[0] = OptionsControl.getBoolean(R.string.prefid_digraphs);
        currentPrefRecord[1] = OptionsControl.getBoolean(R.string.prefid_diacritics);

        int i = 2;
        for (int j = 1; j <= HIRAGANA.getCategoryCount(); j++)
        {
            currentPrefRecord[i] = HIRAGANA.getPref(j);
            i++;
        }
        for (int j = 1; j <= KATAKANA.getCategoryCount(); j++)
        {
            currentPrefRecord[i] = KATAKANA.getPref(j);
            i++;
        }
        for (int j = 1; j <= VOCABULARY.getCategoryCount(); j++)
        {
            currentPrefRecord[i] = VOCABULARY.getPref(j);
            i++;
        }
        return currentPrefRecord;
    }

    public static QuestionBank getStaticQuestionBank()
    {
        return questionBank;
    }

    public static QuestionBank getFullQuestionBank()
    {
        QuestionBank bank = new QuestionBank();
        HIRAGANA.buildQuestionBank(bank);
        KATAKANA.buildQuestionBank(bank);
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
                questionBank.addQuestions(getQuestionSet(i, Diacritic.NO_DIACRITIC, null));
                if (isDiacritics)
                {
                    questionBank.addQuestions(getQuestionSet(i, Diacritic.DAKUTEN, null));
                    questionBank.addQuestions(getQuestionSet(i, Diacritic.HANDAKUTEN, null));
                }
                if (isDigraphs)
                {
                    questionBank.addQuestions(getQuestionSet(i, Diacritic.NO_DIACRITIC, getPrefId(9)));
                    if (isDiacritics)
                    {
                        questionBank.addQuestions(getQuestionSet(i, Diacritic.DAKUTEN, getPrefId(9)));
                        questionBank.addQuestions(getQuestionSet(i, Diacritic.HANDAKUTEN, getPrefId(9)));
                    }
                }
                questionBank.addQuestions(getQuestionSet(i, Diacritic.CONSONANT, null));
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
                if (getPref(i) && ((getQuestionSet(i, Diacritic.DAKUTEN, null) != null) ||
                        (getQuestionSet(i, Diacritic.HANDAKUTEN, null) != null)))
                    return true;

        return false;
    }

    public boolean digraphsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9))
            for (int i = 1; i <= getCategoryCount(); i++)
                if (getPref(i) && (getQuestionSet(i, Diacritic.NO_DIACRITIC, getPrefId(9)) != null))
                    return true;

        return false;
    }

    public boolean diacriticDigraphsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_diacritics) &&
                OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9))
            for (int i = 1; i <= getCategoryCount(); i++)
                if (getPref(i) && ((getQuestionSet(i, Diacritic.DAKUTEN, getPrefId(9)) != null) ||
                        (getQuestionSet(i, Diacritic.HANDAKUTEN, getPrefId(9)) != null)))
                    return true;

        return false;
    }

    private String getQuestionSetDisplay(int setNumber, Diacritic diacritic)
    {
        StringBuilder returnValue = new StringBuilder();
        Question[] questionSet = getQuestionSet(setNumber, diacritic, null);
        if (questionSet != null)
            for (Question question : questionSet)
                if (question.getClass().equals(KanaQuestion.class))
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
                table.addView(ReferenceCell.buildRow(context, getQuestionSet(i, Diacritic.NO_DIACRITIC, null)));

        if (isFullReference || getPref(9))
            table.addView(ReferenceCell.buildSpecialRow(context, getQuestionSet(9, Diacritic.NO_DIACRITIC, null)));
        if (isFullReference || getPref(8)) //fits gojūon ordering
            table.addView(ReferenceCell.buildRow(context, getQuestionSet(8, Diacritic.NO_DIACRITIC, null)));
        if (isFullReference || getPref(10))
        {
            table.addView(ReferenceCell.buildSpecialRow(context, getQuestionSet(10, Diacritic.NO_DIACRITIC, null)));
            table.addView(ReferenceCell.buildSpecialRow(context, getQuestionSet(10, Diacritic.CONSONANT, null)));
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
                table.addView(ReferenceCell.buildRow(context, getQuestionSet(i, Diacritic.DAKUTEN, null)));
                table.addView(ReferenceCell.buildRow(context, getQuestionSet(i, Diacritic.HANDAKUTEN, null)));
            }

        return table;
    }

    public ReferenceTable getMainDigraphsReferenceTable(Context context)
    {
        ReferenceTable table = new ReferenceTable(context);

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= getCategoryCount(); i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getQuestionSet(i, Diacritic.NO_DIACRITIC, getPrefId(9))));

        return table;
    }

    public ReferenceTable getDiacriticDigraphsReferenceTable(Context context)
    {
        ReferenceTable table = new ReferenceTable(context);

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= getCategoryCount(); i++)
            if (isFullReference || getPref(i))
            {
                table.addView(ReferenceCell.buildRow(context, getQuestionSet(i, Diacritic.DAKUTEN, getPrefId(9))));
                table.addView(ReferenceCell.buildRow(context, getQuestionSet(i, Diacritic.HANDAKUTEN, getPrefId(9))));
            }

        return table;
    }

    public View getVocabReferenceTable(Context context, int setNumber)
    {
        FlowLayout layout = new FlowLayout(context);
        layout.setGravity(Gravity.FILL);

        Question[] questionSet = getQuestionSet(setNumber, Diacritic.NO_DIACRITIC, null);

        int padding = context.getResources().getDimensionPixelSize(R.dimen.vocabReferenceCellHorizontalPadding);

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
        layout.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 1; i <= getCategoryCount(); i++)
        {
            QuestionSelectionItem item = new QuestionSelectionItem(context);
            item.setTitle(getSetTitle(i));
            item.setContents(displayContents(i));
            item.setPrefId(getPrefId(i));
            layout.addView(item);
        }

        return layout;
    }
}
