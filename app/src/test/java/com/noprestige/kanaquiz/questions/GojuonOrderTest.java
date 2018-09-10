package com.noprestige.kanaquiz.questions;

import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertArrayEquals;

public class GojuonOrderTest
{
    private static final String[] EXPECTED_ORDER_DEFAULT = {
            "a", "i", "u", "e", "o", "ka", "ki", "ku", "ke", "ko", "kya", "kyu", "kyo", "ga", "gi", "gu", "ge", "go",
            "gya", "gyu", "gyo", "sa", "shi", "su", "se", "so", "sha", "shu", "sho", "za", "ji", "zu", "ze", "zo", "ja",
            "ju", "jo", "ta", "chi", "tsu", "te", "to", "cha", "chu", "cho", "da", /*"ji", "zu",*/ "de", "do", /*"ja",
            "ju", "jo",*/ "na", "ni", "nu", "ne", "no", "nya", "nyu", "nyo", "ha", "hi", "fu", "he", "ho", "hya", "hyu",
            "hyo", "ba", "bi", "bu", "be", "bo", "bya", "byu", "byo", "pa", "pi", "pu", "pe", "po", "pya", "pyu", "pyo",
            "ma", "mi", "mu", "me", "mo", "mya", "myu", "myo", "ya", "yu", "yo", "ra", "ri", "ru", "re", "ro", "rya",
            "ryu", "ryo", "wa", "wi", "we", "wo", "n", "l", "q", "v", "x"
    };

    private static final String[] EXPECTED_ORDER_HEPBURN = {
            "a", "i", "u", "e", "o", "ka", "ki", "ku", "ke", "ko", "kya", "kyu", "kyo", "ga", "gi", "gu", "ge", "go",
            "gya", "gyu", "gyo", "sa", "shi", "su", "se", "so", "sha", "shu", "sho", "za", "ji", "zu", "ze", "zo", "ja",
            "ju", "jo", "ta", "chi", "tsu", "te", "to", "cha", "chu", "cho", "da", /*"ji", "zu",*/ "de", "do", /*"ja",
            "ju", "jo",*/ "na", "ni", "nu", "ne", "no", "nya", "nyu", "nyo", "ha", "hi", "fu", "he", "ho", "hya", "hyu",
            "hyo", "ba", "bi", "bu", "be", "bo", "bya", "byu", "byo", "pa", "pi", "pu", "pe", "po", "pya", "pyu", "pyo",
            "ma", "mi", "mu", "me", "mo", "mya", "myu", "myo", "ya", "yu", "yo", "ra", "ri", "ru", "re", "ro", "rya",
            "ryu", "ryo", "wa", /*"i", "e", "o",*/ "n", "l", "q", "v", "x"
    };

    private static final String[] EXPECTED_ORDER_NIHON = {
            "a", "i", "u", "e", "o", "ka", "ki", "ku", "ke", "ko", "kya", "kyu", "kyo", "ga", "gi", "gu", "ge", "go",
            "gya", "gyu", "gyo", "sa", "si", "su", "se", "so", "sya", "syu", "syo", "za", "zi", "zu", "ze", "zo", "zya",
            "zyu", "zyo", "ta", "ti", "tu", "te", "to", "tya", "tyu", "tyo", "da", "di", "du", "de", "do", "dya", "dyu",
            "dyo", "na", "ni", "nu", "ne", "no", "nya", "nyu", "nyo", "ha", "hi", "hu", "he", "ho", "hya", "hyu", "hyo",
            "ba", "bi", "bu", "be", "bo", "bya", "byu", "byo", "pa", "pi", "pu", "pe", "po", "pya", "pyu", "pyo", "ma",
            "mi", "mu", "me", "mo", "mya", "myu", "myo", "ya", "yu", "yo", "ra", "ri", "ru", "re", "ro", "rya", "ryu",
            "ryo", "wa", "wi", "we", "wo", "n", "l", "q", "v", "x"
    };

    private static final String[] EXPECTED_ORDER_KUNREI = {
            "a", "i", "u", "e", "o", "ka", "ki", "ku", "ke", "ko", "kya", "kyu", "kyo", "ga", "gi", "gu", "ge", "go",
            "gya", "gyu", "gyo", "sa", "si", "su", "se", "so", "sya", "syu", "syo", "za", "zi", "zu", "ze", "zo", "zya",
            "zyu", "zyo", "ta", "ti", "tu", "te", "to", "tya", "tyu", "tyo", "da", /*"zi", "zu",*/ "de", "do", /*"zya",
            "zyu", "zyo",*/ "na", "ni", "nu", "ne", "no", "nya", "nyu", "nyo", "ha", "hi", "hu", "he", "ho", "hya",
            "hyu", "hyo", "ba", "bi", "bu", "be", "bo", "bya", "byu", "byo", "pa", "pi", "pu", "pe", "po", "pya", "pyu",
            "pyo", "ma", "mi", "mu", "me", "mo", "mya", "myu", "myo", "ya", "yu", "yo", "ra", "ri", "ru", "re", "ro",
            "rya", "ryu", "ryo", "wa", /*"i", "e", "o",*/ "n", "l", "q", "v", "x"
    };

    private static final String[] EXPECTED_ORDER_NIHON_CAPITAL = {
            "A", "I", "U", "E", "O", "KA", "KI", "KU", "KE", "KO", "KYA", "KYU", "KYO", "GA", "GI", "GU", "GE", "GO",
            "GYA", "GYU", "GYO", "SA", "SI", "SU", "SE", "SO", "SYA", "SYU", "SYO", "ZA", "ZI", "ZU", "ZE", "ZO", "ZYA",
            "ZYU", "ZYO", "TA", "TI", "TU", "TE", "TO", "TYA", "TYU", "TYO", "DA", "DI", "DU", "DE", "DO", "DYA", "DYU",
            "DYO", "NA", "NI", "NU", "NE", "NO", "NYA", "NYU", "NYO", "HA", "HI", "HU", "HE", "HO", "HYA", "HYU", "HYO",
            "BA", "BI", "BU", "BE", "BO", "BYA", "BYU", "BYO", "PA", "PI", "PU", "PE", "PO", "PYA", "PYU", "PYO", "MA",
            "MI", "MU", "ME", "MO", "MYA", "MYU", "MYO", "YA", "YU", "YO", "RA", "RI", "RU", "RE", "RO", "RYA", "RYU",
            "RYO", "WA", "WI", "WE", "WO", "N", "L"
    };

    private static final String[] EXPECTED_ORDER_MULTICHAR = {
            "iau", "iae", "ie", "ieo", "ue", "ueo", "kaa", "ki", "kiu", "ku", "kua", "kuu", "kuka", "kuko", "kyu",
            "kyui", "kyuka", "kyuko", "kyo", "n"
    };

    @Test
    public void sortTest()
    {
        checkOrder(EXPECTED_ORDER_DEFAULT);
        checkOrder(EXPECTED_ORDER_HEPBURN);
        checkOrder(EXPECTED_ORDER_NIHON);
        checkOrder(EXPECTED_ORDER_KUNREI);

        checkOrder(EXPECTED_ORDER_NIHON_CAPITAL);
        checkOrder(EXPECTED_ORDER_MULTICHAR);
    }

    private void checkOrder(String[] testArray)
    {
        //to confirm the sorting is actually happening, reverse the array fed to the sorter
        String[] reversedArray = new String[testArray.length];
        for (int i = 0; i < testArray.length; i++)
            reversedArray[testArray.length - 1 - i] = testArray[i];

        Set<String> sortedSet = new TreeSet<>(new GojuonOrder());
        sortedSet.addAll(Arrays.asList(reversedArray));

        assertArrayEquals(testArray, sortedSet.toArray());
    }
}