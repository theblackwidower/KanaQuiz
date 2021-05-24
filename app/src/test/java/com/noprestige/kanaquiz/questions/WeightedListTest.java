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

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class WeightedListTest
{
    private static class SampleData
    {
        public int[] weights;
        public String[] strings;
    }

    private WeightedList<String> demoList(SampleData data)
    {
        WeightedList<String> list = new WeightedList<>();
        int sampleCount = data.weights.length;
        assertThat(data.strings.length, is(sampleCount));

        for (int i = 0; i < sampleCount; i++)
            list.add(data.weights[i], data.strings[i]);

        return list;
    }

    private int testList(int i, SampleData data, WeightedList<String> list)
    {
        int sampleCount = data.weights.length;
        assertThat(data.strings.length, is(sampleCount));

        for (int j = 0; j < sampleCount; j++)
            for (int start = i; i < (data.weights[j] + start); i++)
            {
                assertThat(list.getWeight(i), is(data.weights[j]));
                assertThat(list.get(i), is(data.strings[j]));
            }

        return i;
    }

    private void testListEnd(int i, WeightedList<String> list)
    {
        assertThat(list.maxValue(), is(i));

        try
        {
            list.get(i);
            fail("Failed to throw error when over limit.");
        }
        catch (IndexOutOfBoundsException ignored) {}
    }

    private void removeFromSampleData(int index, WeightedList<String> list, SampleData data)
    {
        int sum = data.weights[index] / 2;
        for (int i = 0; i < index; i++)
            sum += data.weights[i];

        assertThat(list.remove(sum), is(data.strings[index]));
        for (int i = index; i < (data.weights.length - 1); i++)
        {
            data.weights[i] = data.weights[i + 1];
            data.strings[i] = data.strings[i + 1];
        }
        data.weights[data.weights.length - 1] = 0;
        data.strings[data.strings.length - 1] = null;
    }

    private void adjustSampleData(int index, int adjustment, WeightedList<String> list, SampleData data)
    {
        int sum = data.weights[index] / 2;
        for (int i = 0; i < index; i++)
            sum += data.weights[i];

        // confirm zero-out catches work
        assertThat(list.adjustWeight(sum, -data.weights[index] - 1), is(false));
        assertThat(list.adjustWeight(sum, -data.weights[index]), is(false));

        data.weights[index] += adjustment;

        assertThat(list.adjustWeight(sum, adjustment), is(true));
    }

    @Test
    public void rangeTest()
    {
        SampleData data = new SampleData();
        data.weights = new int[]{3, 8, 27, 6};
        data.strings = new String[]{"Bleh", "Foo", "Snide", "Mesh"};
        WeightedList<String> list = demoList(data);

        int i = 0;
        i = testList(i, data, list);
        testListEnd(i, list);
    }

    @Test
    public void mergeTest()
    {
        SampleData dataOne = new SampleData();
        dataOne.weights = new int[]{3, 8, 27, 6};
        dataOne.strings = new String[]{"Bleh", "Foo", "Snide", "Mesh"};
        WeightedList<String> listOne = demoList(dataOne);

        SampleData dataTwo = new SampleData();
        dataTwo.weights = new int[]{8, 2, 9, 21};
        dataTwo.strings = new String[]{"Gree", "Fnex", "Moose", "Twa"};
        WeightedList<String> listTwo = demoList(dataTwo);

        WeightedList<String> listMerged = new WeightedList<>();

        listMerged.merge(listOne);
        listMerged.merge(listTwo);

        int i = 0;
        i = testList(i, dataOne, listMerged);
        i = testList(i, dataTwo, listMerged);
        testListEnd(i, listMerged);
    }

    @Test
    public void removeTest()
    {
        SampleData data = new SampleData();
        data.weights = new int[]{3, 8, 27, 6, 8, 2, 9, 21};
        data.strings = new String[]{"Bleh", "Foo", "Snide", "Mesh", "Gree", "Fnex", "Moose", "Twa"};
        WeightedList<String> list = demoList(data);

        removeFromSampleData(1, list, data);
        data.weights = Arrays.copyOf(data.weights, data.weights.length - 1);
        data.strings = Arrays.copyOf(data.strings, data.strings.length - 1);

        int j = 0;
        j = testList(j, data, list);
        testListEnd(j, list);

        removeFromSampleData(3, list, data);
        data.weights = Arrays.copyOf(data.weights, data.weights.length - 1);
        data.strings = Arrays.copyOf(data.strings, data.strings.length - 1);

        j = 0;
        j = testList(j, data, list);
        testListEnd(j, list);

        removeFromSampleData(5, list, data);
        data.weights = Arrays.copyOf(data.weights, data.weights.length - 1);
        data.strings = Arrays.copyOf(data.strings, data.strings.length - 1);

        j = 0;
        j = testList(j, data, list);
        testListEnd(j, list);
    }

    @Test
    public void adjustWeightTest()
    {
        SampleData data = new SampleData();
        data.weights = new int[]{3, 8, 27, 6, 8, 2, 9, 21};
        data.strings = new String[]{"Bleh", "Foo", "Snide", "Mesh", "Gree", "Fnex", "Moose", "Twa"};
        WeightedList<String> list = demoList(data);

        adjustSampleData(2, -8, list, data);

        int i = 0;
        i = testList(i, data, list);
        testListEnd(i, list);

        adjustSampleData(4, 7, list, data);

        i = 0;
        i = testList(i, data, list);
        testListEnd(i, list);

        adjustSampleData(0, -1, list, data);

        i = 0;
        i = testList(i, data, list);
        testListEnd(i, list);

        adjustSampleData(7, 4, list, data);

        i = 0;
        i = testList(i, data, list);
        testListEnd(i, list);
    }

    @Test
    public void findIndexTest()
    {
        SampleData data = new SampleData();
        data.weights = new int[]{3, 8, 27, 6, 8, 2, 9, 21};
        data.strings = new String[]{"Bleh", "Foo", "Snide", "Mesh", "Gree", "Fnex", "Moose", "Twa"};
        WeightedList<String> list = demoList(data);

        int sum = 0;
        for (int i = 0; i < data.strings.length; i++)
        {
            assertThat(list.findIndex(data.strings[i]), is(sum));
            sum += data.weights[i];
        }
    }
}