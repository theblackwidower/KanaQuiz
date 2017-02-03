package com.noprestige.kanaquiz;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SetSelectionPager extends FragmentPagerAdapter
{
    Context context;

    public SetSelectionPager(FragmentManager fm, Context context)
    {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        return SetSelectionPage.newInstance(position);
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position)
        {
            case 0:
                return context.getResources().getString(R.string.hiragana);
            case 1:
                return context.getResources().getString(R.string.katakana);
            default:
                return "";
        }
    }
}
