/*
 *    Copyright 2018 T Duke Perry
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

public class Fraction
{
    private int whole;
    private int numerator;
    private int denominator;

    private static final int[] PRIME_NUMBERS = {2, 3, 5};
    private static final int RESOLUTION = 240;
    private static final char NUL = '\u0000';
    private static final char[][] FRACTION_CHARS = {
            {'½'}, {'⅓', '⅔'}, {'¼', NUL, '¾'}, {'⅕', '⅖', '⅗', '⅘'}, {'⅙', NUL, NUL, NUL, '⅚'}, {'⅐'},
            {'⅛', NUL, '⅜', NUL, '⅝', NUL, '⅞'}, {'⅑'}, {'⅒'}
    };


    public Fraction(float value)
    {
        denominator = RESOLUTION;
        numerator = Math.round(value * (float) denominator);
        whole = numerator / denominator;
        numerator = numerator % denominator;
    }

    public void simplify()
    {
        for (int prime : PRIME_NUMBERS)
        {
            if (((denominator % prime) == 0) && ((numerator % prime) == 0))
            {
                numerator /= prime;
                denominator /= prime;
                simplify();
                break;
            }
        }
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
            simplify();
            char fractionChar;
            try
            {
                fractionChar = FRACTION_CHARS[denominator - 2][numerator - 1];
            }
            catch (ArrayIndexOutOfBoundsException ex)
            {
                fractionChar = NUL;
            }
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
}
