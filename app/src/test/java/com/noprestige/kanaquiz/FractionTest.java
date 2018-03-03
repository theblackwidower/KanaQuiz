package com.noprestige.kanaquiz;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FractionTest
{
    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(new Fraction(3f / 2f).toString(), "1½");

        assertEquals(new Fraction(4f / 3f).toString(), "1⅓");
        assertEquals(new Fraction(5f / 3f).toString(), "1⅔");

        assertEquals(new Fraction(5f / 4f).toString(), "1¼");
        assertEquals(new Fraction(7f / 4f).toString(), "1¾");

        assertEquals(new Fraction(6f / 5f).toString(), "1⅕");
        assertEquals(new Fraction(7f / 5f).toString(), "1⅖");
        assertEquals(new Fraction(8f / 5f).toString(), "1⅗");
        assertEquals(new Fraction(9f / 5f).toString(), "1⅘");

        assertEquals(new Fraction(7f / 6f).toString(), "1⅙");
        assertEquals(new Fraction(11f / 6f).toString(), "1⅚");

        assertEquals(new Fraction(9f / 8f).toString(), "1⅛");
        assertEquals(new Fraction(11f / 8f).toString(), "1⅜");
        assertEquals(new Fraction(13f / 8f).toString(), "1⅝");
        assertEquals(new Fraction(15f / 8f).toString(), "1⅞");


        assertEquals(new Fraction(1f / 2f).toString(), "½");
        assertEquals(new Fraction(1f / 12f).toString(), "1⁄12");

        assertEquals(new Fraction(13f / 12f).toString(), "1\u200B1⁄12");
    }

}