package com.noprestige.kanaquiz.questions;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.Comparator;

public class GojuonOrder implements Comparator<String>
{
    static void sort(String[] romanji)
    {
        Arrays.sort(romanji, new GojuonOrder());
    }

    private static int getSortId(CharacterIterator romanji)
    {
        try
        {
            return getRomanjiKey(romanji);
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            return 109;
        }
    }

    private static int getRomanjiKey(CharacterIterator romanji)
    {
        switch (romanji.current())
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
                romanji.next();
                return 5 + getSubKey(romanji);
            case 'g':
                romanji.next();
                return 13 + getSubKey(romanji);

            case 's':
                romanji.next();
                if (romanji.current() == 'h')
                {
                    romanji.next();
                    return 21 + getAltIKey(romanji);
                }
                else
                    return 21 + getSubKey(romanji);
            case 'z':
                romanji.next();
                return 29 + getSubKey(romanji);
            case 'j':
                romanji.next();
                return 29 + getAltIKey(romanji);

            case 't':
                romanji.next();
                if (romanji.current() == 's')
                {
                    if (romanji.next() == 'u')
                        return 37 + 2;
                    else
                        break;
                }
                else
                    return 37 + getSubKey(romanji);
            case 'c':
                if (romanji.next() == 'h')
                {
                    romanji.next();
                    return 37 + getAltIKey(romanji);
                }
                else
                    break;
            case 'd':
                romanji.next();
                return 45 + getSubKey(romanji);

            case 'n':
                try
                {
                    romanji.next();
                    return 53 + getSubKey(romanji);
                }
                catch (ArrayIndexOutOfBoundsException ex)
                {
                    romanji.previous();
                    romanji.previous();
                    return 108; //N Consonant
                }

            case 'h':
                romanji.next();
                return 61 + getSubKey(romanji);
            case 'f':
                if (romanji.next() == 'u')
                    return 61 + 2;
                else
                    break;
            case 'b':
                romanji.next();
                return 69 + getSubKey(romanji);
            case 'p':
                romanji.next();
                return 77 + getSubKey(romanji);

            case 'm':
                romanji.next();
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
                romanji.next();
                return 96 + getSubKey(romanji);

            case 'w':
                romanji.next();
                switch (romanji.current())
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

    private static int getSubKey(CharacterIterator romanji)
    {
        switch (romanji.current())
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
        throw new ArrayIndexOutOfBoundsException();
    }

    private static int getAltIKey(CharacterIterator romanji)
    {
        switch (romanji.current())
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
        if (item1.equals(item2))
            return 0;
        else
        {
            CharacterIterator item1iterator = new StringCharacterIterator(item1);
            CharacterIterator item2iterator = new StringCharacterIterator(item2);
            int item1code = 0;
            int item2code = 0;

            while (item1iterator.current() != CharacterIterator.DONE &&
                    item2iterator.current() != CharacterIterator.DONE && item1code == item2code)
            {
                item1code = getSortId(item1iterator);
                item2code = getSortId(item2iterator);
                item1iterator.next();
                item2iterator.next();
            }

            item1iterator.first();
            item2iterator.first();

            while (item1code == item2code)
            {
                item1code = item1iterator.current();
                item2code = item2iterator.current();
                item1iterator.next();
                item2iterator.next();
            }

            return item1code - item2code;
        }
    }
}
