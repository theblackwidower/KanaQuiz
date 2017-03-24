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
        boolean isHiragana = HiraganaQuestions.anySelected();
        boolean isKatakana = KatakanaQuestions.anySelected();
        if (!(isHiragana || isKatakana))
            return 0;
        else if (isHiragana ^ isKatakana)
            return 1;
        else
            return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                if (!HiraganaQuestions.anySelected())
                    return context.getResources().getString(R.string.katakana);
                else
                    return context.getResources().getString(R.string.hiragana);
            case 1:
                return context.getResources().getString(R.string.katakana);
            default:
                return "";
        }
    }
}
