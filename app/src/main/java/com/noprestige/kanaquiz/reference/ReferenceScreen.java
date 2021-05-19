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

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
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

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ThemeManager.setTheme(this);
        setContentView(R.layout.activity_tabbed_screen);

        viewPager = findViewById(R.id.viewPager);
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

    private Rect touchArea;

    private float startX;
    private float lastX;
    private boolean isFirstItem;
    private boolean isLastItem;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (touchArea == null)
            {
                touchArea = new Rect();
                ViewPager2 nestedViewPager = getNestedPager(viewPager.getCurrentItem());
                nestedViewPager.getGlobalVisibleRect(touchArea);
            }

            // checks if the motion started in the right location
            if (touchArea.contains(Math.round(event.getX()), Math.round(event.getY())))
            {
                ViewPager2 nestedViewPager = getNestedPager(viewPager.getCurrentItem());

                int currentItem = nestedViewPager.getCurrentItem();
                isFirstItem = currentItem == 0;
                isLastItem = currentItem == (nestedViewPager.getAdapter().getItemCount() - 1);

                if (isFirstItem || isLastItem)
                {
                    startX = event.getX();
                    lastX = startX;

                    viewPager.beginFakeDrag();
                }
            }
        }
        else if (isFirstItem || isLastItem)
            if ((event.getAction() == MotionEvent.ACTION_UP) || (event.getAction() == MotionEvent.ACTION_CANCEL))
            {
                viewPager.endFakeDrag();

                startX = 0;
                lastX = 0;
                isFirstItem = false;
                isLastItem = false;
            }
            else
            {
                float thisX = event.getX();
                float deltaX = 0;

                // if both statements execute the net result is:
                //    deltaX = thisX - lastX;
                if (((isFirstItem) && (lastX > startX)) || ((isLastItem) && (lastX < startX)))
                    deltaX += startX - lastX;
                if (((isFirstItem) && (thisX > startX)) || ((isLastItem) && (thisX < startX)))
                    deltaX += thisX - startX;

                if (deltaX != 0)
                    viewPager.fakeDragBy(deltaX);

                lastX = thisX;
            }

        return super.dispatchTouchEvent(event);
    }

    ViewPager2 getNestedPager(int position)
    {
        long nestedType = viewPager.getAdapter().getItemId(position);

        if (nestedType == R.string.hiragana)
            return findViewById(R.id.hiraganaReferenceViewPager);
        else if (nestedType == R.string.katakana)
            return findViewById(R.id.katakanaReferenceViewPager);
        else if (nestedType == R.string.kanji)
            return findViewById(R.id.kanjiReferenceViewPager);
        else if (nestedType == R.string.vocabulary)
            return findViewById(R.id.vocabularyReferenceViewPager);

        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ThemeManager.permissionRequestReturn(this, permissions, grantResults);
    }
}
