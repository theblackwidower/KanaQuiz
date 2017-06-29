package com.noprestige.kanaquiz;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;

abstract class QuestionManagement
{
    protected KanaQuestion[] KANA_SET_1;
    protected KanaQuestion[] KANA_SET_2_BASE;
    protected KanaQuestion[] KANA_SET_2_DAKUTEN;
    protected KanaQuestion[] KANA_SET_2_BASE_DIGRAPHS;
    protected KanaQuestion[] KANA_SET_2_DAKUTEN_DIGRAPHS;
    protected KanaQuestion[] KANA_SET_3_BASE;
    protected KanaQuestion[] KANA_SET_3_DAKUTEN;
    protected KanaQuestion[] KANA_SET_3_BASE_DIGRAPHS;
    protected KanaQuestion[] KANA_SET_3_DAKUTEN_DIGRAPHS;
    protected KanaQuestion[] KANA_SET_4_BASE;
    protected KanaQuestion[] KANA_SET_4_DAKUTEN;
    protected KanaQuestion[] KANA_SET_4_BASE_DIGRAPHS;
    protected KanaQuestion[] KANA_SET_4_DAKUTEN_DIGRAPHS;
    protected KanaQuestion[] KANA_SET_5;
    protected KanaQuestion[] KANA_SET_5_DIGRAPHS;
    protected KanaQuestion[] KANA_SET_6_BASE;
    protected KanaQuestion[] KANA_SET_6_DAKUTEN;
    protected KanaQuestion[] KANA_SET_6_HANDAKETEN;
    protected KanaQuestion[] KANA_SET_6_BASE_DIGRAPHS;
    protected KanaQuestion[] KANA_SET_6_DAKUTEN_DIGRAPHS;
    protected KanaQuestion[] KANA_SET_6_HANDAKETEN_DIGRAPHS;
    protected KanaQuestion[] KANA_SET_7;
    protected KanaQuestion[] KANA_SET_7_DIGRAPHS;
    protected KanaQuestion[] KANA_SET_8;
    protected KanaQuestion[] KANA_SET_8_DIGRAPHS;
    protected KanaQuestion[] KANA_SET_9;
    protected KanaQuestion[] KANA_SET_10_W_GROUP;
    protected KanaQuestion[] KANA_SET_10_N_CONSONANT;

    protected int PREFID_1;
    protected int PREFID_2;
    protected int PREFID_3;
    protected int PREFID_4;
    protected int PREFID_5;
    protected int PREFID_6;
    protected int PREFID_7;
    protected int PREFID_8;
    protected int PREFID_9;
    protected int PREFID_10;

    KanaQuestionBank getQuestionBank()
    {
        KanaQuestionBank questionBank = new KanaQuestionBank();

        boolean isDigraphs = OptionsControl.getBoolean(R.string.prefid_digraphs) && OptionsControl.getBoolean(PREFID_9);
        boolean isDiacritics = OptionsControl.getBoolean(R.string.prefid_diacritics);

        if (OptionsControl.getBoolean(PREFID_1))
            questionBank.addQuestions(KANA_SET_1);
        if (OptionsControl.getBoolean(PREFID_2))
        {
            questionBank.addQuestions(KANA_SET_2_BASE);
            if (isDiacritics)
                questionBank.addQuestions(KANA_SET_2_DAKUTEN);
            if (isDigraphs)
                questionBank.addQuestions(KANA_SET_2_BASE_DIGRAPHS);
            if (isDigraphs && isDiacritics)
                questionBank.addQuestions(KANA_SET_2_DAKUTEN_DIGRAPHS);
        }
        if (OptionsControl.getBoolean(PREFID_3))
        {
            questionBank.addQuestions(KANA_SET_3_BASE);
            if (isDiacritics)
                questionBank.addQuestions(KANA_SET_3_DAKUTEN);
            if (isDigraphs)
                questionBank.addQuestions(KANA_SET_3_BASE_DIGRAPHS);
            if (isDigraphs && isDiacritics)
                questionBank.addQuestions(KANA_SET_3_DAKUTEN_DIGRAPHS);
        }
        if (OptionsControl.getBoolean(PREFID_4))
        {
            questionBank.addQuestions(KANA_SET_4_BASE);
            if (isDiacritics)
                questionBank.addQuestions(KANA_SET_4_DAKUTEN);
            if (isDigraphs)
                questionBank.addQuestions(KANA_SET_4_BASE_DIGRAPHS);
            if (isDigraphs && isDiacritics)
                questionBank.addQuestions(KANA_SET_4_DAKUTEN_DIGRAPHS);
        }
        if (OptionsControl.getBoolean(PREFID_5))
        {
            questionBank.addQuestions(KANA_SET_5);
            if (isDigraphs)
                questionBank.addQuestions(KANA_SET_5_DIGRAPHS);
        }
        if (OptionsControl.getBoolean(PREFID_6))
        {
            questionBank.addQuestions(KANA_SET_6_BASE);
            if (isDiacritics)
                questionBank.addQuestions(KANA_SET_6_DAKUTEN, KANA_SET_6_HANDAKETEN);
            if (isDigraphs)
                questionBank.addQuestions(KANA_SET_6_BASE_DIGRAPHS);
            if (isDigraphs && isDiacritics)
                questionBank.addQuestions(KANA_SET_6_DAKUTEN_DIGRAPHS, KANA_SET_6_HANDAKETEN_DIGRAPHS);

        }
        if (OptionsControl.getBoolean(PREFID_7))
        {
            questionBank.addQuestions(KANA_SET_7);
            if (isDigraphs)
                questionBank.addQuestions(KANA_SET_7_DIGRAPHS);
        }
        if (OptionsControl.getBoolean(PREFID_8))
        {
            questionBank.addQuestions(KANA_SET_8);
            if (isDigraphs)
                questionBank.addQuestions(KANA_SET_8_DIGRAPHS);
        }
        if (OptionsControl.getBoolean(PREFID_9))
            questionBank.addQuestions(KANA_SET_9);
        if (OptionsControl.getBoolean(PREFID_10))
            questionBank.addQuestions(KANA_SET_10_W_GROUP, KANA_SET_10_N_CONSONANT);

        return questionBank;
    }

    boolean anySelected()
    {
        return (OptionsControl.getBoolean(PREFID_1) ||
                OptionsControl.getBoolean(PREFID_2) ||
                OptionsControl.getBoolean(PREFID_3) ||
                OptionsControl.getBoolean(PREFID_4) ||
                OptionsControl.getBoolean(PREFID_5) ||
                OptionsControl.getBoolean(PREFID_6) ||
                OptionsControl.getBoolean(PREFID_7) ||
                OptionsControl.getBoolean(PREFID_8) ||
                OptionsControl.getBoolean(PREFID_9) ||
                OptionsControl.getBoolean(PREFID_10));
    }

    LinearLayout getReferenceTable(Context context)
    {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        TableLayout tableOne = new TableLayout(context);
        tableOne.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (OptionsControl.getBoolean(PREFID_1))
            tableOne.addView(ReferenceCell.buildRow(context, KANA_SET_1));
        if (OptionsControl.getBoolean(PREFID_2))
            tableOne.addView(ReferenceCell.buildRow(context, KANA_SET_2_BASE));
        if (OptionsControl.getBoolean(PREFID_3))
            tableOne.addView(ReferenceCell.buildRow(context, KANA_SET_3_BASE));
        if (OptionsControl.getBoolean(PREFID_4))
            tableOne.addView(ReferenceCell.buildRow(context, KANA_SET_4_BASE));
        if (OptionsControl.getBoolean(PREFID_5))
            tableOne.addView(ReferenceCell.buildRow(context, KANA_SET_5));
        if (OptionsControl.getBoolean(PREFID_6))
            tableOne.addView(ReferenceCell.buildRow(context, KANA_SET_6_BASE));
        if (OptionsControl.getBoolean(PREFID_7))
            tableOne.addView(ReferenceCell.buildRow(context, KANA_SET_7));
        if (OptionsControl.getBoolean(PREFID_8))
            tableOne.addView(ReferenceCell.buildRow(context, KANA_SET_8));
        if (OptionsControl.getBoolean(PREFID_9))
            tableOne.addView(ReferenceCell.buildSpecialRow(context, KANA_SET_9));
        if (OptionsControl.getBoolean(PREFID_10))
        {
            tableOne.addView(ReferenceCell.buildSpecialRow(context, KANA_SET_10_W_GROUP));
            tableOne.addView(ReferenceCell.buildSpecialRow(context, KANA_SET_10_N_CONSONANT));
        }

        layout.addView(tableOne);

        if (OptionsControl.getBoolean(R.string.prefid_diacritics))
        {
            TableLayout tableTwo = new TableLayout(context);
            tableTwo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            if (OptionsControl.getBoolean(PREFID_2))
                tableTwo.addView(ReferenceCell.buildRow(context, KANA_SET_2_DAKUTEN));
            if (OptionsControl.getBoolean(PREFID_3))
                tableTwo.addView(ReferenceCell.buildRow(context, KANA_SET_3_DAKUTEN));
            if (OptionsControl.getBoolean(PREFID_4))
                tableTwo.addView(ReferenceCell.buildRow(context, KANA_SET_4_DAKUTEN));
            if (OptionsControl.getBoolean(PREFID_6))
            {
                tableTwo.addView(ReferenceCell.buildRow(context, KANA_SET_6_DAKUTEN));
                tableTwo.addView(ReferenceCell.buildRow(context, KANA_SET_6_HANDAKETEN));
            }

            if (tableTwo.getChildCount() > 0)
                layout.addView(ReferenceCell.buildHeader(context, R.string.diacritics_title));

            layout.addView(tableTwo);
        }

        if (OptionsControl.getBoolean(R.string.prefid_digraphs) && OptionsControl.getBoolean(PREFID_9))
        {
            TableLayout tableThree = new TableLayout(context);
            tableThree.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            if (OptionsControl.getBoolean(PREFID_2))
                tableThree.addView(ReferenceCell.buildRow(context, KANA_SET_2_BASE_DIGRAPHS));
            if (OptionsControl.getBoolean(PREFID_3))
                tableThree.addView(ReferenceCell.buildRow(context, KANA_SET_3_BASE_DIGRAPHS));
            if (OptionsControl.getBoolean(PREFID_4))
                tableThree.addView(ReferenceCell.buildRow(context, KANA_SET_4_BASE_DIGRAPHS));
            if (OptionsControl.getBoolean(PREFID_5))
                tableThree.addView(ReferenceCell.buildRow(context, KANA_SET_5_DIGRAPHS));
            if (OptionsControl.getBoolean(PREFID_6))
                tableThree.addView(ReferenceCell.buildRow(context, KANA_SET_6_BASE_DIGRAPHS));
            if (OptionsControl.getBoolean(PREFID_7))
                tableThree.addView(ReferenceCell.buildRow(context, KANA_SET_7_DIGRAPHS));
            if (OptionsControl.getBoolean(PREFID_8))
                tableThree.addView(ReferenceCell.buildRow(context, KANA_SET_8_DIGRAPHS));

            if (OptionsControl.getBoolean(R.string.prefid_diacritics))
            {
                if (OptionsControl.getBoolean(PREFID_2))
                    tableThree.addView(ReferenceCell.buildRow(context, KANA_SET_2_DAKUTEN_DIGRAPHS));
                if (OptionsControl.getBoolean(PREFID_3))
                    tableThree.addView(ReferenceCell.buildRow(context, KANA_SET_3_DAKUTEN_DIGRAPHS));
                if (OptionsControl.getBoolean(PREFID_4))
                    tableThree.addView(ReferenceCell.buildRow(context, KANA_SET_4_DAKUTEN_DIGRAPHS));
                if (OptionsControl.getBoolean(PREFID_6))
                {
                    tableThree.addView(ReferenceCell.buildRow(context, KANA_SET_6_DAKUTEN_DIGRAPHS));
                    tableThree.addView(ReferenceCell.buildRow(context, KANA_SET_6_HANDAKETEN_DIGRAPHS));
                }
            }

            if (tableThree.getChildCount() > 0)
                layout.addView(ReferenceCell.buildHeader(context, R.string.digraphs_title));

            layout.addView(tableThree);
        }

        return layout;
    }
}
