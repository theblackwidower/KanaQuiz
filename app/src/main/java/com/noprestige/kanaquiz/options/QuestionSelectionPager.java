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

package com.noprestige.kanaquiz.options;

import android.content.Context;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.questions.QuestionManagement;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

class QuestionSelectionPager extends FragmentStateAdapter
{
    private List<Integer> tabList;
    Context context;

    QuestionSelectionPager(FragmentActivity fa)
    {
        super(fa);

        tabList = new ArrayList<>(3);
        context = fa.getBaseContext();

        tabList.add(R.string.hiragana);
        tabList.add(R.string.katakana);
        if (QuestionManagement.getKanjiFileCount() > 0)
            tabList.add(R.string.kanji);
        tabList.add(R.string.vocabulary);
    }

    @Override
    public Fragment createFragment(int position)
    {
        return QuestionSelectionPage.newInstance(tabList.get(position));
    }

    @Override
    public long getItemId(int position)
    {
        return tabList.get(position);
    }

    @Override
    public int getItemCount()
    {
        return tabList.size();
    }

    public CharSequence getPageTitle(int position)
    {
        return context.getResources().getString(tabList.get(position));
    }
}
