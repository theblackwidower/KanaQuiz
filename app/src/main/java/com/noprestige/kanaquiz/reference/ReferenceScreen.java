package com.noprestige.kanaquiz.reference;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.noprestige.kanaquiz.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class ReferenceScreen extends AppCompatActivity
{
    private static final int MAX_TABS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_screen);

        ViewPager viewPager = findViewById(R.id.viewPager);
        ReferencePager pagerAdapter = new ReferencePager(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        if (pagerAdapter.getCount() == 0)
        {
            TextView lblEmpty = new TextView(this);
            lblEmpty.setText(R.string.no_reference);
            lblEmpty.setTextSize(getResources().getDimensionPixelSize(R.dimen.embeddedAlertMessageSize));
            lblEmpty.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblEmpty.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            ViewGroup layout = (ViewGroup) viewPager.getParent();
            layout.removeAllViews();
            layout.addView(lblEmpty);
        }
        else
        {
            TabLayout tabLayout = findViewById(R.id.tabLayout);
            //TODO: Make this check more dynamic, accounting for screen width and actual tab width
            if (pagerAdapter.getCount() > MAX_TABS)
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
