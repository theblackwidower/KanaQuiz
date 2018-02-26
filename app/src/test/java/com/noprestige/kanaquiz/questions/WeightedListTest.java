package com.noprestige.kanaquiz.questions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeightedListTest
{
    @Test
    public void rangeTest() throws Exception
    {
        WeightedList<String> listOne = new WeightedList<>();

        int[] weights = new int[]{3, 8, 27, 6};
        String[] strings = new String[]{"Bleh", "Foo", "Snide", "Mesh"};
        final int SAMPLE_COUNT = 4;
        for (int i = 0; i < SAMPLE_COUNT; i++)
            listOne.add(weights[i], strings[i]);

        int i = 0;
        int sum = 0;

        for (int j = 0; j < SAMPLE_COUNT; j++)
        {
            sum += weights[j];
            for (int start = i; i < weights[j] + start; i++)
                assertEquals(listOne.get(i), strings[j]);
        }

        assertEquals(listOne.maxValue(), sum);
    }

    @Test
    public void mergeTest() throws Exception
    {
        WeightedList<String> listOne = new WeightedList<>();

        int[] weightsOne = new int[]{3, 8, 27, 6};
        String[] stringsOne = new String[]{"Bleh", "Foo", "Snide", "Mesh"};
        final int SAMPLE_COUNT_ONE = 4;

        for (int i = 0; i < SAMPLE_COUNT_ONE; i++)
            listOne.add(weightsOne[i], stringsOne[i]);

        WeightedList<String> listTwo = new WeightedList<>();

        int[] weightsTwo = new int[]{8, 2, 9, 21};
        String[] stringsTwo = new String[]{"Gree", "Fnex", "Moose", "Twa"};
        final int SAMPLE_COUNT_TWO = 4;

        for (int i = 0; i < SAMPLE_COUNT_TWO; i++)
            listTwo.add(weightsTwo[i], stringsTwo[i]);

        WeightedList<String> listMerged = new WeightedList<>();

        listMerged.merge(listOne);
        listMerged.merge(listTwo);

        int i = 0;
        int sum = 0;

        for (int j = 0; j < SAMPLE_COUNT_ONE; j++)
        {
            sum += weightsOne[j];
            for (int start = i; i < weightsOne[j] + start; i++)
                assertEquals(listMerged.get(i), stringsOne[j]);
        }
        for (int j = 0; j < SAMPLE_COUNT_TWO; j++)
        {
            sum += weightsTwo[j];
            for (int start = i; i < weightsTwo[j] + start; i++)
                assertEquals(listMerged.get(i), stringsTwo[j]);
        }

        assertEquals(listMerged.maxValue(), sum);
    }
}