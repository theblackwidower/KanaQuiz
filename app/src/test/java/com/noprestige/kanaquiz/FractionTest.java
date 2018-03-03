package com.noprestige.kanaquiz;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FractionTest
{
    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(new Fraction(1f / 2f).toString(), "½");
        assertEquals(new Fraction(2f / 2f).toString(), "1");
        assertEquals(new Fraction(3f / 2f).toString(), "1½");
        assertEquals(new Fraction(4f / 2f).toString(), "2");

        assertEquals(new Fraction(1f / 3f).toString(), "⅓");
        assertEquals(new Fraction(2f / 3f).toString(), "⅔");
        assertEquals(new Fraction(3f / 3f).toString(), "1");
        assertEquals(new Fraction(4f / 3f).toString(), "1⅓");
        assertEquals(new Fraction(5f / 3f).toString(), "1⅔");
        assertEquals(new Fraction(6f / 3f).toString(), "2");

        assertEquals(new Fraction(1f / 4f).toString(), "¼");
        assertEquals(new Fraction(2f / 4f).toString(), "½");
        assertEquals(new Fraction(3f / 4f).toString(), "¾");
        assertEquals(new Fraction(4f / 4f).toString(), "1");
        assertEquals(new Fraction(5f / 4f).toString(), "1¼");
        assertEquals(new Fraction(6f / 4f).toString(), "1½");
        assertEquals(new Fraction(7f / 4f).toString(), "1¾");
        assertEquals(new Fraction(8f / 4f).toString(), "2");

        assertEquals(new Fraction(1f / 5f).toString(), "⅕");
        assertEquals(new Fraction(2f / 5f).toString(), "⅖");
        assertEquals(new Fraction(3f / 5f).toString(), "⅗");
        assertEquals(new Fraction(4f / 5f).toString(), "⅘");
        assertEquals(new Fraction(5f / 5f).toString(), "1");
        assertEquals(new Fraction(6f / 5f).toString(), "1⅕");
        assertEquals(new Fraction(7f / 5f).toString(), "1⅖");
        assertEquals(new Fraction(8f / 5f).toString(), "1⅗");
        assertEquals(new Fraction(9f / 5f).toString(), "1⅘");
        assertEquals(new Fraction(10f / 5f).toString(), "2");

        assertEquals(new Fraction(1f / 6f).toString(), "⅙");
        assertEquals(new Fraction(2f / 6f).toString(), "⅓");
        assertEquals(new Fraction(3f / 6f).toString(), "½");
        assertEquals(new Fraction(4f / 6f).toString(), "⅔");
        assertEquals(new Fraction(5f / 6f).toString(), "⅚");
        assertEquals(new Fraction(6f / 6f).toString(), "1");
        assertEquals(new Fraction(7f / 6f).toString(), "1⅙");
        assertEquals(new Fraction(8f / 6f).toString(), "1⅓");
        assertEquals(new Fraction(9f / 6f).toString(), "1½");
        assertEquals(new Fraction(10f / 6f).toString(), "1⅔");
        assertEquals(new Fraction(11f / 6f).toString(), "1⅚");
        assertEquals(new Fraction(12f / 6f).toString(), "2");

        assertEquals(new Fraction(1f / 8f).toString(), "⅛");
        assertEquals(new Fraction(2f / 8f).toString(), "¼");
        assertEquals(new Fraction(3f / 8f).toString(), "⅜");
        assertEquals(new Fraction(4f / 8f).toString(), "½");
        assertEquals(new Fraction(5f / 8f).toString(), "⅝");
        assertEquals(new Fraction(6f / 8f).toString(), "¾");
        assertEquals(new Fraction(7f / 8f).toString(), "⅞");
        assertEquals(new Fraction(8f / 8f).toString(), "1");
        assertEquals(new Fraction(9f / 8f).toString(), "1⅛");
        assertEquals(new Fraction(10f / 8f).toString(), "1¼");
        assertEquals(new Fraction(11f / 8f).toString(), "1⅜");
        assertEquals(new Fraction(12f / 8f).toString(), "1½");
        assertEquals(new Fraction(13f / 8f).toString(), "1⅝");
        assertEquals(new Fraction(14f / 8f).toString(), "1¾");
        assertEquals(new Fraction(15f / 8f).toString(), "1⅞");
        assertEquals(new Fraction(16f / 8f).toString(), "2");


        assertEquals(new Fraction(1f / 12f).toString(), "1⁄12");

        assertEquals(new Fraction(13f / 12f).toString(), "1\u200B1⁄12");

        assertEquals(new Fraction(0).toString(), "0");
    }

}