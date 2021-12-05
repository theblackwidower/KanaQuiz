/*
 *    Copyright 2021 T Duke Perry
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.noprestige.kanaquiz;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FractionTest
{
    @Test
    public void toStringTest()
    {
        assertThat(Fraction.fromFloat(0).toString(), is("0"));

        assertThat(Fraction.fromFloat(1f / 2f).toString(), is("½"));
        assertThat(Fraction.fromFloat(2f / 2f).toString(), is("1"));
        assertThat(Fraction.fromFloat(3f / 2f).toString(), is("1½"));
        assertThat(Fraction.fromFloat(4f / 2f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 3f).toString(), is("⅓"));
        assertThat(Fraction.fromFloat(2f / 3f).toString(), is("⅔"));
        assertThat(Fraction.fromFloat(3f / 3f).toString(), is("1"));
        assertThat(Fraction.fromFloat(4f / 3f).toString(), is("1⅓"));
        assertThat(Fraction.fromFloat(5f / 3f).toString(), is("1⅔"));
        assertThat(Fraction.fromFloat(6f / 3f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 4f).toString(), is("¼"));
        assertThat(Fraction.fromFloat(2f / 4f).toString(), is("½"));
        assertThat(Fraction.fromFloat(3f / 4f).toString(), is("¾"));
        assertThat(Fraction.fromFloat(4f / 4f).toString(), is("1"));
        assertThat(Fraction.fromFloat(5f / 4f).toString(), is("1¼"));
        assertThat(Fraction.fromFloat(6f / 4f).toString(), is("1½"));
        assertThat(Fraction.fromFloat(7f / 4f).toString(), is("1¾"));
        assertThat(Fraction.fromFloat(8f / 4f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 5f).toString(), is("⅕"));
        assertThat(Fraction.fromFloat(2f / 5f).toString(), is("⅖"));
        assertThat(Fraction.fromFloat(3f / 5f).toString(), is("⅗"));
        assertThat(Fraction.fromFloat(4f / 5f).toString(), is("⅘"));
        assertThat(Fraction.fromFloat(5f / 5f).toString(), is("1"));
        assertThat(Fraction.fromFloat(6f / 5f).toString(), is("1⅕"));
        assertThat(Fraction.fromFloat(7f / 5f).toString(), is("1⅖"));
        assertThat(Fraction.fromFloat(8f / 5f).toString(), is("1⅗"));
        assertThat(Fraction.fromFloat(9f / 5f).toString(), is("1⅘"));
        assertThat(Fraction.fromFloat(10f / 5f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 6f).toString(), is("⅙"));
        assertThat(Fraction.fromFloat(2f / 6f).toString(), is("⅓"));
        assertThat(Fraction.fromFloat(3f / 6f).toString(), is("½"));
        assertThat(Fraction.fromFloat(4f / 6f).toString(), is("⅔"));
        assertThat(Fraction.fromFloat(5f / 6f).toString(), is("⅚"));
        assertThat(Fraction.fromFloat(6f / 6f).toString(), is("1"));
        assertThat(Fraction.fromFloat(7f / 6f).toString(), is("1⅙"));
        assertThat(Fraction.fromFloat(8f / 6f).toString(), is("1⅓"));
        assertThat(Fraction.fromFloat(9f / 6f).toString(), is("1½"));
        assertThat(Fraction.fromFloat(10f / 6f).toString(), is("1⅔"));
        assertThat(Fraction.fromFloat(11f / 6f).toString(), is("1⅚"));
        assertThat(Fraction.fromFloat(12f / 6f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 7f).toString(), is("⅐"));
        assertThat(Fraction.fromFloat(2f / 7f).toString(), is("2⁄7"));
        assertThat(Fraction.fromFloat(3f / 7f).toString(), is("3⁄7"));
        assertThat(Fraction.fromFloat(4f / 7f).toString(), is("4⁄7"));
        assertThat(Fraction.fromFloat(5f / 7f).toString(), is("5⁄7"));
        assertThat(Fraction.fromFloat(6f / 7f).toString(), is("6⁄7"));
        assertThat(Fraction.fromFloat(7f / 7f).toString(), is("1"));
        assertThat(Fraction.fromFloat(8f / 7f).toString(), is("1⅐"));
        assertThat(Fraction.fromFloat(9f / 7f).toString(), is("1\u200B2⁄7"));
        assertThat(Fraction.fromFloat(10f / 7f).toString(), is("1\u200B3⁄7"));
        assertThat(Fraction.fromFloat(11f / 7f).toString(), is("1\u200B4⁄7"));
        assertThat(Fraction.fromFloat(12f / 7f).toString(), is("1\u200B5⁄7"));
        assertThat(Fraction.fromFloat(13f / 7f).toString(), is("1\u200B6⁄7"));
        assertThat(Fraction.fromFloat(14f / 7f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 8f).toString(), is("⅛"));
        assertThat(Fraction.fromFloat(2f / 8f).toString(), is("¼"));
        assertThat(Fraction.fromFloat(3f / 8f).toString(), is("⅜"));
        assertThat(Fraction.fromFloat(4f / 8f).toString(), is("½"));
        assertThat(Fraction.fromFloat(5f / 8f).toString(), is("⅝"));
        assertThat(Fraction.fromFloat(6f / 8f).toString(), is("¾"));
        assertThat(Fraction.fromFloat(7f / 8f).toString(), is("⅞"));
        assertThat(Fraction.fromFloat(8f / 8f).toString(), is("1"));
        assertThat(Fraction.fromFloat(9f / 8f).toString(), is("1⅛"));
        assertThat(Fraction.fromFloat(10f / 8f).toString(), is("1¼"));
        assertThat(Fraction.fromFloat(11f / 8f).toString(), is("1⅜"));
        assertThat(Fraction.fromFloat(12f / 8f).toString(), is("1½"));
        assertThat(Fraction.fromFloat(13f / 8f).toString(), is("1⅝"));
        assertThat(Fraction.fromFloat(14f / 8f).toString(), is("1¾"));
        assertThat(Fraction.fromFloat(15f / 8f).toString(), is("1⅞"));
        assertThat(Fraction.fromFloat(16f / 8f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 9f).toString(), is("⅑"));
        assertThat(Fraction.fromFloat(2f / 9f).toString(), is("2⁄9"));
        assertThat(Fraction.fromFloat(3f / 9f).toString(), is("⅓"));
        assertThat(Fraction.fromFloat(4f / 9f).toString(), is("4⁄9"));
        assertThat(Fraction.fromFloat(5f / 9f).toString(), is("5⁄9"));
        assertThat(Fraction.fromFloat(6f / 9f).toString(), is("⅔"));
        assertThat(Fraction.fromFloat(7f / 9f).toString(), is("7⁄9"));
        assertThat(Fraction.fromFloat(8f / 9f).toString(), is("8⁄9"));
        assertThat(Fraction.fromFloat(9f / 9f).toString(), is("1"));
        assertThat(Fraction.fromFloat(10f / 9f).toString(), is("1⅑"));
        assertThat(Fraction.fromFloat(11f / 9f).toString(), is("1\u200B2⁄9"));
        assertThat(Fraction.fromFloat(12f / 9f).toString(), is("1⅓"));
        assertThat(Fraction.fromFloat(13f / 9f).toString(), is("1\u200B4⁄9"));
        assertThat(Fraction.fromFloat(14f / 9f).toString(), is("1\u200B5⁄9"));
        assertThat(Fraction.fromFloat(15f / 9f).toString(), is("1⅔"));
        assertThat(Fraction.fromFloat(16f / 9f).toString(), is("1\u200B7⁄9"));
        assertThat(Fraction.fromFloat(17f / 9f).toString(), is("1\u200B8⁄9"));
        assertThat(Fraction.fromFloat(18f / 9f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 10f).toString(), is("⅒"));
        assertThat(Fraction.fromFloat(2f / 10f).toString(), is("⅕"));
        assertThat(Fraction.fromFloat(3f / 10f).toString(), is("3⁄10"));
        assertThat(Fraction.fromFloat(4f / 10f).toString(), is("⅖"));
        assertThat(Fraction.fromFloat(5f / 10f).toString(), is("½"));
        assertThat(Fraction.fromFloat(6f / 10f).toString(), is("⅗"));
        assertThat(Fraction.fromFloat(7f / 10f).toString(), is("7⁄10"));
        assertThat(Fraction.fromFloat(8f / 10f).toString(), is("⅘"));
        assertThat(Fraction.fromFloat(9f / 10f).toString(), is("9⁄10"));
        assertThat(Fraction.fromFloat(10f / 10f).toString(), is("1"));
        assertThat(Fraction.fromFloat(11f / 10f).toString(), is("1⅒"));
        assertThat(Fraction.fromFloat(12f / 10f).toString(), is("1⅕"));
        assertThat(Fraction.fromFloat(13f / 10f).toString(), is("1\u200B3⁄10"));
        assertThat(Fraction.fromFloat(14f / 10f).toString(), is("1⅖"));
        assertThat(Fraction.fromFloat(15f / 10f).toString(), is("1½"));
        assertThat(Fraction.fromFloat(16f / 10f).toString(), is("1⅗"));
        assertThat(Fraction.fromFloat(17f / 10f).toString(), is("1\u200B7⁄10"));
        assertThat(Fraction.fromFloat(18f / 10f).toString(), is("1⅘"));
        assertThat(Fraction.fromFloat(19f / 10f).toString(), is("1\u200B9⁄10"));
        assertThat(Fraction.fromFloat(20f / 10f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 11f).toString(), is("1⁄11"));
        assertThat(Fraction.fromFloat(2f / 11f).toString(), is("2⁄11"));
        assertThat(Fraction.fromFloat(3f / 11f).toString(), is("3⁄11"));
        assertThat(Fraction.fromFloat(4f / 11f).toString(), is("4⁄11"));
        assertThat(Fraction.fromFloat(5f / 11f).toString(), is("5⁄11"));
        assertThat(Fraction.fromFloat(6f / 11f).toString(), is("6⁄11"));
        assertThat(Fraction.fromFloat(7f / 11f).toString(), is("7⁄11"));
        assertThat(Fraction.fromFloat(8f / 11f).toString(), is("8⁄11"));
        assertThat(Fraction.fromFloat(9f / 11f).toString(), is("9⁄11"));
        assertThat(Fraction.fromFloat(10f / 11f).toString(), is("10⁄11"));
        assertThat(Fraction.fromFloat(11f / 11f).toString(), is("1"));
        assertThat(Fraction.fromFloat(12f / 11f).toString(), is("1\u200B1⁄11"));
        assertThat(Fraction.fromFloat(13f / 11f).toString(), is("1\u200B2⁄11"));
        assertThat(Fraction.fromFloat(14f / 11f).toString(), is("1\u200B3⁄11"));
        assertThat(Fraction.fromFloat(15f / 11f).toString(), is("1\u200B4⁄11"));
        assertThat(Fraction.fromFloat(16f / 11f).toString(), is("1\u200B5⁄11"));
        assertThat(Fraction.fromFloat(17f / 11f).toString(), is("1\u200B6⁄11"));
        assertThat(Fraction.fromFloat(18f / 11f).toString(), is("1\u200B7⁄11"));
        assertThat(Fraction.fromFloat(19f / 11f).toString(), is("1\u200B8⁄11"));
        assertThat(Fraction.fromFloat(20f / 11f).toString(), is("1\u200B9⁄11"));
        assertThat(Fraction.fromFloat(21f / 11f).toString(), is("1\u200B10⁄11"));
        assertThat(Fraction.fromFloat(22f / 11f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 12f).toString(), is("1⁄12"));
        assertThat(Fraction.fromFloat(2f / 12f).toString(), is("⅙"));
        assertThat(Fraction.fromFloat(3f / 12f).toString(), is("¼"));
        assertThat(Fraction.fromFloat(4f / 12f).toString(), is("⅓"));
        assertThat(Fraction.fromFloat(5f / 12f).toString(), is("5⁄12"));
        assertThat(Fraction.fromFloat(6f / 12f).toString(), is("½"));
        assertThat(Fraction.fromFloat(7f / 12f).toString(), is("7⁄12"));
        assertThat(Fraction.fromFloat(8f / 12f).toString(), is("⅔"));
        assertThat(Fraction.fromFloat(9f / 12f).toString(), is("¾"));
        assertThat(Fraction.fromFloat(10f / 12f).toString(), is("⅚"));
        assertThat(Fraction.fromFloat(11f / 12f).toString(), is("11⁄12"));
        assertThat(Fraction.fromFloat(12f / 12f).toString(), is("1"));
        assertThat(Fraction.fromFloat(13f / 12f).toString(), is("1\u200B1⁄12"));
        assertThat(Fraction.fromFloat(14f / 12f).toString(), is("1⅙"));
        assertThat(Fraction.fromFloat(15f / 12f).toString(), is("1¼"));
        assertThat(Fraction.fromFloat(16f / 12f).toString(), is("1⅓"));
        assertThat(Fraction.fromFloat(17f / 12f).toString(), is("1\u200B5⁄12"));
        assertThat(Fraction.fromFloat(18f / 12f).toString(), is("1½"));
        assertThat(Fraction.fromFloat(19f / 12f).toString(), is("1\u200B7⁄12"));
        assertThat(Fraction.fromFloat(20f / 12f).toString(), is("1⅔"));
        assertThat(Fraction.fromFloat(21f / 12f).toString(), is("1¾"));
        assertThat(Fraction.fromFloat(22f / 12f).toString(), is("1⅚"));
        assertThat(Fraction.fromFloat(23f / 12f).toString(), is("1\u200B11⁄12"));
        assertThat(Fraction.fromFloat(24f / 12f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 13f).toString(), is("1⁄13"));
        assertThat(Fraction.fromFloat(2f / 13f).toString(), is("2⁄13"));
        assertThat(Fraction.fromFloat(3f / 13f).toString(), is("3⁄13"));
        assertThat(Fraction.fromFloat(4f / 13f).toString(), is("4⁄13"));
        assertThat(Fraction.fromFloat(5f / 13f).toString(), is("5⁄13"));
        assertThat(Fraction.fromFloat(6f / 13f).toString(), is("6⁄13"));
        assertThat(Fraction.fromFloat(7f / 13f).toString(), is("7⁄13"));
        assertThat(Fraction.fromFloat(8f / 13f).toString(), is("8⁄13"));
        assertThat(Fraction.fromFloat(9f / 13f).toString(), is("9⁄13"));
        assertThat(Fraction.fromFloat(10f / 13f).toString(), is("10⁄13"));
        assertThat(Fraction.fromFloat(11f / 13f).toString(), is("11⁄13"));
        assertThat(Fraction.fromFloat(12f / 13f).toString(), is("12⁄13"));
        assertThat(Fraction.fromFloat(13f / 13f).toString(), is("1"));
        assertThat(Fraction.fromFloat(14f / 13f).toString(), is("1\u200B1⁄13"));
        assertThat(Fraction.fromFloat(15f / 13f).toString(), is("1\u200B2⁄13"));
        assertThat(Fraction.fromFloat(16f / 13f).toString(), is("1\u200B3⁄13"));
        assertThat(Fraction.fromFloat(17f / 13f).toString(), is("1\u200B4⁄13"));
        assertThat(Fraction.fromFloat(18f / 13f).toString(), is("1\u200B5⁄13"));
        assertThat(Fraction.fromFloat(19f / 13f).toString(), is("1\u200B6⁄13"));
        assertThat(Fraction.fromFloat(20f / 13f).toString(), is("1\u200B7⁄13"));
        assertThat(Fraction.fromFloat(21f / 13f).toString(), is("1\u200B8⁄13"));
        assertThat(Fraction.fromFloat(22f / 13f).toString(), is("1\u200B9⁄13"));
        assertThat(Fraction.fromFloat(23f / 13f).toString(), is("1\u200B10⁄13"));
        assertThat(Fraction.fromFloat(24f / 13f).toString(), is("1\u200B11⁄13"));
        assertThat(Fraction.fromFloat(25f / 13f).toString(), is("1\u200B12⁄13"));
        assertThat(Fraction.fromFloat(26f / 13f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 14f).toString(), is("1⁄14"));
        assertThat(Fraction.fromFloat(2f / 14f).toString(), is("⅐"));
        assertThat(Fraction.fromFloat(3f / 14f).toString(), is("3⁄14"));
        assertThat(Fraction.fromFloat(4f / 14f).toString(), is("2⁄7"));
        assertThat(Fraction.fromFloat(5f / 14f).toString(), is("5⁄14"));
        assertThat(Fraction.fromFloat(6f / 14f).toString(), is("3⁄7"));
        assertThat(Fraction.fromFloat(7f / 14f).toString(), is("½"));
        assertThat(Fraction.fromFloat(8f / 14f).toString(), is("4⁄7"));
        assertThat(Fraction.fromFloat(9f / 14f).toString(), is("9⁄14"));
        assertThat(Fraction.fromFloat(10f / 14f).toString(), is("5⁄7"));
        assertThat(Fraction.fromFloat(11f / 14f).toString(), is("11⁄14"));
        assertThat(Fraction.fromFloat(12f / 14f).toString(), is("6⁄7"));
        assertThat(Fraction.fromFloat(13f / 14f).toString(), is("13⁄14"));
        assertThat(Fraction.fromFloat(14f / 14f).toString(), is("1"));
        assertThat(Fraction.fromFloat(15f / 14f).toString(), is("1\u200B1⁄14"));
        assertThat(Fraction.fromFloat(16f / 14f).toString(), is("1⅐"));
        assertThat(Fraction.fromFloat(17f / 14f).toString(), is("1\u200B3⁄14"));
        assertThat(Fraction.fromFloat(18f / 14f).toString(), is("1\u200B2⁄7"));
        assertThat(Fraction.fromFloat(19f / 14f).toString(), is("1\u200B5⁄14"));
        assertThat(Fraction.fromFloat(20f / 14f).toString(), is("1\u200B3⁄7"));
        assertThat(Fraction.fromFloat(21f / 14f).toString(), is("1½"));
        assertThat(Fraction.fromFloat(22f / 14f).toString(), is("1\u200B4⁄7"));
        assertThat(Fraction.fromFloat(23f / 14f).toString(), is("1\u200B9⁄14"));
        assertThat(Fraction.fromFloat(24f / 14f).toString(), is("1\u200B5⁄7"));
        assertThat(Fraction.fromFloat(25f / 14f).toString(), is("1\u200B11⁄14"));
        assertThat(Fraction.fromFloat(26f / 14f).toString(), is("1\u200B6⁄7"));
        assertThat(Fraction.fromFloat(27f / 14f).toString(), is("1\u200B13⁄14"));
        assertThat(Fraction.fromFloat(28f / 14f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 15f).toString(), is("1⁄15"));
        assertThat(Fraction.fromFloat(2f / 15f).toString(), is("2⁄15"));
        assertThat(Fraction.fromFloat(3f / 15f).toString(), is("⅕"));
        assertThat(Fraction.fromFloat(4f / 15f).toString(), is("4⁄15"));
        assertThat(Fraction.fromFloat(5f / 15f).toString(), is("⅓"));
        assertThat(Fraction.fromFloat(6f / 15f).toString(), is("⅖"));
        assertThat(Fraction.fromFloat(7f / 15f).toString(), is("7⁄15"));
        assertThat(Fraction.fromFloat(8f / 15f).toString(), is("8⁄15"));
        assertThat(Fraction.fromFloat(9f / 15f).toString(), is("⅗"));
        assertThat(Fraction.fromFloat(10f / 15f).toString(), is("⅔"));
        assertThat(Fraction.fromFloat(11f / 15f).toString(), is("11⁄15"));
        assertThat(Fraction.fromFloat(12f / 15f).toString(), is("⅘"));
        assertThat(Fraction.fromFloat(13f / 15f).toString(), is("13⁄15"));
        assertThat(Fraction.fromFloat(14f / 15f).toString(), is("14⁄15"));
        assertThat(Fraction.fromFloat(15f / 15f).toString(), is("1"));
        assertThat(Fraction.fromFloat(16f / 15f).toString(), is("1\u200B1⁄15"));
        assertThat(Fraction.fromFloat(17f / 15f).toString(), is("1\u200B2⁄15"));
        assertThat(Fraction.fromFloat(18f / 15f).toString(), is("1⅕"));
        assertThat(Fraction.fromFloat(19f / 15f).toString(), is("1\u200B4⁄15"));
        assertThat(Fraction.fromFloat(20f / 15f).toString(), is("1⅓"));
        assertThat(Fraction.fromFloat(21f / 15f).toString(), is("1⅖"));
        assertThat(Fraction.fromFloat(22f / 15f).toString(), is("1\u200B7⁄15"));
        assertThat(Fraction.fromFloat(23f / 15f).toString(), is("1\u200B8⁄15"));
        assertThat(Fraction.fromFloat(24f / 15f).toString(), is("1⅗"));
        assertThat(Fraction.fromFloat(25f / 15f).toString(), is("1⅔"));
        assertThat(Fraction.fromFloat(26f / 15f).toString(), is("1\u200B11⁄15"));
        assertThat(Fraction.fromFloat(27f / 15f).toString(), is("1⅘"));
        assertThat(Fraction.fromFloat(28f / 15f).toString(), is("1\u200B13⁄15"));
        assertThat(Fraction.fromFloat(29f / 15f).toString(), is("1\u200B14⁄15"));
        assertThat(Fraction.fromFloat(30f / 15f).toString(), is("2"));

        assertThat(Fraction.fromFloat(1f / 16f).toString(), is("1⁄16"));
        assertThat(Fraction.fromFloat(2f / 16f).toString(), is("⅛"));
        assertThat(Fraction.fromFloat(3f / 16f).toString(), is("3⁄16"));
        assertThat(Fraction.fromFloat(4f / 16f).toString(), is("¼"));
        assertThat(Fraction.fromFloat(5f / 16f).toString(), is("5⁄16"));
        assertThat(Fraction.fromFloat(6f / 16f).toString(), is("⅜"));
        assertThat(Fraction.fromFloat(7f / 16f).toString(), is("7⁄16"));
        assertThat(Fraction.fromFloat(8f / 16f).toString(), is("½"));
        assertThat(Fraction.fromFloat(9f / 16f).toString(), is("9⁄16"));
        assertThat(Fraction.fromFloat(10f / 16f).toString(), is("⅝"));
        assertThat(Fraction.fromFloat(11f / 16f).toString(), is("11⁄16"));
        assertThat(Fraction.fromFloat(12f / 16f).toString(), is("¾"));
        assertThat(Fraction.fromFloat(13f / 16f).toString(), is("13⁄16"));
        assertThat(Fraction.fromFloat(14f / 16f).toString(), is("⅞"));
        assertThat(Fraction.fromFloat(15f / 16f).toString(), is("15⁄16"));
        assertThat(Fraction.fromFloat(16f / 16f).toString(), is("1"));
        assertThat(Fraction.fromFloat(17f / 16f).toString(), is("1\u200B1⁄16"));
        assertThat(Fraction.fromFloat(18f / 16f).toString(), is("1⅛"));
        assertThat(Fraction.fromFloat(19f / 16f).toString(), is("1\u200B3⁄16"));
        assertThat(Fraction.fromFloat(20f / 16f).toString(), is("1¼"));
        assertThat(Fraction.fromFloat(21f / 16f).toString(), is("1\u200B5⁄16"));
        assertThat(Fraction.fromFloat(22f / 16f).toString(), is("1⅜"));
        assertThat(Fraction.fromFloat(23f / 16f).toString(), is("1\u200B7⁄16"));
        assertThat(Fraction.fromFloat(24f / 16f).toString(), is("1½"));
        assertThat(Fraction.fromFloat(25f / 16f).toString(), is("1\u200B9⁄16"));
        assertThat(Fraction.fromFloat(26f / 16f).toString(), is("1⅝"));
        assertThat(Fraction.fromFloat(27f / 16f).toString(), is("1\u200B11⁄16"));
        assertThat(Fraction.fromFloat(28f / 16f).toString(), is("1¾"));
        assertThat(Fraction.fromFloat(29f / 16f).toString(), is("1\u200B13⁄16"));
        assertThat(Fraction.fromFloat(30f / 16f).toString(), is("1⅞"));
        assertThat(Fraction.fromFloat(31f / 16f).toString(), is("1\u200B15⁄16"));
        assertThat(Fraction.fromFloat(32f / 16f).toString(), is("2"));

        for (int i = 17; i <= 256; i++)
        {
            assertThat(Fraction.fromFloat(1f / i).toString(), is("1⁄" + i));
            assertThat(Fraction.fromFloat((i - 1f) / i).toString(), is((i - 1) + "⁄" + i));

            assertThat(Fraction.fromFloat((i + 1f) / i).toString(), is("1\u200B1⁄" + i));
            assertThat(Fraction.fromFloat(((2 * i) - 1f) / i).toString(), is("1\u200B" + (i - 1) + "⁄" + i));
        }
    }
}