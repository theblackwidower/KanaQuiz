package com.noprestige.kanaquiz;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import static com.noprestige.kanaquiz.Diacritic.CONSONANT;
import static com.noprestige.kanaquiz.Diacritic.DAKUTEN;
import static com.noprestige.kanaquiz.Diacritic.HANDAKUTEN;
import static com.noprestige.kanaquiz.Diacritic.NO_DIACRITIC;

abstract class QuestionManagement
{
    private static final int CATEGORY_COUNT = 10;

    private static final int[] SET_TITLES = {
            R.string.set_1_title, R.string.set_2_title,
            R.string.set_3_title, R.string.set_4_title,
            R.string.set_5_title, R.string.set_6_title,
            R.string.set_7_title, R.string.set_8_title,
            R.string.set_9_title, R.string.set_10_title};

    abstract KanaQuestion[] getKanaSet(int number, Diacritic diacritic, boolean isDigraphs);

    abstract int getPrefId(int number);

    private int getSetTitle(int number)
    {
        return SET_TITLES[number - 1];
    }

    private boolean getPref(int number)
    {
        return OptionsControl.getBoolean(getPrefId(number));
    }

    KanaQuestionBank getQuestionBank()
    {
        KanaQuestionBank questionBank = new KanaQuestionBank();

        boolean isDigraphs = OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9);
        boolean isDiacritics = OptionsControl.getBoolean(R.string.prefid_diacritics);

        if (getPref(1))
            questionBank.addQuestions(getKanaSet(1, NO_DIACRITIC, false));
        for (int i = 2; i <= 8; i++)
            if (getPref(i))
            {
                questionBank.addQuestions(getKanaSet(i, NO_DIACRITIC, false));
                if (isDigraphs)
                    questionBank.addQuestions(getKanaSet(i, NO_DIACRITIC, true));
            }

        if (isDiacritics)
        {
            for (int i = 2; i <= 4; i++)
                if (getPref(i))
                {
                    questionBank.addQuestions(getKanaSet(i, DAKUTEN, false));
                    if (isDigraphs)
                        questionBank.addQuestions(getKanaSet(i, DAKUTEN, true));
                }

            if (getPref(6))
            {
                questionBank.addQuestions(getKanaSet(6, DAKUTEN, false));
                questionBank.addQuestions(getKanaSet(6, HANDAKUTEN, false));
                if (isDigraphs)
                {
                    questionBank.addQuestions(getKanaSet(6, DAKUTEN, true));
                    questionBank.addQuestions(getKanaSet(6, HANDAKUTEN, true));
                }
            }
        }
        if (getPref(9))
            questionBank.addQuestions(getKanaSet(9, NO_DIACRITIC, false));
        if (getPref(10))
        {
            questionBank.addQuestions(getKanaSet(10, NO_DIACRITIC, false));
            questionBank.addQuestions(getKanaSet(10, CONSONANT, false));
        }

        return questionBank;
    }

    boolean anySelected()
    {
        for (int i = 1; i <= CATEGORY_COUNT; i++)
            if (getPref(i))
                return true;

        return false;
    }

    boolean diacriticsSelected()
    {
        return (OptionsControl.getBoolean(R.string.prefid_diacritics) &&
                (getPref(2) || getPref(3) || getPref(4) || getPref(6)));
    }

    boolean digraphsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9))
            for (int i = 2; i <= 8; i++)
                if (getPref(i))
                    return true;

        return false;
    }

    boolean diacriticDigraphsSelected()
    {
        return (OptionsControl.getBoolean(R.string.prefid_diacritics) &&
                OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9) &&
                (getPref(2) || getPref(3) || getPref(4) || getPref(6)));
    }

    private String getKanaSetDisplay(int setNumber)
    {
        return getKanaSetDisplay(setNumber, NO_DIACRITIC);
    }

    private String getKanaSetDisplay(int setNumber, Diacritic diacritic)
    {
        StringBuilder returnValue = new StringBuilder();
        for (KanaQuestion question : getKanaSet(setNumber, diacritic, false))
            returnValue.append(question.getKana() + " ");
        return returnValue.toString();
    }

    private String displayContents(int setNumber)
    {
        String returnValue = "";

        boolean isDiacritics = OptionsControl.getBoolean(R.string.prefid_diacritics);

        returnValue += getKanaSetDisplay(setNumber);

        if (isDiacritics && (setNumber >= 2 && setNumber <= 4 || setNumber == 6))
        {
            returnValue += getKanaSetDisplay(setNumber, DAKUTEN);

            if (setNumber == 6)
                returnValue += getKanaSetDisplay(setNumber, HANDAKUTEN);
        }

        if (setNumber == 10)
            returnValue += getKanaSetDisplay(setNumber, CONSONANT);

        return returnValue;
    }

    TableLayout getMainReferenceTable(Context context)
    {
        TableLayout table = new TableLayout(context);
        table.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= 7; i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, NO_DIACRITIC, false)));

        if (isFullReference || getPref(9))
            table.addView(ReferenceCell.buildSpecialRow(context, getKanaSet(9, NO_DIACRITIC, false)));
        if (isFullReference || getPref(8)) //fits gojūon ordering
            table.addView(ReferenceCell.buildRow(context, getKanaSet(8, NO_DIACRITIC, false)));
        if (isFullReference || getPref(10))
        {
            table.addView(ReferenceCell.buildSpecialRow(context, getKanaSet(10, NO_DIACRITIC, false)));
            table.addView(ReferenceCell.buildSpecialRow(context, getKanaSet(10, CONSONANT, false)));
        }

        return table;
    }

    TableLayout getDiacriticReferenceTable(Context context)
    {
        TableLayout table = new TableLayout(context);
        table.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 2; i <= 4; i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, DAKUTEN, false)));

        if (isFullReference || getPref(6))
        {
            table.addView(ReferenceCell.buildRow(context, getKanaSet(6, DAKUTEN, false)));
            table.addView(ReferenceCell.buildRow(context, getKanaSet(6, HANDAKUTEN, false)));
        }

        return table;
    }

    TableLayout getMainDigraphsReferenceTable(Context context)
    {
        TableLayout table = new TableLayout(context);
        table.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 2; i <= 8; i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, NO_DIACRITIC, true)));

        return table;
    }

    TableLayout getDiacriticDigraphsReferenceTable(Context context)
    {
        TableLayout table = new TableLayout(context);
        table.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 2; i <= 4; i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, DAKUTEN, true)));

        if (isFullReference || getPref(6))
        {
            table.addView(ReferenceCell.buildRow(context, getKanaSet(6, DAKUTEN, true)));
            table.addView(ReferenceCell.buildRow(context, getKanaSet(6, HANDAKUTEN, true)));
        }

        return table;
    }

    LinearLayout getSelectionScreen(Context context)
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
