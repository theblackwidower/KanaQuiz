package com.noprestige.kanaquiz.questions;

import java.util.Arrays;
import java.util.Comparator;

public class GojuonOrder implements Comparator<String>
{
    static void sort(String[] romanji)
    {
        Arrays.sort(romanji, new GojuonOrder());
    }

    private static int getSortId(String romanji)
    {
        try
        {
            return getRomanjiKey(romanji, 0);
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            return 109;
        }
    }

    private static int getRomanjiKey(String romanji, int charIndex)
    {
        switch (romanji.charAt(charIndex))
        {
            case 'a':
                return 0;
            case 'i':
                return 1;
            case 'u':
                return 2;
            case 'e':
                return 3;
            case 'o':
                return 4;

            case 'k':
                return 5 + getSubKey(romanji, charIndex + 1);
            case 'g':
                return 13 + getSubKey(romanji, charIndex + 1);

            case 's':
                if (romanji.charAt(charIndex + 1) == 'h')
                    return 21 + getAltIKey(romanji, charIndex + 2);
                else
                    return 21 + getSubKey(romanji, charIndex + 1);
            case 'z':
                return 29 + getSubKey(romanji, charIndex + 1);
            case 'j':
                return 29 + getAltIKey(romanji, charIndex + 1);

            case 't':
                if (romanji.charAt(charIndex + 1) == 's' && romanji.charAt(charIndex + 2) == 'u')
                    return 37 + 2;
                else
                    return 37 + getSubKey(romanji, charIndex + 1);
            case 'c':
                if (romanji.charAt(charIndex + 1) == 'h')
                    return 37 + getAltIKey(romanji, charIndex + 2);
                else
                    break;
            case 'd':
                return 45 + getSubKey(romanji, charIndex + 1);

            case 'n':
                try
                {
                    return 53 + getSubKey(romanji, charIndex + 1);
                }
                catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException ex)
                {
                    return 108; //N Consonant
                }

            case 'h':
                return 61 + getSubKey(romanji, charIndex + 1);
            case 'f':
                if (romanji.charAt(charIndex + 1) == 'u')
                    return 61 + 2;
                else
                    break;
            case 'b':
                return 69 + getSubKey(romanji, charIndex + 1);
            case 'p':
                return 77 + getSubKey(romanji, charIndex + 1);

            case 'm':
                return 85 + getSubKey(romanji, charIndex + 1);

            case 'y':
                switch (romanji.charAt(charIndex + 1))
                {
                    case 'a':
                        return 93;
                    case 'u':
                        return 94;
                    case 'o':
                        return 95;
                }
                break;

            case 'r':
                return 96 + getSubKey(romanji, charIndex + 1);

            case 'w':
                switch (romanji.charAt(charIndex + 1))
                {
                    case 'a':
                        return 104;
                    case 'i':
                        return 105;
                    case 'e':
                        return 106;
                    case 'o':
                        return 107;
                }
                break;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    private static int getSubKey(String romanji, int charIndex)
    {
        switch (romanji.charAt(charIndex))
        {
            case 'a':
                return 0;
            case 'i':
                return 1;
            case 'u':
                return 2;
            case 'e':
                return 3;
            case 'o':
                return 4;
            case 'y':
                switch (romanji.charAt(charIndex + 1))
                {
                    case 'a':
                        return 5;
                    case 'u':
                        return 6;
                    case 'o':
                        return 7;
                }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    private static int getAltIKey(String romanji, int charIndex)
    {
        switch (romanji.charAt(charIndex))
        {
            case 'i':
                return 1;

            case 'a':
                return 5;
            case 'u':
                return 6;
            case 'o':
                return 7;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public int compare(String item1, String item2)
    {
        return getSortId(item1) - getSortId(item2);
    }
}
