package com.noprestige.kanaquiz;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

class ReferenceSubsectionPager extends FragmentPagerAdapter
{
    private String kanaType;
    private ArrayList<String> tabList;

    ReferenceSubsectionPager(FragmentManager fm, Context context, String kanaType)
    {
        super(fm);
        this.kanaType = kanaType;

        tabList = new ArrayList<>(3);

        if (OptionsControl.getBoolean(R.string.prefid_full_reference))
        {
            tabList.add(context.getResources().getString(R.string.base_form_title));
            tabList.add(context.getResources().getString(R.string.diacritics_title));
            tabList.add(context.getResources().getString(R.string.digraphs_title));
        }
        else if (kanaType.equals(context.getResources().getString(R.string.hiragana)))
        {
            if (Hiragana.QUESTIONS.anySelected())
                tabList.add(context.getResources().getString(R.string.base_form_title));
            if (Hiragana.QUESTIONS.diacriticsSelected())
                tabList.add(context.getResources().getString(R.string.diacritics_title));
            if (Hiragana.QUESTIONS.digraphsSelected())
                tabList.add(context.getResources().getString(R.string.digraphs_title));
        }
        else if (kanaType.equals(context.getResources().getString(R.string.katakana)))
        {
            if (Katakana.QUESTIONS.anySelected())
                tabList.add(context.getResources().getString(R.string.base_form_title));
            if (Katakana.QUESTIONS.diacriticsSelected())
                tabList.add(context.getResources().getString(R.string.diacritics_title));
            if (Katakana.QUESTIONS.digraphsSelected())
                tabList.add(context.getResources().getString(R.string.digraphs_title));
        }
    }

    @Override
    public Fragment getItem(int position)
    {
        return ReferenceSubsectionPage.newInstance(position, kanaType);
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
