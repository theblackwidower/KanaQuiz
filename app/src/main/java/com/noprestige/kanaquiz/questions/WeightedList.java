package com.noprestige.kanaquiz.questions;

import java.util.Collection;
import java.util.Random;
import java.util.TreeMap;

class WeightedList<E>
{
    private TreeMap<Integer, E> map = new TreeMap<>();
    private int maxValue = 0;

    private static Random rng = new Random();

    boolean add(int weight, E element)
    {
        map.put(maxValue, element);
        maxValue += weight;
        return true;
    }

    boolean add(double weight, E element)
    {
        return this.add((int) Math.ceil(weight), element);
    }

    E remove(int key)
    {
        key = map.floorKey(key);
        E returnValue = map.remove(key);
        if (returnValue == null)
            return null;
        else if (map.higherKey(key) == null)
        {
            maxValue = key;
            return returnValue;
        }
        else
        {
            Integer thisKey = map.higherKey(key);
            int gap = thisKey - key;

            while (thisKey != null)
            {
                E currentElement = map.remove(thisKey);
                map.put(thisKey - gap, currentElement);
                thisKey = map.higherKey(thisKey);
            }

            maxValue -= gap;
            return returnValue;
        }
    }

    int count()
    {
        return map.size();
    }

    E get(int value)
    {
        if (value > maxValue || value < 0)
            throw new NullPointerException();
        else
            return map.get(map.floorKey(value));
    }

    E getRandom()
    {
        return this.get(rng.nextInt(maxValue));
    }

    int maxValue()
    {
        return maxValue;
    }

    boolean merge(WeightedList<E> list)
    {
        for (Integer oldKey : list.map.keySet())
            map.put(this.maxValue + oldKey, list.map.get(oldKey));
        this.maxValue += list.maxValue;

        return true;
    }

    Collection<E> values()
    {
        return map.values();
    }
}
