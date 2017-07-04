package com.noprestige.kanaquiz;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class ReferenceSubsectionPager extends FragmentPagerAdapter
{
    private Context context;
    private String kanaType;

    ReferenceSubsectionPager(FragmentManager fm, Context context, String kanaType)
    {
        super(fm);
        this.kanaType = kanaType;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        return ReferenceSubsectionPage.newInstance(position, kanaType);
    }

    @Override
    public int getCount()
    {
        int count = 0;
        if (kanaType.equals(context.getResources().getString(R.string.hiragana)))
        {
            if (Hiragana.QUESTIONS.anySelected())
                count++;
            if (Hiragana.QUESTIONS.diacriticsSelected())
                count++;
            if (Hiragana.QUESTIONS.digraphsSelected())
                count++;
        }
        else if (kanaType.equals(context.getResources().getString(R.string.katakana)))
        {
            if (Katakana.QUESTIONS.anySelected())
                count++;
            if (Katakana.QUESTIONS.diacriticsSelected())
                count++;
            if (Katakana.QUESTIONS.digraphsSelected())
                count++;
        }
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return context.getResources().getString(R.string.base_form_title);
            case 1:
                if ((kanaType.equals(context.getResources().getString(R.string.hiragana)) && Hiragana.QUESTIONS.diacriticsSelected()) ||
                        (kanaType.equals(context.getResources().getString(R.string.katakana)) && Katakana.QUESTIONS.diacriticsSelected()))
                    return context.getResources().getString(R.string.diacritics_title);
                // else continue on
            case 2:
                return context.getResources().getString(R.string.digraphs_title);
            default:
                return "";
        }
    }
}
