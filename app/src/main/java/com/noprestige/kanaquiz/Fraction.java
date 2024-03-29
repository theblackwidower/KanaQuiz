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

import java.lang.reflect.Array;

public class Fraction implements Comparable<Fraction>
{
    public final int whole;
    public final int numerator;
    public final int denominator;

    private static final float RESOLUTION = 1.52587890625e-05f; //2^-16
    private static final int MAX_DENOMINATOR = 256;
    private static final char NUL = '\u0000';
    private static final char[][] FRACTION_CHARS = {
            {'½'}, {'⅓', '⅔'}, {'¼', NUL, '¾'}, {'⅕', '⅖', '⅗', '⅘'}, {'⅙', NUL, NUL, NUL, '⅚'}, {'⅐'},
            {'⅛', NUL, '⅜', NUL, '⅝', NUL, '⅞'}, {'⅑'}, {'⅒'}
    };

    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 0, 1);
    public static final Fraction HUNDRED = new Fraction(100, 0, 1);

    public static Fraction fromFloat(float value)
    {
        int whole = (int) Math.floor(value);
        value -= whole;
        if (value < RESOLUTION) //fractional part is zero
            return new Fraction(whole, 0, 1);
        if ((1f - value) < RESOLUTION) //fractional part is 1
            return new Fraction(whole + 1, 0, 1);
        else
            for (int i = 2; i < MAX_DENOMINATOR; i++)
                for (int j = 1; j < i; j++)
                    if (Math.abs(((float) j / (float) i) - value) < RESOLUTION)
                        return new Fraction(whole, j, i);
        return new Fraction(whole, Math.round(value * (float) MAX_DENOMINATOR), MAX_DENOMINATOR);
    }

    public Fraction(int numerator, int denominator)
    {
        this(0, numerator, denominator);
    }

    public Fraction(int whole, int numerator, int denominator)
    {
        if (numerator >= denominator)
        {
            whole += numerator / denominator;
            numerator %= denominator;
        }

        if (numerator != 0)
        {
            int divisor = GCD(numerator, denominator);

            if (divisor > 1)
            {
                numerator /= divisor;
                denominator /= divisor;
            }
        }

        this.whole = whole;
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static int GCD(int num1, int num2)
    {
        while (num1 != num2)
        {
            if (num1 < num2)
                num2 -= num1;
            else
                num1 -= num2;
        }
        return num1;
    }

    public Fraction add(int num)
    {
        return new Fraction(whole + num, numerator, denominator);
    }

    public Fraction add(Fraction num)
    {
        int newNumerator = (numerator * num.denominator) + (num.numerator * denominator);
        int newDenominator = denominator * num.denominator;
        return new Fraction(whole + num.whole, newNumerator, newDenominator);
    }

    public Fraction subtract(int num)
    {
        return new Fraction(whole - num, numerator, denominator);
    }

    public Fraction subtract(Fraction num)
    {
        int newNumerator = (numerator * num.denominator) - (num.numerator * denominator);
        int newDenominator = denominator * num.denominator;
        return new Fraction(whole - num.whole, newNumerator, newDenominator);
    }

    public Fraction multiply(int num)
    {
        return new Fraction(whole * num, numerator * num, denominator);
    }

    public Fraction multiply(Fraction num)
    {
        int newWhole = whole * num.whole;
        int newNumerator = numerator * num.numerator;
        int newDenominator = denominator * num.denominator;
        newNumerator += whole * denominator * num.numerator;
        newNumerator += num.whole * num.denominator * numerator;
        return new Fraction(newWhole, newNumerator, newDenominator);
    }

    public Fraction divide(int num)
    {
        return new Fraction((whole * denominator) + numerator, denominator * num);
    }

    public Fraction divide(Fraction num)
    {
        return multiply(num.getReciprocal());
    }

    public Fraction getReciprocal()
    {
        return new Fraction(denominator, (whole * denominator) + numerator);
    }

    public float getDecimal()
    {
        return whole + ((float) numerator / (float) denominator);
    }

    @Override
    public String toString()
    {
        StringBuilder returnValue = new StringBuilder();
        if (whole != 0)
            returnValue.append(whole);

        if (numerator != 0)
        {
            char fractionChar;
            if (((denominator - 2) < Array.getLength(FRACTION_CHARS)) &&
                    ((numerator - 1) < Array.getLength(FRACTION_CHARS[denominator - 2])))
                fractionChar = FRACTION_CHARS[denominator - 2][numerator - 1];
            else
                fractionChar = NUL;
            if (fractionChar == NUL)
            {
                if (returnValue.length() > 0)
                    returnValue.append('\u200B');
                returnValue.append(numerator);
                returnValue.append('⁄');
                returnValue.append(denominator);
            }
            else
                returnValue.append(fractionChar);
        }
        if (returnValue.length() == 0)
            returnValue.append('0');
        return returnValue.toString();
    }

    public static Fraction parse(String string)
    {
        int numerator = Integer.MIN_VALUE;
        int denominator = Integer.MIN_VALUE;
        int fractionChar = -1;
        for (int i = 0; (fractionChar == -1) && (i < Array.getLength(FRACTION_CHARS)); i++)
            for (int j = 0; (fractionChar == -1) && (j < Array.getLength(FRACTION_CHARS[i])); j++)
                if (FRACTION_CHARS[i][j] != NUL)
                {
                    fractionChar = string.indexOf(FRACTION_CHARS[i][j]);
                    if (fractionChar > -1)
                    {
                        denominator = i + 2;
                        numerator = j + 1;
                    }
                }
        int whole;
        if (fractionChar == 0)
            return new Fraction(numerator, denominator);
        else if (fractionChar > 0)
        {
            whole = Integer.parseInt(string.substring(0, fractionChar - 1));
            return new Fraction(whole, numerator, denominator);
        }
        int slash = string.indexOf("⁄");
        if (slash > -1)
        {
            int splitter = string.indexOf("\u200B");
            numerator = Integer.parseInt(string.substring(splitter + 1, slash - 1));
            denominator = Integer.parseInt(string.substring(slash + 1));
            if (splitter == -1)
                whole = 0;
            else
                whole = Integer.parseInt(string.substring(0, splitter - 1));
            return new Fraction(whole, numerator, denominator);
        }
        return new Fraction(Integer.parseInt(string), 0, 1);
    }

    public int round()
    {
        int returnValue = whole;
        if ((numerator * 2) >= denominator)
            returnValue++;
        return returnValue;
    }

    @Override
    public int compareTo(Fraction o)
    {
        int returnValue = whole - o.whole;
        if (returnValue == 0)
        {
            if (denominator == o.denominator)
                returnValue = numerator - o.numerator;
            else
                returnValue = (numerator * o.denominator) - (o.numerator * denominator);
        }
        return returnValue;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Fraction))
            return false;
        return (whole == ((Fraction) o).whole) && (numerator == ((Fraction) o).numerator) &&
                (denominator == ((Fraction) o).denominator);
    }

    @Override
    public int hashCode()
    {
        return (((whole << 8) + numerator) << 8) + denominator;
    }
}
