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

package com.noprestige.kanaquiz.options;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.questions.QuestionManagement;

import java.util.Map;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class QuestionSelectionPage extends Fragment
{
    private static final String ARG_QUESTION_TYPE_REF = "questionTypeRef";
    private static final String ARG_ITEM_IDS = "prefIds";
    private static final String ARG_ITEM_STATES = "states";
    LinearLayout screen;

    private static final char STATUS_TRUE = 'T';
    private static final char STATUS_FALSE = 'F';
    private static final char STATUS_NEUTRAL = 'N';

    public static QuestionSelectionPage newInstance(int questionTypeRef)
    {
        QuestionSelectionPage screen = new QuestionSelectionPage();
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_TYPE_REF, questionTypeRef);
        screen.setArguments(args);
        return screen;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int questionTypeRef = getArguments().getInt(ARG_QUESTION_TYPE_REF, 0);

        if (questionTypeRef == R.string.hiragana)
            screen = QuestionManagement.getHiragana().getSelectionScreen(getContext());
        else if (questionTypeRef == R.string.katakana)
            screen = QuestionManagement.getKatakana().getSelectionScreen(getContext());
        else if (questionTypeRef == R.string.kanji)
        {
            screen = new LinearLayout(getContext());
            screen.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            screen.setOrientation(LinearLayout.VERTICAL);
            for (int i = 0; i < QuestionManagement.getKanjiFileCount(); i++)
            {
                screen.addView(buildHeader(getContext(), QuestionManagement.getKanjiTitle(i)));
                QuestionManagement.getKanji(i).populateSelectionScreen(screen);
            }
        }
        else if (questionTypeRef == R.string.vocabulary)
            screen = QuestionManagement.getVocabulary().getSelectionScreen(getContext());

        ScrollView scrollView = new ScrollView(getContext());
        scrollView.addView(screen);
        return scrollView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        //This block of code could also work in the onResume method
        char[] record = getArguments().getCharArray(ARG_ITEM_STATES);
        String[] prefIds = getArguments().getStringArray(ARG_ITEM_IDS);
        if (record != null)
        {
            Map<String, QuestionSelectionItem> itemList = new TreeMap<>();

            for (int i = 0; i < screen.getChildCount(); i++)
                if (screen.getChildAt(i) instanceof Checkable)
                {
                    QuestionSelectionItem item = (QuestionSelectionItem) screen.getChildAt(i);
                    itemList.put(item.getPrefId(), item);
                }
            for (int i = 0; i < record.length; i++)
                if (prefIds[i] != null)
                {
                    QuestionSelectionItem item = itemList.get(prefIds[i]);
                    if (item != null)
                        if (record[i] == STATUS_NEUTRAL)
                            item.nullify();
                        else
                            item.setChecked(record[i] == STATUS_TRUE);
                }
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        //This block of code could also work in the onPause and onStop methods
        int count = screen.getChildCount();
        char[] record = new char[count];
        String[] prefIds = new String[count];

        for (int i = 0; i < count; i++)
            if (screen.getChildAt(i) instanceof Checkable)
            {
                QuestionSelectionItem item = (QuestionSelectionItem) screen.getChildAt(i);
                prefIds[i] = item.getPrefId();
                if (item.isNeutral())
                    record[i] = STATUS_NEUTRAL;
                else if (item.isChecked())
                    record[i] = STATUS_TRUE;
                else
                    record[i] = STATUS_FALSE;
            }

        getArguments().putCharArray(ARG_ITEM_STATES, record);
        getArguments().putStringArray(ARG_ITEM_IDS, prefIds);
    }

    public static TextView buildHeader(Context context, String title)
    {
        TextView header = new TextView(context);
        header.setText(title);
        header.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setGravity(Gravity.CENTER);
        header.setTextSize(context.getResources().getDimension(R.dimen.headerTextSize));
        header.setPadding(0, context.getResources().getDimensionPixelSize(R.dimen.headerTopPaddingQuestionSelection), 0,
                context.getResources().getDimensionPixelSize(R.dimen.headerBottomPadding));
        header.setTypeface(header.getTypeface(), Typeface.BOLD);
        header.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        header.setAllCaps(true);
        return header;
    }
}
