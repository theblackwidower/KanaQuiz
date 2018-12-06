package com.noprestige.kanaquiz.reference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.noprestige.kanaquiz.R;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ReferencePage extends Fragment
{
    private static final String ARG_QUESTION_TYPE_REF = "questionTypeRef";
    private static final int MAX_TABS = 4;

    public static ReferencePage newInstance(int questionTypeRef)
    {
        ReferencePage screen = new ReferencePage();
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_TYPE_REF, questionTypeRef);
        screen.setArguments(args);
        return screen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int questionType = getArguments().getInt(ARG_QUESTION_TYPE_REF, 0);

        LinearLayout subScreen = (LinearLayout) inflater.inflate(R.layout.activity_tabbed_screen, container, false);
        subScreen.setPadding(0, 0, 0, 0);

        ViewPager viewPager = subScreen.findViewById(R.id.viewPager);

        if (questionType == R.string.hiragana)
            viewPager.setId(R.id.hiraganaReferenceViewPager);
        else if (questionType == R.string.katakana)
            viewPager.setId(R.id.katakanaReferenceViewPager);
        else if (questionType == R.string.kanji)
            viewPager.setId(R.id.kanjiReferenceViewPager);
        else if (questionType == R.string.vocabulary)
            viewPager.setId(R.id.vocabularyReferenceViewPager);

        //ref: https://stackoverflow.com/a/40829361/3582371
        ReferenceSubsectionPager pagerAdapter =
                new ReferenceSubsectionPager(getChildFragmentManager(), getContext(), questionType);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = subScreen.findViewById(R.id.tabLayout);
        //TODO: Make this check more dynamic, accounting for screen width and actual tab width
        if (pagerAdapter.getCount() > MAX_TABS)
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setPadding(getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin) / 4, 0,
                getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin) / 4, 0);
        tabLayout.setupWithViewPager(viewPager);

        return subScreen;
    }
}
