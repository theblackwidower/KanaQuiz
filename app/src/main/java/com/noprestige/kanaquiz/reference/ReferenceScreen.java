/*
 *    Copyright 2021 T Duke Perry
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.noprestige.kanaquiz.reference;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.themes.ThemeManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class ReferenceScreen extends AppCompatActivity
{
    private static final int MAX_TABS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ThemeManager.setTheme(this);
        setContentView(R.layout.activity_tabbed_screen);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        ReferencePager pagerAdapter = new ReferencePager(this);
        viewPager.setAdapter(pagerAdapter);
        if (pagerAdapter.getItemCount() == 0)
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
            if (pagerAdapter.getItemCount() > MAX_TABS)
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> tab.setText(pagerAdapter.getPageTitle(position))).attach();
            viewPager.setUserInputEnabled(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ThemeManager.permissionRequestReturn(this, permissions, grantResults);
    }
}
