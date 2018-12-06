package com.noprestige.kanaquiz.reference;

import android.content.Context;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.QuestionManagement;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import static com.noprestige.kanaquiz.questions.QuestionManagement.HIRAGANA;
import static com.noprestige.kanaquiz.questions.QuestionManagement.KATAKANA;
import static com.noprestige.kanaquiz.questions.QuestionManagement.VOCABULARY;

class ReferenceSubsectionPager extends FragmentPagerAdapter
{
    private int questionTypeRef;
    private List<Integer> tabList;
    Context context;

    ReferenceSubsectionPager(FragmentManager fm, Context context, int questionTypeRef)
    {
        super(fm);
        this.questionTypeRef = questionTypeRef;
        this.context = context;

        tabList = new ArrayList<>(3);

        if (questionTypeRef == R.string.vocabulary)
        {
            boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);
            for (int i = 1; i <= VOCABULARY.getCategoryCount(); i++)
                if (isFullReference || VOCABULARY.getPref(i))
                    tabList.add(i);
        }
        else if (OptionsControl.getBoolean(R.string.prefid_full_reference))
        {
            tabList.add(R.string.base_form_title);
            tabList.add(R.string.diacritics_title);
            tabList.add(R.string.digraphs_title);
        }
        else
        {
            QuestionManagement questions;

            if (questionTypeRef == R.string.hiragana)
                questions = HIRAGANA;
            else if (questionTypeRef == R.string.katakana)
                questions = KATAKANA;
            else
                throw new IllegalArgumentException("questionTypeRef '" + questionTypeRef + "' is invalid.");

            if (questions.anySelected())
                tabList.add(R.string.base_form_title);
            if (questions.diacriticsSelected())
                tabList.add(R.string.diacritics_title);
            if (questions.digraphsSelected())
                tabList.add(R.string.digraphs_title);
        }
    }

    @Override
    public Fragment getItem(int position)
    {
        return ReferenceSubsectionPage.newInstance(questionTypeRef, tabList.get(position));
    }

    @Override
    public int getCount()
    {
        return tabList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        if (questionTypeRef == R.string.vocabulary)
            return VOCABULARY.getSetTitle(tabList.get(position)).toString();
        else
            return context.getResources().getString(tabList.get(position));
    }
}
