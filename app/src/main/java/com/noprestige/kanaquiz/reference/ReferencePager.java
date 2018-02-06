package com.noprestige.kanaquiz.reference;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.noprestige.kanaquiz.Hiragana;
import com.noprestige.kanaquiz.Katakana;
import com.noprestige.kanaquiz.OptionsControl;
import com.noprestige.kanaquiz.R;

import java.util.ArrayList;

class ReferencePager extends FragmentPagerAdapter
{
    private ArrayList<String> tabList;

    ReferencePager(FragmentManager fm, Context context)
    {
        super(fm);

        tabList = new ArrayList<>(2);

        if (OptionsControl.getBoolean(R.string.prefid_full_reference))
        {
            tabList.add(context.getResources().getString(R.string.hiragana));
            tabList.add(context.getResources().getString(R.string.katakana));
        }
        else
        {
            if (Hiragana.QUESTIONS.anySelected())
                tabList.add(context.getResources().getString(R.string.hiragana));
            if (Katakana.QUESTIONS.anySelected())
                tabList.add(context.getResources().getString(R.string.katakana));
        }
    }

    @Override
    public Fragment getItem(int position)
    {
        return ReferencePage.newInstance(tabList.get(position));
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
