package com.noprestige.kanaquiz;

import java.util.Random;
import java.util.TreeMap;

class WeightedList<E> extends TreeMap<Integer, E>
{
    private int maxValue = 0;

    private static Random rng = new Random();

    boolean add(int weight, E element)
    {
        this.put(maxValue, element);
        maxValue += weight;
        return true;
    }

    boolean add(double weight, E element)
    {
        return this.add((int) Math.ceil(weight), element);
    }

    E remove(int key)
    {
        key = this.floorKey(key);
        E returnValue = super.remove(key);
        if (returnValue == null)
            return null;
        else
        {
            int gap = this.higherKey(key) - key;

            for (Integer thisKey : this.keySet())
                if (thisKey >= key)
                {
                    E currentElement = super.get(thisKey);
                    super.remove(thisKey);
                    super.put(thisKey - gap, currentElement);
                }
            return returnValue;
        }
    }

    int count()
    {
        return super.size();
    }

    E get(int value)
    {
        if (value > maxValue || value < 0)
            throw new NullPointerException();
        else
            return this.get(this.floorKey(value));
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
        for (Integer oldKey : list.keySet())
            this.put(this.maxValue + oldKey, list.get(oldKey));
        this.maxValue += list.maxValue;

        return true;
    }
}
