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
            if (denominator % prime == 0 && numerator % prime == 0)
            {
                numerator /= prime;
                denominator /= prime;
                simplify();
                break;
            }
        }
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
