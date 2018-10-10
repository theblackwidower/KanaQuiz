package com.noprestige.kanaquiz.reference;

import android.content.Context;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import static com.noprestige.kanaquiz.questions.QuestionManagement.HIRAGANA;
import static com.noprestige.kanaquiz.questions.QuestionManagement.KATAKANA;

class ReferencePager extends FragmentPagerAdapter
{
    private List<String> tabList;

    ReferencePager(FragmentManager fm, Context context)
    {
        super(fm);

        tabList = new ArrayList<>(3);

        if (OptionsControl.getBoolean(R.string.prefid_full_reference))
        {
            tabList.add(context.getResources().getString(R.string.hiragana));
            tabList.add(context.getResources().getString(R.string.katakana));
//            tabList.add(context.getResources().getString(R.string.vocabulary));
        }
        else
        {
            if (HIRAGANA.anySelected())
                tabList.add(context.getResources().getString(R.string.hiragana));
            if (KATAKANA.anySelected())
                tabList.add(context.getResources().getString(R.string.katakana));
            //TODO: fix issue with tab transition
//            if (VOCABULARY.anySelected())
//                tabList.add(context.getResources().getString(R.string.vocabulary));
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
