package com.noprestige.kanaquiz.options;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.noprestige.kanaquiz.R;

public class KanaSelection extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_screen);

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new KanaSelectionPager(getSupportFragmentManager(), this));
        ((TabLayout) findViewById(R.id.tabLayout)).setupWithViewPager(viewPager);
    }
}
