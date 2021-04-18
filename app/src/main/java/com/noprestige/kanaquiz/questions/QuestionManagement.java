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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuestionManagement
{
    private static QuestionManagement HIRAGANA;
    private static QuestionManagement KATAKANA;
    private static QuestionManagement[] KANJI_FILES;
    private static String[] KANJI_TITLES;
    private static QuestionManagement VOCABULARY;

    private final int categoryCount;

    private final Map<SetCode, Question[]> questionSets;

    private final String[] prefIds;

    private final String[] setTitles;

    private final String[] setNoDiacriticsTitles;

    public int getCategoryCount()
    {
        return categoryCount;
    }

    enum Diacritic
    {
        NO_DIACRITIC,
        DAKUTEN,
        HANDAKUTEN,
        CONSONANT
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

    public static QuestionManagement getHiragana()
    {
        return HIRAGANA;
    }

    public static QuestionManagement getKatakana()
    {
        return KATAKANA;
    }

    public static int getKanjiFileCount()
    {
        return KANJI_FILES.length;
    }

    public static QuestionManagement getKanji(int index)
    {
        return KANJI_FILES[index];
    }

    public static String getKanjiTitle(int index)
    {
        return KANJI_TITLES[index];
    }

    public static QuestionManagement getVocabulary()
    {
        return VOCABULARY;
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

    QuestionManagement(int xmlRefId, Resources resources)
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

        OptionsControl.setQuestionSetDefaults(prefIds);
    }

    public static void initialize(Context context)
    {
        HIRAGANA = new QuestionManagement(R.xml.hiragana, context.getResources());
        KATAKANA = new QuestionManagement(R.xml.katakana, context.getResources());

        List<QuestionManagement> fileSetList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        XmlParser.parseXmlFileSetDocument(R.xml.kanji, context.getResources(), fileSetList, titleList);

        KANJI_FILES = fileSetList.toArray(new QuestionManagement[0]);
        KANJI_TITLES = titleList.toArray(new String[0]);

        VOCABULARY = new QuestionManagement(R.xml.vocabulary, context.getResources());

        //TODO: Find way to preserve previous questions record
        if (questionBank != null)
            currentQuestionBackup = questionBank.getCurrentQuestionKey();

        prefRecord = null;
        questionBank = null;
    }

    private static QuestionBank questionBank;
    private static boolean[][] prefRecord;
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
            boolean[][] currentPrefRecord = getCurrentPrefRecord();

            //TODO: Add something to handle repetition control changes so we can update the previousQuestion record

            if (!comparePrefRecord(currentPrefRecord))
            {
                prefRecord = currentPrefRecord;
                questionBank = getFullQuestionBank();
                questionBank.newQuestion();
            }
        }
    }

    private static boolean[][] getCurrentPrefRecord()
    {
        int prefCount = HIRAGANA.getCategoryCount() + KATAKANA.getCategoryCount() + VOCABULARY.getCategoryCount() + 2;
        for (QuestionManagement kanjiFile : KANJI_FILES)
            prefCount += kanjiFile.getCategoryCount();
        boolean[][] currentPrefRecord = new boolean[prefCount][];

        currentPrefRecord[0] = new boolean[]{OptionsControl.getBoolean(R.string.prefid_digraphs)};
        currentPrefRecord[1] = new boolean[]{OptionsControl.getBoolean(R.string.prefid_diacritics)};

        int i = 2;
        for (int j = 1; j <= HIRAGANA.getCategoryCount(); j++)
        {
            currentPrefRecord[i] = HIRAGANA.getPrefRecord(j);
            i++;
        }
        for (int j = 1; j <= KATAKANA.getCategoryCount(); j++)
        {
            currentPrefRecord[i] = KATAKANA.getPrefRecord(j);
            i++;
        }
        for (int j = 1; j <= VOCABULARY.getCategoryCount(); j++)
        {
            currentPrefRecord[i] = VOCABULARY.getPrefRecord(j);
            i++;
        }
        for (QuestionManagement kanjiFile : KANJI_FILES)
            for (int j = 1; j <= kanjiFile.getCategoryCount(); j++)
            {
                currentPrefRecord[i] = kanjiFile.getPrefRecord(j);
                i++;
            }
        return currentPrefRecord;
    }

    private boolean[] getPrefRecord(int number)
    {
        Boolean[] objectArray = getAllPrefs(number).values().toArray(new Boolean[0]);
        boolean[] returnValue = new boolean[objectArray.length];
        for (int i = 0; i < objectArray.length; i++)
            returnValue[i] = objectArray[i];
        return returnValue;
    }

    static private boolean comparePrefRecord(boolean[][] currentPrefRecord)
    {
        if (prefRecord.length != currentPrefRecord.length)
            return false;
        else
            for (int i = 0; i < prefRecord.length; i++)
                if (!Arrays.equals(prefRecord[i], currentPrefRecord[i]))
                    return false;
        return true;
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
        for (QuestionManagement kanjiFile : KANJI_FILES)
            kanjiFile.buildQuestionBank(bank);
        VOCABULARY.buildQuestionBank(bank);
        return bank;
    }

    public Boolean getPref(int number)
    {
        Boolean returnValue = null;
        Map<String, Boolean> prefs = getAllPrefs(number);
        for (Boolean currentValue : prefs.values())
            if (returnValue == null)
                returnValue = currentValue;
            else if (!returnValue.equals(currentValue))
                return null;
        return returnValue;
    }

    public Map<String, Boolean> getAllPrefs(int number)
    {
        String prefStart = getPrefId(number);
        Map<String, Boolean> returnValue = new HashMap<>();
        if (OptionsControl.exists(prefStart))
            returnValue.put("all", OptionsControl.getQuestionSetBool(prefStart));
        else
            for (Map.Entry<SetCode, Question[]> set : questionSets.entrySet())
                if ((set.getKey().number == number) && (set.getKey().digraphs == null))
                    for (Question question : set.getValue())
                    {
                        String key = question.getDatabaseKey();
                        String fullPrefId = prefStart + OptionsControl.SUBPREFERENCE_DELIMITER + key;
                        returnValue.put(key, OptionsControl.getQuestionSetBool(fullPrefId));
                    }
        return returnValue;
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

        for (Map.Entry<SetCode, Question[]> set : questionSets.entrySet())
        {
            if ((isDiacritics || (set.getKey().diacritic == Diacritic.NO_DIACRITIC) ||
                    (set.getKey().diacritic == Diacritic.CONSONANT)) && (isDigraphs || (set.getKey().digraphs == null)))
            {
                Boolean pref = getPref(set.getKey().number);
                if (pref == null)
                {
                    ArrayList<Question> tempBank = new ArrayList<>(set.getValue().length);
                    for (Question question : set.getValue())
                        if (OptionsControl.getBoolean(
                                getPrefId(set.getKey().number) + OptionsControl.SUBPREFERENCE_DELIMITER +
                                        question.getDatabaseKey()))
                            questionBank.addQuestion(question);
                    questionBank.addQuestions(tempBank.toArray(new Question[]{}));
                }
                else if (pref)
                    questionBank.addQuestions(set.getValue());
            }
        }
    }

    public boolean anySelected()
    {
        for (int i = 1; i <= getCategoryCount(); i++)
            if (getPref(i))
                return true;

        return false;
    }

    public boolean anyMainKanaSelected()
    {
        for (int i = 1; i <= 10; i++)
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

    public boolean extendedKatakanaSelected()
    {
        for (int i = 11; i <= getCategoryCount(); i++)
            if (getPref(i))
                return true;

        return false;
    }

    private String getQuestionSetDisplay(int setNumber, Diacritic diacritic)
    {
        StringBuilder returnValue = new StringBuilder();
        Question[] questionSet = getQuestionSet(setNumber, diacritic, null);
        if (questionSet != null)
            for (Question question : questionSet)
                if (question.getClass().equals(KanaQuestion.class) || question.getClass().equals(KanjiQuestion.class))
                {
                    returnValue.append(question.getQuestionText());
                    if (questionSet.length <= 10)
                        returnValue.append('\u00A0');
                    else
                        returnValue.append(' ');
                }
                else if (question.getClass().equals(WordQuestion.class))
                {
                    returnValue.append(question.fetchCorrectAnswer().replace(' ', '\u00A0'));
                    returnValue.append(", ");
                }
        if (returnValue.length() > 1)
            returnValue.setCharAt(returnValue.length() - 1, ' ');
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

    public View getExtendedKatakanaReferenceTable(Context context)
    {
        LinearLayout fullLayout = new LinearLayout(context);
        fullLayout.setOrientation(LinearLayout.VERTICAL);
        fullLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 11; i <= getCategoryCount(); i++)
            if (isFullReference || getPref(i))
            {
                fullLayout.addView(
                        ReferenceCell.buildHeader(context, getSetTitle(i).toString().split("\\s*[()]\\s*")[1]));
                FlowLayout sectionLayout = new FlowLayout(context);
                sectionLayout.setGravity(Gravity.FILL);
                sectionLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                Question[] questionSet = getQuestionSet(i, Diacritic.NO_DIACRITIC, null);

                for (Question question : questionSet)
                    sectionLayout.addView(question.generateReference(context));

                fullLayout.addView(sectionLayout);
            }

        return fullLayout;
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
                Question[] questionSet = getQuestionSet(i, Diacritic.NO_DIACRITIC, null);
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
            ArrayList<String> questions = new ArrayList();
            for (Map.Entry<SetCode, Question[]> set : questionSets.entrySet())
                if ((set.getKey().number == i) && (set.getKey().digraphs == null))
                    for (Question question : set.getValue())
                        questions.add(question.getDatabaseKey());

            item.setQuestions(questions.toArray(new String[]{}));
            layout.addView(item);
        }
    }
}
