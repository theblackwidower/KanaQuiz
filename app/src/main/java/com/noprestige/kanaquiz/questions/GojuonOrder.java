package com.noprestige.kanaquiz.questions;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.Comparator;

public class GojuonOrder implements Comparator<String>
{
    private static class NotRomanjiException extends Exception {}

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
            romanji.setIndex(startIndex);
            return 109;
        }
        finally
        {
            romanji.next();
        }
    }

    private static int getRomanjiKey(CharacterIterator romanji) throws NotRomanjiException
    {
        switch (romanji.current())
        {
            case ' ':
            case CharacterIterator.DONE:
                return -1;
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
                return 5 + getSubKey(romanji);
            case 'g':
                return 13 + getSubKey(romanji);

            case 's':
                if (romanji.next() == 'h')
                    return 21 + getAltIKey(romanji);
                else
                {
                    romanji.previous();
                    return 21 + getSubKey(romanji);
                }
            case 'z':
                return 29 + getSubKey(romanji);
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
            case 'd':
                return 45 + getSubKey(romanji);

            case 'n':
                int startIndex = romanji.getIndex();
                try
                {
                    return 53 + getSubKey(romanji);
                }
                catch (NotRomanjiException ex)
                {
                    romanji.setIndex(startIndex);
                    return 108; //N Consonant
                }

            case 'h':
                return 61 + getSubKey(romanji);
            case 'f':
                if (romanji.next() == 'u')
                    return 61 + 2;
                else
                    break;
            case 'b':
                return 69 + getSubKey(romanji);
            case 'p':
                return 77 + getSubKey(romanji);

            case 'm':
                return 85 + getSubKey(romanji);

            case 'y':
                switch (romanji.next())
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
        switch (romanji.next())
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
                switch (romanji.next())
                {
                    case 'a':
                        return 5;
                    case 'u':
                        return 6;
                    case 'o':
                        return 7;
                }
        }
        throw new NotRomanjiException();
    }

    private static int getAltIKey(CharacterIterator romanji) throws NotRomanjiException
    {
        switch (romanji.next())
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
