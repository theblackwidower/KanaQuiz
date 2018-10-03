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
    private static final String ARG_KANA_TYPE = "kanaType";

    public static ReferencePage newInstance(String kanaType)
    {
        ReferencePage screen = new ReferencePage();
        Bundle args = new Bundle();
        args.putString(ARG_KANA_TYPE, kanaType);
        screen.setArguments(args);
        return screen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        String kanaType = getArguments().getString(ARG_KANA_TYPE, "");

        LinearLayout subScreen = (LinearLayout) inflater.inflate(R.layout.activity_tabbed_screen, container, false);
        subScreen.setPadding(0, 0, 0, 0);

        ViewPager viewPager = subScreen.findViewById(R.id.viewPager);

        if (kanaType.equals(getResources().getString(R.string.hiragana)))
            viewPager.setId(R.id.hiraganaReferenceViewPager);
        else if (kanaType.equals(getResources().getString(R.string.katakana)))
            viewPager.setId(R.id.katakanaReferenceViewPager);

        viewPager.setAdapter(
                new ReferenceSubsectionPager(getActivity().getSupportFragmentManager(), getContext(), kanaType));
        TabLayout tabLayout = subScreen.findViewById(R.id.tabLayout);
        tabLayout.setPadding(getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin) / 4, 0,
                getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin) / 4, 0);
        tabLayout.setupWithViewPager(viewPager);

        return subScreen;
    }
}
