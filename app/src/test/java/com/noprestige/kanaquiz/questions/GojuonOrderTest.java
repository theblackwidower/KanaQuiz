package com.noprestige.kanaquiz.questions;

import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertArrayEquals;

public class GojuonOrderTest
{
    private static final String[] EXPECTED_ORDER = {
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

    @Test
    public void sortTest() throws Exception
    {
        Set<String> sortedSet = new TreeSet<>(new GojuonOrder());
        sortedSet.addAll(Arrays.asList(EXPECTED_ORDER));

        assertArrayEquals(sortedSet.toArray(), EXPECTED_ORDER);
    }
}