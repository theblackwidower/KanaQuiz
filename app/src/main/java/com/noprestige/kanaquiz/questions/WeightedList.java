/*
 *    Copyright 2021 T Duke Perry
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.noprestige.kanaquiz.questions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import androidx.annotation.NonNull;

class WeightedList<E> implements Cloneable
{
    private TreeMap<Integer, E> map = new TreeMap<>();
    private int maxValue;

    private static final Random rng = new Random();

    boolean add(int weight, E element)
    {
        map.put(maxValue, element);
        maxValue += weight;
        return true;
    }

    boolean add(double weight, E element)
    {
        return add((int) Math.ceil(weight), element);
    }

    E remove(int key)
    {
        if ((key >= maxValue) || (key < 0))
            throw new IndexOutOfBoundsException(
                    "(" + key + ") is an invalid key. The key must be between 0 and " + maxValue + ".");
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
        if ((key >= maxValue) || (key < 0))
            throw new IndexOutOfBoundsException(
                    "(" + key + ") is an invalid key. The key must be between 0 and " + maxValue + ".");
        else
        {
            if ((getWeight(key) + adjustment) <= 0)
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

    public int count()
    {
        return map.size();
    }

    E get(int value)
    {
        if ((value >= maxValue) || (value < 0))
            throw new IndexOutOfBoundsException(
                    "(" + value + ") is an invalid key. The key must be between 0 and " + maxValue + ".");
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
        return get(getRandomKey());
    }

    E[] getRandom(E[] array)
    {
        try
        {
            WeightedList<E> destructibleList = clone();

            for (int i = 0; i < array.length; i++)
                array[i] = destructibleList.remove(destructibleList.getRandomKey());

            return array;
        }
        catch (CloneNotSupportedException ex)
        {
            //if this happens, we have bigger problems
            //noinspection ProhibitedExceptionThrown
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
            map.put(maxValue + oldKey, list.map.get(oldKey));
        maxValue += list.maxValue;

        return true;
    }

    Collection<E> values()
    {
        return map.values();
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public WeightedList<E> clone() throws CloneNotSupportedException
    {
        WeightedList<E> newObject = (WeightedList<E>) super.clone();
        newObject.map = (TreeMap<Integer, E>) map.clone();
        return newObject;
    }
}
