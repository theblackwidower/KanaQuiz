package com.noprestige.kanaquiz.questions;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.Comparator;

public class GojuonOrder implements Comparator<String>
{
    static class NotRomajiException extends Exception {}

    static void sort(String[] romaji)
    {
        Arrays.sort(romaji, new GojuonOrder());
    }

    private static int getSortId(CharacterIterator romaji)
    {
        int startIndex = romaji.getIndex();
        try
        {
            return getRomajiKey(romaji);
        }
        catch (NotRomajiException ex)
        {
            if (romaji.setIndex(startIndex) == 'n')
                return 108; //N Consonant
            else
                return 109 + romaji.current();
        }
        finally
        {
            romaji.next();
        }
    }

    private static final char[] VOWELS = {'a', 'i', 'u', 'e', 'o'};
    private static final char[] MAIN_SETS = {'k', 'g', '\u0000', 'z', '\u0000', 'd', 'n', 'h', 'b', 'p', 'm'};
    private static final char[] Y_VOWELS = {'a', 'u', 'o'};

    private static int getRomajiKey(CharacterIterator romaji) throws NotRomajiException
    {
        for (int i = 0; i < VOWELS.length; i++)
            if (romaji.current() == VOWELS[i])
                return i;

        for (int i = 0; i < MAIN_SETS.length; i++)
            if (romaji.current() == MAIN_SETS[i])
                return 5 + (i * 8) + getSubKey(romaji);

        switch (romaji.current())
        {
            case ' ':
            case CharacterIterator.DONE:
                return -1;

            case 's':
                if (romaji.next() == 'h')
                    return 21 + getAltIKey(romaji);
                else
                {
                    romaji.previous();
                    return 21 + getSubKey(romaji);
                }
            case 'j':
                return 29 + getAltIKey(romaji);

            case 't':
                if (romaji.next() == 's')
                {
                    if (romaji.next() == 'u')
                        return 37 + 2;
                    else
                        break;
                }
                else
                {
                    romaji.previous();
                    return 37 + getSubKey(romaji);
                }
            case 'c':
                if (romaji.next() == 'h')
                    return 37 + getAltIKey(romaji);
                else
                    break;

            case 'f':
                if (romaji.next() == 'u')
                    return 61 + 2;
                else
                    break;

            case 'y':
                romaji.next();
                return 93 + getYVowelKey(romaji);

            case 'r':
                return 96 + getSubKey(romaji);

            case 'w':
                switch (romaji.next())
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
        throw new NotRomajiException();
    }

    private static int getSubKey(CharacterIterator romaji) throws NotRomajiException
    {
        romaji.next();
        for (int i = 0; i < VOWELS.length; i++)
            if (romaji.current() == VOWELS[i])
                return i;

        if (romaji.current() == 'y')
        {
            romaji.next();
            return 5 + getYVowelKey(romaji);
        }
        throw new NotRomajiException();
    }

    private static int getAltIKey(CharacterIterator romaji) throws NotRomajiException
    {
        if (romaji.next() == 'i')
            return 1;

        return 5 + getYVowelKey(romaji);
    }

    private static int getYVowelKey(CharacterIterator romaji) throws NotRomajiException
    {
        for (int i = 0; i < Y_VOWELS.length; i++)
            if (romaji.current() == Y_VOWELS[i])
                return i;

        throw new NotRomajiException();
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

            while (((item1iterator.current() != CharacterIterator.DONE) ||
                    (item2iterator.current() != CharacterIterator.DONE)) && (item1code == item2code))
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
