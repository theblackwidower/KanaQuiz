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

    KanaQuestion[] getKanaSet(int number)
    {
        return getKanaSet(number, NO_DIACRITIC);
    }

    KanaQuestion[] getKanaSet(int number, Diacritic diacritic)
    {
        return getKanaSet(number, diacritic, false);
    }

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

    private String getKanaSetDisplay(int setNumber)
    {
        return getKanaSetDisplay(setNumber, NO_DIACRITIC);
    }

    private String getKanaSetDisplay(int setNumber, Diacritic diacritic)
    {
        String returnValue = "";
        for (KanaQuestion question : getKanaSet(setNumber, diacritic))
            returnValue += question.getKana() + " ";
        return returnValue;
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
            item.setContents(displayContents(i));
            item.setPrefId(getPrefId(i));
            layout.addView(item);
        }

        return layout;
    }

    private static final String[] GOJUON_ORDER = {
            "a", "i", "u", "e", "o",
            "ka", "ki", "ku", "ke", "ko",
            "kya", "kyu", "kyo",
            "ga", "gi", "gu", "ge", "go",
            "gya", "gyu", "gyo",
            "sa", "shi", "su", "se", "so",
            "sha", "shu", "sho",
            "za", "ji", "zu", "ze", "zo",
            "ja", "ju", "jo",
            "ta", "chi", "tsu", "te", "to",
            "cha", "chu", "cho",
            "da", /*"ji", "zu",*/ "de", "do",
            //"ja", "ju", "jo",
            "na", "ni", "nu", "ne", "no",
            "nya", "nyu", "nyo",
            "ha", "hi", "fu", "he", "ho",
            "hya", "hyu", "hyo",
            "ba", "bi", "bu", "be", "bo",
            "bya", "byu", "byo",
            "pa", "pi", "pu", "pe", "po",
            "pya", "pyu", "pyo",
            "ma", "mi", "mu", "me", "mo",
            "mya", "myu", "myo",
            "ya", "yu", "yo",
            "ra", "ri", "ru", "re", "ro",
            "rya", "ryu", "ryo",
            "wa", "wo",
            "n",};

    static void gojuonSort(String[] romanji)
    {
        //String order = "aiueokgszjtcdnhfbpmyrw";
        for (int i = 1; i < romanji.length; i++)
        {
            for (int j = 0; j < romanji.length - i; j++)
            {
                if (getSortId(romanji[j]) > getSortId(romanji[j + 1]))
                {
                    String temp = romanji[j];
                    romanji[j] = romanji[j + 1];
                    romanji[j + 1] = temp;
                }
            }
        }
    }

    private static int getSortId(String romanji)
    {
        for (int i = 0; i < GOJUON_ORDER.length; i++)
            if (GOJUON_ORDER[i].equals(romanji))
                return i;
        return GOJUON_ORDER.length;
    }
}
