package com.noprestige.kanaquiz;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReferenceScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_screen);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerReference);
        viewPager.setAdapter(new ReferencePager(getSupportFragmentManager(), this));
        ((TabLayout) findViewById(R.id.tabLayoutReference)).setupWithViewPager(viewPager);
    }
}
