package com.noprestige.kanaquiz;

public class Fraction
{
    private int whole;
    private int numerator;
    private int denominator;

    static final private int[] PRIME_NUMBERS = {2, 3, 5};
    static final private int RESOLUTION = 240;
    static final private char NUL = '\u0000';
    static final private char[][] FRACTION_CHARS = {
            {},
            {NUL, NUL, '½', '⅓', '¼', '⅕', '⅙', NUL, '⅛'},
            {NUL, NUL, NUL, '⅔', NUL, '⅖'},
            {NUL, NUL, NUL, NUL, '¾', '⅗', NUL, NUL, '⅜'},
            {NUL, NUL, NUL, NUL, NUL, '⅘'},
            {NUL, NUL, NUL, NUL, NUL, NUL, '⅚', NUL, '⅝'},
            {},
            {NUL, NUL, NUL, NUL, NUL, NUL, NUL, NUL, '⅞'}};


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

    public String toString()
    {
        simplify();
        StringBuilder returnValue = new StringBuilder();
        if (whole != 0)
            returnValue.append(whole);

        if (numerator != 0)
        {
            char fractionChar;
            try
            {
                fractionChar = FRACTION_CHARS[numerator][denominator];
            }
            catch (ArrayIndexOutOfBoundsException ex)
            {
                fractionChar = NUL;
            }
            if (fractionChar != NUL)
                returnValue.append(fractionChar);
            else
            {
                if (returnValue.length() > 0)
                    returnValue.append('\u200B');
                returnValue.append(numerator);
                returnValue.append('⁄');
                returnValue.append(denominator);
            }
        }
        if (returnValue.length() == 0)
            returnValue.append('0');
        return returnValue.toString();
    }
}
