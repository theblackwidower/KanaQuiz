package com.noprestige.kanaquiz.questions;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WeightedListTest
{
    private WeightedList<String> demoList(int[] weights, String[] strings)
    {
        WeightedList<String> list = new WeightedList<>();
        final int SAMPLE_COUNT = weights.length;
        assertEquals(strings.length, SAMPLE_COUNT);

        for (int i = 0; i < SAMPLE_COUNT; i++)
            list.add(weights[i], strings[i]);

        return list;
    }

    private int testList(int i, int[] weights, String[] strings, WeightedList<String> list)
    {
        final int SAMPLE_COUNT = weights.length;
        assertEquals(strings.length, SAMPLE_COUNT);

        for (int j = 0; j < SAMPLE_COUNT; j++)
            for (int start = i; i < weights[j] + start; i++)
                assertEquals(list.get(i), strings[j]);

        return i;
    }

    private void testListEnd(int i, WeightedList<String> list)
    {
        assertEquals(list.maxValue(), i);

        try
        {
            list.get(i);
            assertTrue(false);
        }
        catch (IndexOutOfBoundsException e)
        {
        }
    }

    private void removeFromSampleData(int index, WeightedList<String> list, int[] weights, String[] strings)
    {
        int sum = weights[index] / 2;
        for (int i = 0; i < index; i++)
            sum += weights[i];

        assertEquals(list.remove(sum), strings[index]);
        for (int i = index; i < weights.length - 1; i++)
        {
            weights[i] = weights[i + 1];
            strings[i] = strings[i + 1];
        }
        weights[weights.length - 1] = 0;
        strings[strings.length - 1] = null;
    }

    @Test
    public void rangeTest() throws Exception
    {
        int[] weights = new int[]{3, 8, 27, 6};
        String[] strings = new String[]{"Bleh", "Foo", "Snide", "Mesh"};
        WeightedList<String> list = demoList(weights, strings);

        int i = 0;
        i = testList(i, weights, strings, list);
        testListEnd(i, list);
    }

    @Test
    public void mergeTest() throws Exception
    {
        int[] weightsOne = new int[]{3, 8, 27, 6};
        String[] stringsOne = new String[]{"Bleh", "Foo", "Snide", "Mesh"};
        WeightedList<String> listOne = demoList(weightsOne, stringsOne);

        int[] weightsTwo = new int[]{8, 2, 9, 21};
        String[] stringsTwo = new String[]{"Gree", "Fnex", "Moose", "Twa"};
        WeightedList<String> listTwo = demoList(weightsTwo, stringsTwo);

        WeightedList<String> listMerged = new WeightedList<>();

        listMerged.merge(listOne);
        listMerged.merge(listTwo);

        int i = 0;
        i = testList(i, weightsOne, stringsOne, listMerged);
        i = testList(i, weightsTwo, stringsTwo, listMerged);
        testListEnd(i, listMerged);
    }

    @Test
    public void removeTest() throws Exception
    {
        int[] weights = new int[]{3, 8, 27, 6, 8, 2, 9, 21};
        String[] strings = new String[]{"Bleh", "Foo", "Snide", "Mesh", "Gree", "Fnex", "Moose", "Twa"};
        WeightedList<String> list = demoList(weights, strings);

        removeFromSampleData(1, list, weights, strings);
        weights = Arrays.copyOf(weights, weights.length - 1);
        strings = Arrays.copyOf(strings, strings.length - 1);

        int j = 0;
        j = testList(j, weights, strings, list);
        testListEnd(j, list);

        removeFromSampleData(3, list, weights, strings);
        weights = Arrays.copyOf(weights, weights.length - 1);
        strings = Arrays.copyOf(strings, strings.length - 1);

        j = 0;
        j = testList(j, weights, strings, list);
        testListEnd(j, list);

        removeFromSampleData(5, list, weights, strings);
        weights = Arrays.copyOf(weights, weights.length - 1);
        strings = Arrays.copyOf(strings, strings.length - 1);

        j = 0;
        j = testList(j, weights, strings, list);
        testListEnd(j, list);
    }
}