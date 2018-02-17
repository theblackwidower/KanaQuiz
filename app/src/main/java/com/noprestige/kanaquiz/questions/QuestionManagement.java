package com.noprestige.kanaquiz.questions;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.KanaSelectionItem;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.reference.ReferenceCell;
import com.noprestige.kanaquiz.reference.ReferenceTable;

public abstract class QuestionManagement
{
    private static final int CATEGORY_COUNT = 10;

    abstract KanaQuestion[] getKanaSet(int number, Diacritic diacritic, boolean isDigraphs);

    abstract int getPrefId(int number);

    private int getSetTitle(int number)
    {
        switch (number)
        {
            case 1:
                return R.string.set_1_title;
            case 2:
                return OptionsControl.getBoolean(R.string.prefid_diacritics) ?
                        R.string.set_2_title : R.string.set_2_no_diacritics_title;
            case 3:
                return OptionsControl.getBoolean(R.string.prefid_diacritics) ?
                        R.string.set_3_title : R.string.set_3_no_diacritics_title;
            case 4:
                return OptionsControl.getBoolean(R.string.prefid_diacritics) ?
                        R.string.set_4_title : R.string.set_4_no_diacritics_title;
            case 5:
                return R.string.set_5_title;
            case 6:
                return OptionsControl.getBoolean(R.string.prefid_diacritics) ?
                        R.string.set_6_title : R.string.set_6_no_diacritics_title;
            case 7:
                return R.string.set_7_title;
            case 8:
                return R.string.set_8_title;
            case 9:
                return R.string.set_9_title;
            case 10:
                return R.string.set_10_title;
            default:
                return 0;
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
            item.setTitle(context.getResources().getString(getSetTitle(i),
                    context.getResources().getString(R.string.set)));
            item.setContents(displayContents(i));
            item.setPrefId(getPrefId(i));
            layout.addView(item);
        }

        return layout;
    }
}
