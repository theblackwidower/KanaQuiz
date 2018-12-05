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
    private List<String> tabList;

    QuestionSelectionPager(FragmentManager fm, Context context)
    {
        super(fm);

        tabList = new ArrayList<>(3);

        tabList.add(context.getResources().getString(R.string.hiragana));
        tabList.add(context.getResources().getString(R.string.katakana));
        if (KANJI_TITLES.length > 0)
            tabList.add(context.getResources().getString(R.string.kanji));
        tabList.add(context.getResources().getString(R.string.vocabulary));
    }

    @Override
    public Fragment getItem(int position)
    {
        return QuestionSelectionPage.newInstance(tabList.get(position));
    }

    @Override
    public int getCount()
    {
        return tabList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabList.get(position);
    }
}
