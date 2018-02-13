package com.noprestige.kanaquiz.questions;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.KanaSelectionItem;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.reference.ReferenceCell;

public abstract class QuestionManagement
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
                if (getPref(i))
                    if (getKanaSet(i, Diacritic.DAKUTEN, false) != null ||
                            getKanaSet(i, Diacritic.HANDAKUTEN, false) != null)
                        return true;

        return false;
    }

    public boolean digraphsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9))
            for (int i = 1; i <= CATEGORY_COUNT; i++)
                if (getPref(i))
                    if (getKanaSet(i, Diacritic.NO_DIACRITIC, true) != null)
                        return true;

        return false;
    }

    public boolean diacriticDigraphsSelected()
    {
        if (OptionsControl.getBoolean(R.string.prefid_diacritics) &&
                OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9))
            for (int i = 1; i <= CATEGORY_COUNT; i++)
                if (getPref(i))
                    if (getKanaSet(i, Diacritic.DAKUTEN, true) != null ||
                            getKanaSet(i, Diacritic.HANDAKUTEN, true) != null)
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

    public TableLayout getMainReferenceTable(Context context)
    {
        TableLayout table = new TableLayout(context);
        table.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

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

    public TableLayout getDiacriticReferenceTable(Context context)
    {
        TableLayout table = new TableLayout(context);
        table.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 2; i <= 4; i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, Diacritic.DAKUTEN, false)));

        if (isFullReference || getPref(6))
        {
            table.addView(ReferenceCell.buildRow(context, getKanaSet(6, Diacritic.DAKUTEN, false)));
            table.addView(ReferenceCell.buildRow(context, getKanaSet(6, Diacritic.HANDAKUTEN, false)));
        }

        return table;
    }

    public TableLayout getMainDigraphsReferenceTable(Context context)
    {
        TableLayout table = new TableLayout(context);
        table.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 2; i <= 8; i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, Diacritic.NO_DIACRITIC, true)));

        return table;
    }

    public TableLayout getDiacriticDigraphsReferenceTable(Context context)
    {
        TableLayout table = new TableLayout(context);
        table.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 2; i <= 4; i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, Diacritic.DAKUTEN, true)));

        if (isFullReference || getPref(6))
        {
            table.addView(ReferenceCell.buildRow(context, getKanaSet(6, Diacritic.DAKUTEN, true)));
            table.addView(ReferenceCell.buildRow(context, getKanaSet(6, Diacritic.HANDAKUTEN, true)));
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
