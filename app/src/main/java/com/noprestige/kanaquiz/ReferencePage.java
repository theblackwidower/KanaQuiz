package com.noprestige.kanaquiz;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ReferencePage extends Fragment
{
    private static final String ARG_PAGE_NUMBER = "position";

    public ReferencePage() {}

    public static ReferencePage newInstance(int id)
    {
        ReferencePage screen = new ReferencePage();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, id);
        screen.setArguments(args);
        return screen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        String kanaType;
        switch (getArguments().getInt(ARG_PAGE_NUMBER, -1))
        {
            case 0:
                if (Hiragana.QUESTIONS.anySelected())
                    kanaType = getResources().getString(R.string.hiragana);
                else
                    kanaType = getResources().getString(R.string.katakana);
                break;
            case 1:
                kanaType = getResources().getString(R.string.katakana);
                break;
            default:
                kanaType = "";
        }

        LinearLayout subScreen = new LinearLayout(getContext());
        subScreen.setOrientation(LinearLayout.VERTICAL);
        subScreen.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TabLayout tabLayout = new TabLayout(getContext());
        tabLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ViewPager viewPager = new ViewPager(getContext());
        viewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (kanaType.equals(getResources().getString(R.string.hiragana)))
            viewPager.setId(R.id.hiraganaReferenceViewPager);
        else if (kanaType.equals(getResources().getString(R.string.katakana)))
            viewPager.setId(R.id.katakanaReferenceViewPager);

        subScreen.addView(tabLayout);
        subScreen.addView(viewPager);

        ReferenceSubsectionPager adapter = new ReferenceSubsectionPager(getActivity().getSupportFragmentManager(), getContext(), kanaType);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return subScreen;
    }
}
