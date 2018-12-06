package com.noprestige.kanaquiz.options;

import android.content.Context;

import com.noprestige.kanaquiz.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import static com.noprestige.kanaquiz.questions.QuestionManagement.KANJI_TITLES;

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
        if (KANJI_TITLES.length > 0)
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
