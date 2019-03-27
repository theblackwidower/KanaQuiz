/*
 *    Copyright 2018 T Duke Perry
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

package com.noprestige.kanaquiz.options;

import android.content.Context;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.questions.QuestionManagement;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class QuestionSelectionPager extends FragmentPagerAdapter
{
    private List<Integer> tabList;
    Context context;

    QuestionSelectionPager(FragmentManager fm, Context context)
    {
        super(fm);

        tabList = new ArrayList<>(3);
        this.context = context;

        tabList.add(R.string.hiragana);
        tabList.add(R.string.katakana);
        if (QuestionManagement.getKanjiFileCount() > 0)
            tabList.add(R.string.kanji);
        tabList.add(R.string.vocabulary);
    }

    @Override
    public Fragment getItem(int position)
    {
        return QuestionSelectionPage.newInstance(tabList.get(position));
    }

    @Override
    public long getItemId(int position)
    {
        return tabList.get(position);
    }

    @Override
    public int getCount()
    {
        return tabList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return context.getResources().getString(tabList.get(position));
    }
}
