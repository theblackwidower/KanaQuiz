package com.noprestige.kanaquiz;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class ReferencePager extends FragmentPagerAdapter
{
    private Context context;

    ReferencePager(FragmentManager fm, Context context)
    {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        return ReferencePage.newInstance(position);
    }

    @Override
    public int getCount()
    {
        boolean isHiragana = Hiragana.QUESTIONS.anySelected();
        boolean isKatakana = Katakana.QUESTIONS.anySelected();
        if (isHiragana && isKatakana)
            return 2;
        else if (isHiragana ^ isKatakana)
            return 1;
        else
            return 0;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                if (Hiragana.QUESTIONS.anySelected())
                    return context.getResources().getString(R.string.hiragana);
                // else continue on
            case 1:
                return context.getResources().getString(R.string.katakana);
            default:
                return "";
        }
    }
}
