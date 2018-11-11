package com.noprestige.kanaquiz.options;

import android.content.Context;

import com.noprestige.kanaquiz.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class QuestionSelectionPager extends FragmentPagerAdapter
{
    private Context context;

    QuestionSelectionPager(FragmentManager fm, Context context)
    {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        return QuestionSelectionPage.newInstance(position);
    }

    @Override
    public int getCount()
    {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return context.getResources().getString(R.string.hiragana);
            case 1:
                return context.getResources().getString(R.string.katakana);
            case 2:
                return context.getResources().getString(R.string.kanji);
            case 3:
                return context.getResources().getString(R.string.vocabulary);
            default:
                return "";
        }
    }
}
