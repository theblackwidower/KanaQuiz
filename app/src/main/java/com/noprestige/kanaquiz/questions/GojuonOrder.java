package com.noprestige.kanaquiz.questions;

import java.util.Arrays;
import java.util.Comparator;

public class GojuonOrder implements Comparator<String>
{
    private static final String[] GOJUON_ORDER = {
            "a", "i", "u", "e", "o",
            "ka", "ki", "ku", "ke", "ko",
            "kya", "kyu", "kyo",
            "ga", "gi", "gu", "ge", "go",
            "gya", "gyu", "gyo",
            "sa", "shi", "si", "su", "se", "so",
            "sha", "sya", "shu", "syu", "sho", "syo",
            "za", "ji", "zi", "zu", "ze", "zo",
            "ja", "zya", "ju", "zyu", "jo", "zyo",
            "ta", "chi", "ti", "tsu", "tu", "te", "to",
            "cha", "tya", "chu", "tyu", "cho", "tyo",
            "da", /*"ji",*/ "di", /*"zi", "zu",*/ "du", "de", "do",
            /*"ja",*/ "dya", /*"zya", "ju",*/ "dyu", /*"zyu", "jo",*/ "dyo", //"zyo",
            "na", "ni", "nu", "ne", "no",
            "nya", "nyu", "nyo",
            "ha", "hi", "fu", "hu", "he", "ho",
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
            "wa", "wi", "we", "wo",
            "n"};

    static void sort(String[] romanji)
    {
        Arrays.sort(romanji, new GojuonOrder());
    }

    private static int getSortId(String romanji)
    {
        for (int i = 0; i < GOJUON_ORDER.length; i++)
            if (GOJUON_ORDER[i].equals(romanji))
                return i;
        return GOJUON_ORDER.length;
    }

    @Override
    public int compare(String item1, String item2)
    {
        return getSortId(item1) - getSortId(item2);
    }
}
