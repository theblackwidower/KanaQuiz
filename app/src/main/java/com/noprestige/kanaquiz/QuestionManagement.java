package com.noprestige.kanaquiz;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;

abstract class QuestionManagement
{
    private KanaQuestion[] KANA_SET_1;
    private KanaQuestion[] KANA_SET_2_BASE;
    private KanaQuestion[] KANA_SET_2_DAKUTEN;
    private KanaQuestion[] KANA_SET_2_BASE_DIGRAPHS;
    private KanaQuestion[] KANA_SET_2_DAKUTEN_DIGRAPHS;
    private KanaQuestion[] KANA_SET_3_BASE;
    private KanaQuestion[] KANA_SET_3_DAKUTEN;
    private KanaQuestion[] KANA_SET_3_BASE_DIGRAPHS;
    private KanaQuestion[] KANA_SET_3_DAKUTEN_DIGRAPHS;
    private KanaQuestion[] KANA_SET_4_BASE;
    private KanaQuestion[] KANA_SET_4_DAKUTEN;
    private KanaQuestion[] KANA_SET_4_BASE_DIGRAPHS;
    private KanaQuestion[] KANA_SET_4_DAKUTEN_DIGRAPHS;
    private KanaQuestion[] KANA_SET_5;
    private KanaQuestion[] KANA_SET_5_DIGRAPHS;
    private KanaQuestion[] KANA_SET_6_BASE;
    private KanaQuestion[] KANA_SET_6_DAKUTEN;
    private KanaQuestion[] KANA_SET_6_HANDAKETEN;
    private KanaQuestion[] KANA_SET_6_BASE_DIGRAPHS;
    private KanaQuestion[] KANA_SET_6_DAKUTEN_DIGRAPHS;
    private KanaQuestion[] KANA_SET_6_HANDAKETEN_DIGRAPHS;
    private KanaQuestion[] KANA_SET_7;
    private KanaQuestion[] KANA_SET_7_DIGRAPHS;
    private KanaQuestion[] KANA_SET_8;
    private KanaQuestion[] KANA_SET_8_DIGRAPHS;
    private KanaQuestion[] KANA_SET_9;
    private KanaQuestion[] KANA_SET_10_W_GROUP;
    private KanaQuestion[] KANA_SET_10_N_CONSONANT;

    private int PREFID_1;
    private int PREFID_2;
    private int PREFID_3;
    private int PREFID_4;
    private int PREFID_5;
    private int PREFID_6;
    private int PREFID_7;
    private int PREFID_8;
    private int PREFID_9;
    private int PREFID_10;

    //TODO: Simplify
    QuestionManagement(
            KanaQuestion[] KANA_SET_1,
            KanaQuestion[] KANA_SET_2_BASE,
            KanaQuestion[] KANA_SET_2_DAKUTEN,
            KanaQuestion[] KANA_SET_2_BASE_DIGRAPHS,
            KanaQuestion[] KANA_SET_2_DAKUTEN_DIGRAPHS,
            KanaQuestion[] KANA_SET_3_BASE,
            KanaQuestion[] KANA_SET_3_DAKUTEN,
            KanaQuestion[] KANA_SET_3_BASE_DIGRAPHS,
            KanaQuestion[] KANA_SET_3_DAKUTEN_DIGRAPHS,
            KanaQuestion[] KANA_SET_4_BASE,
            KanaQuestion[] KANA_SET_4_DAKUTEN,
            KanaQuestion[] KANA_SET_4_BASE_DIGRAPHS,
            KanaQuestion[] KANA_SET_4_DAKUTEN_DIGRAPHS,
            KanaQuestion[] KANA_SET_5,
            KanaQuestion[] KANA_SET_5_DIGRAPHS,
            KanaQuestion[] KANA_SET_6_BASE,
            KanaQuestion[] KANA_SET_6_DAKUTEN,
            KanaQuestion[] KANA_SET_6_HANDAKETEN,
            KanaQuestion[] KANA_SET_6_BASE_DIGRAPHS,
            KanaQuestion[] KANA_SET_6_DAKUTEN_DIGRAPHS,
            KanaQuestion[] KANA_SET_6_HANDAKETEN_DIGRAPHS,
            KanaQuestion[] KANA_SET_7,
            KanaQuestion[] KANA_SET_7_DIGRAPHS,
            KanaQuestion[] KANA_SET_8,
            KanaQuestion[] KANA_SET_8_DIGRAPHS,
            KanaQuestion[] KANA_SET_9,
            KanaQuestion[] KANA_SET_10_W_GROUP,
            KanaQuestion[] KANA_SET_10_N_CONSONANT,
            int PREFID_1, int PREFID_2, int PREFID_3, int PREFID_4, int PREFID_5,
            int PREFID_6, int PREFID_7, int PREFID_8, int PREFID_9, int PREFID_10)
    {
        this.KANA_SET_1 = KANA_SET_1;
        this.KANA_SET_2_BASE = KANA_SET_2_BASE;
        this.KANA_SET_2_DAKUTEN = KANA_SET_2_DAKUTEN;
        this.KANA_SET_2_BASE_DIGRAPHS = KANA_SET_2_BASE_DIGRAPHS;
        this.KANA_SET_2_DAKUTEN_DIGRAPHS = KANA_SET_2_DAKUTEN_DIGRAPHS;
        this.KANA_SET_3_BASE = KANA_SET_3_BASE;
        this.KANA_SET_3_DAKUTEN = KANA_SET_3_DAKUTEN;
        this.KANA_SET_3_BASE_DIGRAPHS = KANA_SET_3_BASE_DIGRAPHS;
        this.KANA_SET_3_DAKUTEN_DIGRAPHS = KANA_SET_3_DAKUTEN_DIGRAPHS;
        this.KANA_SET_4_BASE = KANA_SET_4_BASE;
        this.KANA_SET_4_DAKUTEN = KANA_SET_4_DAKUTEN;
        this.KANA_SET_4_BASE_DIGRAPHS = KANA_SET_4_BASE_DIGRAPHS;
        this.KANA_SET_4_DAKUTEN_DIGRAPHS = KANA_SET_4_DAKUTEN_DIGRAPHS;
        this.KANA_SET_5 = KANA_SET_5;
        this.KANA_SET_5_DIGRAPHS = KANA_SET_5_DIGRAPHS;
        this.KANA_SET_6_BASE = KANA_SET_6_BASE;
        this.KANA_SET_6_DAKUTEN = KANA_SET_6_DAKUTEN;
        this.KANA_SET_6_HANDAKETEN = KANA_SET_6_HANDAKETEN;
        this.KANA_SET_6_BASE_DIGRAPHS = KANA_SET_6_BASE_DIGRAPHS;
        this.KANA_SET_6_DAKUTEN_DIGRAPHS = KANA_SET_6_DAKUTEN_DIGRAPHS;
        this.KANA_SET_6_HANDAKETEN_DIGRAPHS = KANA_SET_6_HANDAKETEN_DIGRAPHS;
        this.KANA_SET_7 = KANA_SET_7;
        this.KANA_SET_7_DIGRAPHS = KANA_SET_7_DIGRAPHS;
        this.KANA_SET_8 = KANA_SET_8;
        this.KANA_SET_8_DIGRAPHS = KANA_SET_8_DIGRAPHS;
        this.KANA_SET_9 = KANA_SET_9;
        this.KANA_SET_10_W_GROUP = KANA_SET_10_W_GROUP;
        this.KANA_SET_10_N_CONSONANT = KANA_SET_10_N_CONSONANT;

        this.PREFID_1 = PREFID_1;
        this.PREFID_2 = PREFID_2;
        this.PREFID_3 = PREFID_3;
        this.PREFID_4 = PREFID_4;
        this.PREFID_5 = PREFID_5;
        this.PREFID_6 = PREFID_6;
        this.PREFID_7 = PREFID_7;
        this.PREFID_8 = PREFID_8;
        this.PREFID_9 = PREFID_9;
        this.PREFID_10 = PREFID_10;
    }

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
