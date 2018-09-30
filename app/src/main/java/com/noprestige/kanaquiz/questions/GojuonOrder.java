package com.noprestige.kanaquiz.questions;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.Comparator;

public class GojuonOrder implements Comparator<String>
{
    static class NotRomanjiException extends Exception {}

    static void sort(String[] romanji)
    {
        Arrays.sort(romanji, new GojuonOrder());
    }

    private static int getSortId(CharacterIterator romanji)
    {
        int startIndex = romanji.getIndex();
        try
        {
            return getRomanjiKey(romanji);
        }
        catch (NotRomanjiException ex)
        {
            if (romanji.setIndex(startIndex) == 'n')
                return 108; //N Consonant
            else
                return 109 + romanji.current();
        }
        finally
        {
            romanji.next();
        }
    }

    private static final char[] VOWELS = {'a', 'i', 'u', 'e', 'o'};
    private static final char[] MAIN_SETS = {'k', 'g', '\u0000', 'z', '\u0000', 'd', 'n', 'h', 'b', 'p', 'm'};
    private static final char[] Y_VOWELS = {'a', 'u', 'o'};

    private static int getRomanjiKey(CharacterIterator romanji) throws NotRomanjiException
    {
        for (int i = 0; i < VOWELS.length; i++)
            if (romanji.current() == VOWELS[i])
                return i;

        for (int i = 0; i < MAIN_SETS.length; i++)
            if (romanji.current() == MAIN_SETS[i])
                return 5 + i * 8 + getSubKey(romanji);

        switch (romanji.current())
        {
            case ' ':
            case CharacterIterator.DONE:
                return -1;

            case 's':
                if (romanji.next() == 'h')
                    return 21 + getAltIKey(romanji);
                else
                {
                    romanji.previous();
                    return 21 + getSubKey(romanji);
                }
            case 'j':
                return 29 + getAltIKey(romanji);

            case 't':
                if (romanji.next() == 's')
                {
                    if (romanji.next() == 'u')
                        return 37 + 2;
                    else
                        break;
                }
                else
                {
                    romanji.previous();
                    return 37 + getSubKey(romanji);
                }
            case 'c':
                if (romanji.next() == 'h')
                    return 37 + getAltIKey(romanji);
                else
                    break;

            case 'f':
                if (romanji.next() == 'u')
                    return 61 + 2;
                else
                    break;

            case 'y':
                romanji.next();
                return 93 + getYVowelKey(romanji);

            case 'r':
                return 96 + getSubKey(romanji);

            case 'w':
                switch (romanji.next())
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
        throw new NotRomanjiException();
    }

    private static int getSubKey(CharacterIterator romanji) throws NotRomanjiException
    {
        romanji.next();
        for (int i = 0; i < VOWELS.length; i++)
            if (romanji.current() == VOWELS[i])
                return i;

        if (romanji.current() == 'y')
        {
            romanji.next();
            return 5 + getYVowelKey(romanji);
        }
        throw new NotRomanjiException();
    }

    private static int getAltIKey(CharacterIterator romanji) throws NotRomanjiException
    {
        if (romanji.next() == 'i')
            return 1;

        return 5 + getYVowelKey(romanji);
    }

    private static int getYVowelKey(CharacterIterator romanji) throws NotRomanjiException
    {
        for (int i = 0; i < Y_VOWELS.length; i++)
            if (romanji.current() == Y_VOWELS[i])
                return i;

        throw new NotRomanjiException();
    }

    @Override
    public int compare(String item1, String item2)
    {
        if (item1.equals(item2))
            return 0;
        else
        {
            CharacterIterator item1iterator = new StringCharacterIterator(item1.toLowerCase());
            CharacterIterator item2iterator = new StringCharacterIterator(item2.toLowerCase());
            int item1code = 0;
            int item2code = 0;

            while ((item1iterator.current() != CharacterIterator.DONE ||
                    item2iterator.current() != CharacterIterator.DONE) && item1code == item2code)
            {
                item1code = getSortId(item1iterator);
                item2code = getSortId(item2iterator);
            }

            // for differing same letters, different case.
            if (item1code == item2code)
            {
                item1iterator = new StringCharacterIterator(item1);
                item2iterator = new StringCharacterIterator(item2);

                item1code = item1iterator.current();
                item2code = item2iterator.current();
                while (item1code == item2code)
                {
                    item1code = item1iterator.next();
                    item2code = item2iterator.next();
                }
            }

            return item1code - item2code;
        }
    }
}
