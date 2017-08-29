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

    private KanaQuestion[][][][] kanaSets;
    private int[] prefIds;

    private static final int SET_1_TITLE = R.string.set_1_title;
    private static final int SET_2_TITLE = R.string.set_2_title;
    private static final int SET_3_TITLE = R.string.set_3_title;
    private static final int SET_4_TITLE = R.string.set_4_title;
    private static final int SET_5_TITLE = R.string.set_5_title;
    private static final int SET_6_TITLE = R.string.set_6_title;
    private static final int SET_7_TITLE = R.string.set_7_title;
    private static final int SET_8_TITLE = R.string.set_8_title;
    private static final int SET_9_TITLE = R.string.set_9_title;
    private static final int SET_10_TITLE = R.string.set_10_title;

    private static final int[] SET_TITLES = {SET_1_TITLE, SET_2_TITLE, SET_3_TITLE, SET_4_TITLE, SET_5_TITLE, SET_6_TITLE, SET_7_TITLE, SET_8_TITLE, SET_9_TITLE, SET_10_TITLE};

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
            int[] prefIds)
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

        this.prefIds = prefIds;
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
        KanaQuestion[] value = kanaSets[number - 1][diacritic.ordinal()][isDigraphs ? 1 : 0];

        if (value.length > 0)
            return value;
        else
            throw new NullPointerException();
    }

    private int getPrefId(int number)
    {
        return prefIds[number - 1];
    }

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
            questionBank.addQuestions(getKanaSet(1));
        for (int i = 2; i <= 8; i++)
            if (getPref(i))
            {
                questionBank.addQuestions(getKanaSet(i));
                if (isDigraphs)
                    questionBank.addQuestions(getKanaSet(i, NO_DIACRITIC, true));
            }

        if (isDiacritics)
        {
            for (int i = 2; i <= 4; i++)
                if (getPref(i))
                {
                    questionBank.addQuestions(getKanaSet(i, DAKUTEN));
                    if (isDigraphs)
                        questionBank.addQuestions(getKanaSet(i, DAKUTEN, true));
                }

            if (getPref(6))
            {
                questionBank.addQuestions(getKanaSet(6, DAKUTEN), getKanaSet(6, HANDAKUTEN));
                if (isDigraphs)
                    questionBank.addQuestions(getKanaSet(6, DAKUTEN, true), getKanaSet(6, HANDAKUTEN, true));
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

    String displayContents(int setNumber)
    {
        String returnValue = "";

        boolean isDiacritics = OptionsControl.getBoolean(R.string.prefid_diacritics);

        for (KanaQuestion question : getKanaSet(setNumber))
            returnValue += question.getKana() + " ";

        if (isDiacritics && (setNumber >= 2 && setNumber <= 4 || setNumber == 6))
        {
            for (KanaQuestion question : getKanaSet(setNumber, DAKUTEN))
                returnValue += question.getKana() + " ";

            if (setNumber == 6)
                for (KanaQuestion question : getKanaSet(setNumber, HANDAKUTEN))
                    returnValue += question.getKana() + " ";
        }

        if (setNumber == 10)
            for (KanaQuestion question : getKanaSet(setNumber, CONSONANT))
                returnValue += question.getKana() + " ";

        return returnValue;
    }

    TableLayout getMainReferenceTable(Context context)
    {
        TableLayout table = new TableLayout(context);
        table.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);

        for (int i = 1; i <= 7; i++)
            if (isFullReference || getPref(i))
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i)));

        if (isFullReference || getPref(9))
            table.addView(ReferenceCell.buildSpecialRow(context, getKanaSet(9)));
        if (isFullReference || getPref(8)) //fits gojūon ordering
            table.addView(ReferenceCell.buildRow(context, getKanaSet(8)));
        if (isFullReference || getPref(10))
        {
            table.addView(ReferenceCell.buildSpecialRow(context, getKanaSet(10)));
            table.addView(ReferenceCell.buildSpecialRow(context, getKanaSet(10, CONSONANT)));
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
                table.addView(ReferenceCell.buildRow(context, getKanaSet(i, DAKUTEN)));

        if (isFullReference || getPref(6))
        {
            table.addView(ReferenceCell.buildRow(context, getKanaSet(6, DAKUTEN)));
            table.addView(ReferenceCell.buildRow(context, getKanaSet(6, HANDAKUTEN)));
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
            item.setBaseContents(displayContents(i));
            item.setPrefId(getPrefId(i));
            layout.addView(item);
        }

        return layout;
    }
}
