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
