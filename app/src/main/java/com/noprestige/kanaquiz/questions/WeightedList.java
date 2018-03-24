package com.noprestige.kanaquiz.questions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

class WeightedList<E> implements Cloneable
{
    private TreeMap<Integer, E> map = new TreeMap<>();
    private int maxValue;

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
        if (key >= maxValue || key < 0)
            throw new IndexOutOfBoundsException();
        else
        {
            key = map.floorKey(key);
            E returnValue = map.remove(key);

            if (map.higherKey(key) == null)
                maxValue = key;
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
            }
            return returnValue;
        }
    }

    boolean adjustWeight(int key, int adjustment)
    {
        if (key >= maxValue || key < 0)
            throw new IndexOutOfBoundsException();
        else
        {
            if (getWeight(key) + adjustment <= 0)
                return false;
            else
            {
                List<Integer> indexList = new ArrayList<>();
                List<E> elementList = new ArrayList<>();

                for (Integer thisKey = map.higherKey(key); thisKey != null; thisKey = map.higherKey(thisKey))
                {
                    indexList.add(thisKey + adjustment);
                    elementList.add(map.remove(thisKey));
                }

                for (int i = 0; i < indexList.size(); i++)
                    map.put(indexList.get(i), elementList.get(i));

                maxValue += adjustment;

                return true;
            }
        }
    }

    int count()
    {
        return map.size();
    }

    E get(int value)
    {
        if (value >= maxValue || value < 0)
            throw new IndexOutOfBoundsException();
        else
            return map.get(map.floorKey(value));
    }

    int getWeight(int key)
    {
        int lowKey = map.floorKey(key);
        Integer highKey = map.higherKey(key);
        if (highKey == null)
            highKey = maxValue;

        return highKey - lowKey;
    }

    int findIndex(E element)
    {
        for (Integer thisKey = 0; thisKey != null; thisKey = map.higherKey(thisKey))
            if (map.get(thisKey).equals(element))
                return thisKey;

        return -1;
    }

    int getRandomKey()
    {
        return rng.nextInt(maxValue);
    }

    E getRandom()
    {
        return this.get(getRandomKey());
    }

    E[] getRandom(E[] array)
    {
        try
        {
            WeightedList<E> destructibleList = (WeightedList<E>) this.clone();

            for (int i = 0; i < array.length; i++)
                array[i] = destructibleList.remove(destructibleList.getRandomKey());

            return array;
        }
        catch (CloneNotSupportedException ex)
        {
            //if this happens, we have bigger problems
            throw new RuntimeException(ex);
        }
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

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        WeightedList<E> newObject = (WeightedList<E>) super.clone();
        newObject.map = (TreeMap<Integer, E>) map.clone();
        return newObject;
    }
}
