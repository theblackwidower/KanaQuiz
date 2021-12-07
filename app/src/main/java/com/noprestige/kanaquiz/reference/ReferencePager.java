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

package com.noprestige.kanaquiz.reference;

import android.content.Context;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.QuestionManagement;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

class ReferencePager extends FragmentStateAdapter
{
    private List<Integer> tabList;
    private Context context;

    ReferencePager(FragmentActivity fa)
    {
        super(fa);

        tabList = new ArrayList<>(3);
        context = fa.getBaseContext();

        if (OptionsControl.getBoolean(R.string.prefid_full_reference))
        {
            tabList.add(R.string.hiragana);
            tabList.add(R.string.katakana);
            if (QuestionManagement.getKanjiFileCount() > 0)
                tabList.add(R.string.kanji);
            if (QuestionManagement.getVocabulary().getCategoryCount() > 0)
                tabList.add(R.string.vocabulary);
        }
        else
        {
            if (QuestionManagement.getHiragana().anySelected())
                tabList.add(R.string.hiragana);
            if (QuestionManagement.getKatakana().anySelected())
                tabList.add(R.string.katakana);
            for (int i = 0; i < QuestionManagement.getKanjiFileCount(); i++)
                if (QuestionManagement.getKanji(i).anySelected())
                {
                    tabList.add(R.string.kanji);
                    break;
                }
            if (QuestionManagement.getVocabulary().anySelected())
                tabList.add(R.string.vocabulary);
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        return ReferencePage.newInstance(tabList.get(position));
    }

    @Override
    public long getItemId(int position)
    {
        return tabList.get(position);
    }

    //ref: https://stackoverflow.com/a/57691487/3582371
    @Override
    public boolean containsItem(long itemId)
    {
        return tabList.contains((int) itemId);
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
