package com.noprestige.kanaquiz;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FractionTest
{
    @Test
    public void toStringTest()
    {
        assertThat(new Fraction(0).toString(), is("0"));

        assertThat(new Fraction(1f / 2f).toString(), is("½"));
        assertThat(new Fraction(2f / 2f).toString(), is("1"));
        assertThat(new Fraction(3f / 2f).toString(), is("1½"));
        assertThat(new Fraction(4f / 2f).toString(), is("2"));

        assertThat(new Fraction(1f / 3f).toString(), is("⅓"));
        assertThat(new Fraction(2f / 3f).toString(), is("⅔"));
        assertThat(new Fraction(3f / 3f).toString(), is("1"));
        assertThat(new Fraction(4f / 3f).toString(), is("1⅓"));
        assertThat(new Fraction(5f / 3f).toString(), is("1⅔"));
        assertThat(new Fraction(6f / 3f).toString(), is("2"));

        assertThat(new Fraction(1f / 4f).toString(), is("¼"));
        assertThat(new Fraction(2f / 4f).toString(), is("½"));
        assertThat(new Fraction(3f / 4f).toString(), is("¾"));
        assertThat(new Fraction(4f / 4f).toString(), is("1"));
        assertThat(new Fraction(5f / 4f).toString(), is("1¼"));
        assertThat(new Fraction(6f / 4f).toString(), is("1½"));
        assertThat(new Fraction(7f / 4f).toString(), is("1¾"));
        assertThat(new Fraction(8f / 4f).toString(), is("2"));

        assertThat(new Fraction(1f / 5f).toString(), is("⅕"));
        assertThat(new Fraction(2f / 5f).toString(), is("⅖"));
        assertThat(new Fraction(3f / 5f).toString(), is("⅗"));
        assertThat(new Fraction(4f / 5f).toString(), is("⅘"));
        assertThat(new Fraction(5f / 5f).toString(), is("1"));
        assertThat(new Fraction(6f / 5f).toString(), is("1⅕"));
        assertThat(new Fraction(7f / 5f).toString(), is("1⅖"));
        assertThat(new Fraction(8f / 5f).toString(), is("1⅗"));
        assertThat(new Fraction(9f / 5f).toString(), is("1⅘"));
        assertThat(new Fraction(10f / 5f).toString(), is("2"));

        assertThat(new Fraction(1f / 6f).toString(), is("⅙"));
        assertThat(new Fraction(2f / 6f).toString(), is("⅓"));
        assertThat(new Fraction(3f / 6f).toString(), is("½"));
        assertThat(new Fraction(4f / 6f).toString(), is("⅔"));
        assertThat(new Fraction(5f / 6f).toString(), is("⅚"));
        assertThat(new Fraction(6f / 6f).toString(), is("1"));
        assertThat(new Fraction(7f / 6f).toString(), is("1⅙"));
        assertThat(new Fraction(8f / 6f).toString(), is("1⅓"));
        assertThat(new Fraction(9f / 6f).toString(), is("1½"));
        assertThat(new Fraction(10f / 6f).toString(), is("1⅔"));
        assertThat(new Fraction(11f / 6f).toString(), is("1⅚"));
        assertThat(new Fraction(12f / 6f).toString(), is("2"));

        assertThat(new Fraction(1f / 8f).toString(), is("⅛"));
        assertThat(new Fraction(2f / 8f).toString(), is("¼"));
        assertThat(new Fraction(3f / 8f).toString(), is("⅜"));
        assertThat(new Fraction(4f / 8f).toString(), is("½"));
        assertThat(new Fraction(5f / 8f).toString(), is("⅝"));
        assertThat(new Fraction(6f / 8f).toString(), is("¾"));
        assertThat(new Fraction(7f / 8f).toString(), is("⅞"));
        assertThat(new Fraction(8f / 8f).toString(), is("1"));
        assertThat(new Fraction(9f / 8f).toString(), is("1⅛"));
        assertThat(new Fraction(10f / 8f).toString(), is("1¼"));
        assertThat(new Fraction(11f / 8f).toString(), is("1⅜"));
        assertThat(new Fraction(12f / 8f).toString(), is("1½"));
        assertThat(new Fraction(13f / 8f).toString(), is("1⅝"));
        assertThat(new Fraction(14f / 8f).toString(), is("1¾"));
        assertThat(new Fraction(15f / 8f).toString(), is("1⅞"));
        assertThat(new Fraction(16f / 8f).toString(), is("2"));

        assertThat(new Fraction(1f / 10f).toString(), is("⅒"));
        assertThat(new Fraction(2f / 10f).toString(), is("⅕"));
        assertThat(new Fraction(3f / 10f).toString(), is("3⁄10"));
        assertThat(new Fraction(4f / 10f).toString(), is("⅖"));
        assertThat(new Fraction(5f / 10f).toString(), is("½"));
        assertThat(new Fraction(6f / 10f).toString(), is("⅗"));
        assertThat(new Fraction(7f / 10f).toString(), is("7⁄10"));
        assertThat(new Fraction(8f / 10f).toString(), is("⅘"));
        assertThat(new Fraction(9f / 10f).toString(), is("9⁄10"));
        assertThat(new Fraction(10f / 10f).toString(), is("1"));
        assertThat(new Fraction(11f / 10f).toString(), is("1⅒"));
        assertThat(new Fraction(12f / 10f).toString(), is("1⅕"));
        assertThat(new Fraction(13f / 10f).toString(), is("1\u200B3⁄10"));
        assertThat(new Fraction(14f / 10f).toString(), is("1⅖"));
        assertThat(new Fraction(15f / 10f).toString(), is("1½"));
        assertThat(new Fraction(16f / 10f).toString(), is("1⅗"));
        assertThat(new Fraction(17f / 10f).toString(), is("1\u200B7⁄10"));
        assertThat(new Fraction(18f / 10f).toString(), is("1⅘"));
        assertThat(new Fraction(19f / 10f).toString(), is("1\u200B9⁄10"));
        assertThat(new Fraction(20f / 10f).toString(), is("2"));

        assertThat(new Fraction(1f / 12f).toString(), is("1⁄12"));
        assertThat(new Fraction(2f / 12f).toString(), is("⅙"));
        assertThat(new Fraction(3f / 12f).toString(), is("¼"));
        assertThat(new Fraction(4f / 12f).toString(), is("⅓"));
        assertThat(new Fraction(5f / 12f).toString(), is("5⁄12"));
        assertThat(new Fraction(6f / 12f).toString(), is("½"));
        assertThat(new Fraction(7f / 12f).toString(), is("7⁄12"));
        assertThat(new Fraction(8f / 12f).toString(), is("⅔"));
        assertThat(new Fraction(9f / 12f).toString(), is("¾"));
        assertThat(new Fraction(10f / 12f).toString(), is("⅚"));
        assertThat(new Fraction(11f / 12f).toString(), is("11⁄12"));
        assertThat(new Fraction(12f / 12f).toString(), is("1"));
        assertThat(new Fraction(13f / 12f).toString(), is("1\u200B1⁄12"));
        assertThat(new Fraction(14f / 12f).toString(), is("1⅙"));
        assertThat(new Fraction(15f / 12f).toString(), is("1¼"));
        assertThat(new Fraction(16f / 12f).toString(), is("1⅓"));
        assertThat(new Fraction(17f / 12f).toString(), is("1\u200B5⁄12"));
        assertThat(new Fraction(18f / 12f).toString(), is("1½"));
        assertThat(new Fraction(19f / 12f).toString(), is("1\u200B7⁄12"));
        assertThat(new Fraction(20f / 12f).toString(), is("1⅔"));
        assertThat(new Fraction(21f / 12f).toString(), is("1¾"));
        assertThat(new Fraction(22f / 12f).toString(), is("1⅚"));
        assertThat(new Fraction(23f / 12f).toString(), is("1\u200B11⁄12"));
        assertThat(new Fraction(24f / 12f).toString(), is("2"));

        assertThat(new Fraction(1f / 16f).toString(), is("1⁄16"));
        assertThat(new Fraction(2f / 16f).toString(), is("⅛"));
        assertThat(new Fraction(3f / 16f).toString(), is("3⁄16"));
        assertThat(new Fraction(4f / 16f).toString(), is("¼"));
        assertThat(new Fraction(5f / 16f).toString(), is("5⁄16"));
        assertThat(new Fraction(6f / 16f).toString(), is("⅜"));
        assertThat(new Fraction(7f / 16f).toString(), is("7⁄16"));
        assertThat(new Fraction(8f / 16f).toString(), is("½"));
        assertThat(new Fraction(9f / 16f).toString(), is("9⁄16"));
        assertThat(new Fraction(10f / 16f).toString(), is("⅝"));
        assertThat(new Fraction(11f / 16f).toString(), is("11⁄16"));
        assertThat(new Fraction(12f / 16f).toString(), is("¾"));
        assertThat(new Fraction(13f / 16f).toString(), is("13⁄16"));
        assertThat(new Fraction(14f / 16f).toString(), is("⅞"));
        assertThat(new Fraction(15f / 16f).toString(), is("15⁄16"));
        assertThat(new Fraction(16f / 16f).toString(), is("1"));
        assertThat(new Fraction(17f / 16f).toString(), is("1\u200B1⁄16"));
        assertThat(new Fraction(18f / 16f).toString(), is("1⅛"));
        assertThat(new Fraction(19f / 16f).toString(), is("1\u200B3⁄16"));
        assertThat(new Fraction(20f / 16f).toString(), is("1¼"));
        assertThat(new Fraction(21f / 16f).toString(), is("1\u200B5⁄16"));
        assertThat(new Fraction(22f / 16f).toString(), is("1⅜"));
        assertThat(new Fraction(23f / 16f).toString(), is("1\u200B7⁄16"));
        assertThat(new Fraction(24f / 16f).toString(), is("1½"));
        assertThat(new Fraction(25f / 16f).toString(), is("1\u200B9⁄16"));
        assertThat(new Fraction(26f / 16f).toString(), is("1⅝"));
        assertThat(new Fraction(27f / 16f).toString(), is("1\u200B11⁄16"));
        assertThat(new Fraction(28f / 16f).toString(), is("1¾"));
        assertThat(new Fraction(29f / 16f).toString(), is("1\u200B13⁄16"));
        assertThat(new Fraction(30f / 16f).toString(), is("1⅞"));
        assertThat(new Fraction(31f / 16f).toString(), is("1\u200B15⁄16"));
        assertThat(new Fraction(32f / 16f).toString(), is("2"));
    }
}