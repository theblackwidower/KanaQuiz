package com.noprestige.kanaquiz;

public class Fraction
{
    private int whole;
    private int numerator;
    private int denominator;

    static final private int[] PRIME_NUMBERS = {2, 3, 5};
    static final private int RESOLUTION = 240;

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
            switch (denominator)
            {
                case 2:
                    switch (numerator)
                    {
                        case 1:
                            returnValue.append('½');
                            break;
                    }
                    break;
                case 3:
                    switch (numerator)
                    {
                        case 1:
                            returnValue.append('⅓');
                            break;
                        case 2:
                            returnValue.append('⅔');
                            break;
                    }
                    break;
                case 4:
                    switch (numerator)
                    {
                        case 1:
                            returnValue.append('¼');
                            break;
                        case 3:
                            returnValue.append('¾');
                            break;
                    }
                    break;
                case 5:
                    switch (numerator)
                    {
                        case 1:
                            returnValue.append('⅕');
                            break;
                        case 2:
                            returnValue.append('⅖');
                            break;
                        case 3:
                            returnValue.append('⅗');
                            break;
                        case 4:
                            returnValue.append('⅘');
                            break;
                    }
                    break;
                case 6:
                    switch (numerator)
                    {
                        case 1:
                            returnValue.append('⅙');
                            break;
                        case 5:
                            returnValue.append('⅚');
                            break;
                    }
                    break;
                case 8:
                    switch (numerator)
                    {
                        case 1:
                            returnValue.append('⅛');
                            break;
                        case 3:
                            returnValue.append('⅜');
                            break;
                        case 5:
                            returnValue.append('⅝');
                            break;
                        case 7:
                            returnValue.append('⅞');
                            break;
                    }
                    break;
                default:
                    if (returnValue.length() > 0)
                        returnValue.append('\u200B');
                    returnValue.append(numerator);
                    returnValue.append('⁄');
                    returnValue.append(denominator);
            }
        }
        return returnValue.toString();
    }
}
