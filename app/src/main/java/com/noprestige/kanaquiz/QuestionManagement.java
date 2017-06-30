package com.noprestige.kanaquiz;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import static com.noprestige.kanaquiz.Diacritic.CONSONANT;
import static com.noprestige.kanaquiz.Diacritic.NO_DIACRITIC;
import static com.noprestige.kanaquiz.Diacritic.DAKUTEN;
import static com.noprestige.kanaquiz.Diacritic.HANDAKUTEN;

abstract class QuestionManagement
{
    private static final int CATEGORY_COUNT = 10;

    private KanaQuestion[][][][] kanaSets;
    private int[] prefIds;

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
        kanaSets = new KanaQuestion[CATEGORY_COUNT][Diacritic.values().length][2][];

        kanaSets[0][NO_DIACRITIC.ordinal()][0] = KANA_SET_1;

        kanaSets[1][NO_DIACRITIC.ordinal()][0] = KANA_SET_2_BASE;
        kanaSets[1][DAKUTEN.ordinal()][0] = KANA_SET_2_DAKUTEN;
        kanaSets[1][NO_DIACRITIC.ordinal()][1] = KANA_SET_2_BASE_DIGRAPHS;
        kanaSets[1][DAKUTEN.ordinal()][1] = KANA_SET_2_DAKUTEN_DIGRAPHS;

        kanaSets[2][NO_DIACRITIC.ordinal()][0] = KANA_SET_3_BASE;
        kanaSets[2][DAKUTEN.ordinal()][0] = KANA_SET_3_DAKUTEN;
        kanaSets[2][NO_DIACRITIC.ordinal()][1] = KANA_SET_3_BASE_DIGRAPHS;
        kanaSets[2][DAKUTEN.ordinal()][1] = KANA_SET_3_DAKUTEN_DIGRAPHS;

        kanaSets[3][NO_DIACRITIC.ordinal()][0] = KANA_SET_4_BASE;
        kanaSets[3][DAKUTEN.ordinal()][0] = KANA_SET_4_DAKUTEN;
        kanaSets[3][NO_DIACRITIC.ordinal()][1] = KANA_SET_4_BASE_DIGRAPHS;
        kanaSets[3][DAKUTEN.ordinal()][1] = KANA_SET_4_DAKUTEN_DIGRAPHS;

        kanaSets[4][NO_DIACRITIC.ordinal()][0] = KANA_SET_5;
        kanaSets[4][NO_DIACRITIC.ordinal()][1] = KANA_SET_5_DIGRAPHS;

        kanaSets[5][NO_DIACRITIC.ordinal()][0] = KANA_SET_6_BASE;
        kanaSets[5][DAKUTEN.ordinal()][0] = KANA_SET_6_DAKUTEN;
        kanaSets[5][HANDAKUTEN.ordinal()][0] = KANA_SET_6_HANDAKETEN;
        kanaSets[5][NO_DIACRITIC.ordinal()][1] = KANA_SET_6_BASE_DIGRAPHS;
        kanaSets[5][DAKUTEN.ordinal()][1] = KANA_SET_6_DAKUTEN_DIGRAPHS;
        kanaSets[5][HANDAKUTEN.ordinal()][1] = KANA_SET_6_HANDAKETEN_DIGRAPHS;

        kanaSets[6][NO_DIACRITIC.ordinal()][0] = KANA_SET_7;
        kanaSets[6][NO_DIACRITIC.ordinal()][1] = KANA_SET_7_DIGRAPHS;

        kanaSets[7][NO_DIACRITIC.ordinal()][0] = KANA_SET_8;
        kanaSets[7][NO_DIACRITIC.ordinal()][1] = KANA_SET_8_DIGRAPHS;

        kanaSets[8][NO_DIACRITIC.ordinal()][0] = KANA_SET_9;

        kanaSets[9][NO_DIACRITIC.ordinal()][0] = KANA_SET_10_W_GROUP;
        kanaSets[9][CONSONANT.ordinal()][0] = KANA_SET_10_N_CONSONANT;

        prefIds = new int[CATEGORY_COUNT];
        prefIds[0] = PREFID_1;
        prefIds[1] = PREFID_2;
        prefIds[2] = PREFID_3;
        prefIds[3] = PREFID_4;
        prefIds[4] = PREFID_5;
        prefIds[5] = PREFID_6;
        prefIds[6] = PREFID_7;
        prefIds[7] = PREFID_8;
        prefIds[8] = PREFID_9;
        prefIds[9] = PREFID_10;
    }

    private KanaQuestion[] getKanaSet(int number)
    {
        return getKanaSet(number, NO_DIACRITIC);
    }
    private KanaQuestion[] getKanaSet(int number, Diacritic diacritic)
    {
        return getKanaSet(number, diacritic, false);
    }
    private KanaQuestion[] getKanaSet(int number, Diacritic diacritic, boolean isDigraphs)
    {
        KanaQuestion[] value = kanaSets[number - 1][diacritic.ordinal()][isDigraphs?1:0];

        if (value.length > 0)
            return value;
        else
            throw new NullPointerException();
    }

    private int getPrefId(int number)
    {
        return prefIds[number - 1];
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
            questionBank.addQuestions(getKanaSet(1));
        for (int i = 2; i <= 4; i++)
        {
            if (getPref(i))
            {
                questionBank.addQuestions(getKanaSet(i));
                if (isDiacritics)
                    questionBank.addQuestions(getKanaSet(i, DAKUTEN));
                if (isDigraphs)
                    questionBank.addQuestions(getKanaSet(i, NO_DIACRITIC, true));
                if (isDigraphs && isDiacritics)
                    questionBank.addQuestions(getKanaSet(i, DAKUTEN, true));
            }
        }
        if (getPref(5))
        {
            questionBank.addQuestions(getKanaSet(5));
            if (isDigraphs)
                questionBank.addQuestions(getKanaSet(5, NO_DIACRITIC, true));
        }
        if (getPref(6))
        {
            questionBank.addQuestions(getKanaSet(6));
            if (isDiacritics)
                questionBank.addQuestions(getKanaSet(6, DAKUTEN), getKanaSet(6, HANDAKUTEN));
            if (isDigraphs)
                questionBank.addQuestions(getKanaSet(6, NO_DIACRITIC, true));
            if (isDigraphs && isDiacritics)
                questionBank.addQuestions(getKanaSet(6, DAKUTEN, true), getKanaSet(6, HANDAKUTEN, true));

        }
        for (int i = 7; i <= 8; i++)
        {
            if (getPref(i))
            {
                questionBank.addQuestions(getKanaSet(i));
                if (isDigraphs)
                    questionBank.addQuestions(getKanaSet(i, NO_DIACRITIC, true));
            }
        }
        if (getPref(9))
            questionBank.addQuestions(getKanaSet(9));
        if (getPref(10))
            questionBank.addQuestions(getKanaSet(10), getKanaSet(10, CONSONANT));

        return questionBank;
    }

    boolean anySelected()
    {
        for (int i = 1; i <= CATEGORY_COUNT; i++)
        {
            if (getPref(i))
                return true;
        }
        return false;
    }

    LinearLayout getReferenceTable(Context context)
    {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);

        TableLayout tableOne = new TableLayout(context);
        tableOne.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TableLayout tableTwo = new TableLayout(context);
        tableTwo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TableLayout tableThree = new TableLayout(context);
        tableThree.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        for (int i = 1; i <= 8; i++)
        {
            if (getPref(i))
                tableOne.addView(ReferenceCell.buildRow(context, getKanaSet(i)));
        }
        if (getPref(9))
            tableOne.addView(ReferenceCell.buildSpecialRow(context, getKanaSet(9)));
        if (getPref(10))
        {
            tableOne.addView(ReferenceCell.buildSpecialRow(context, getKanaSet(10)));
            tableOne.addView(ReferenceCell.buildSpecialRow(context, getKanaSet(10, CONSONANT)));
        }

        if (OptionsControl.getBoolean(R.string.prefid_diacritics))
        {
            for (int i = 2; i <= 4; i++)
            {
                if (getPref(i))
                    tableTwo.addView(ReferenceCell.buildRow(context, getKanaSet(i, DAKUTEN)));
            }
            if (getPref(6))
            {
                tableTwo.addView(ReferenceCell.buildRow(context, getKanaSet(6, DAKUTEN)));
                tableTwo.addView(ReferenceCell.buildRow(context, getKanaSet(6, HANDAKUTEN)));
            }
        }

        if (OptionsControl.getBoolean(R.string.prefid_digraphs) && getPref(9))
        {
            for (int i = 2; i <= 8; i++)
            {
                if (getPref(i))
                    tableThree.addView(ReferenceCell.buildRow(context, getKanaSet(i, NO_DIACRITIC, true)));
            }

            if (OptionsControl.getBoolean(R.string.prefid_diacritics))
            {
                for (int i = 2; i <= 4; i++)
                {
                    if (getPref(i))
                        tableThree.addView(ReferenceCell.buildRow(context, getKanaSet(i, DAKUTEN, true)));
                }
                if (getPref(6))
                {
                    tableThree.addView(ReferenceCell.buildRow(context, getKanaSet(6, DAKUTEN, true)));
                    tableThree.addView(ReferenceCell.buildRow(context, getKanaSet(6, HANDAKUTEN, true)));
                }
            }
        }

        layout.addView(tableOne);
        if (tableTwo.getChildCount() > 0)
        {
            layout.addView(ReferenceCell.buildHeader(context, R.string.diacritics_title));
            layout.addView(tableTwo);
        }
        if (tableThree.getChildCount() > 0)
        {
            layout.addView(ReferenceCell.buildHeader(context, R.string.digraphs_title));
            layout.addView(tableThree);
        }

        return layout;
    }
}
