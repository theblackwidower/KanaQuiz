package com.noprestige.kanaquiz.reference;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.QuestionManagement;

import java.util.ArrayList;
import java.util.List;

import static com.noprestige.kanaquiz.questions.QuestionManagement.HIRAGANA;
import static com.noprestige.kanaquiz.questions.QuestionManagement.KATAKANA;

class ReferenceSubsectionPager extends FragmentPagerAdapter
{
    private String kanaType;
    private List<String> tabList;

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
        else
        {
            QuestionManagement questions;

            if (kanaType.equals(context.getResources().getString(R.string.hiragana)))
                questions = HIRAGANA;
            else if (kanaType.equals(context.getResources().getString(R.string.katakana)))
                questions = KATAKANA;
            else
                throw new IllegalArgumentException("kanaType '" + kanaType + "' is invalid.");

            if (questions.anySelected())
                tabList.add(context.getResources().getString(R.string.base_form_title));
            if (questions.diacriticsSelected())
                tabList.add(context.getResources().getString(R.string.diacritics_title));
            if (questions.digraphsSelected())
                tabList.add(context.getResources().getString(R.string.digraphs_title));
        }
    }

    @Override
    public Fragment getItem(int position)
    {
        return ReferenceSubsectionPage.newInstance(kanaType, tabList.get(position));
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
