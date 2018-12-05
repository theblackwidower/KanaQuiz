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

import androidx.fragment.app.Fragment;

import static com.noprestige.kanaquiz.questions.QuestionManagement.HIRAGANA;
import static com.noprestige.kanaquiz.questions.QuestionManagement.KANJI_FILES;
import static com.noprestige.kanaquiz.questions.QuestionManagement.KANJI_TITLES;
import static com.noprestige.kanaquiz.questions.QuestionManagement.KATAKANA;
import static com.noprestige.kanaquiz.questions.QuestionManagement.VOCABULARY;

public class QuestionSelectionPage extends Fragment
{
    private static final String ARG_PAGE_NUMBER = "position";
    private static final String ARG_ITEM_STATES = "states";
    LinearLayout screen;

    public static QuestionSelectionPage newInstance(int id)
    {
        QuestionSelectionPage screen = new QuestionSelectionPage();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, id);
        screen.setArguments(args);
        return screen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        switch (getArguments().getInt(ARG_PAGE_NUMBER, -1))
        {
            case 0:
                screen = HIRAGANA.getSelectionScreen(getContext());
                break;
            case 1:
                screen = KATAKANA.getSelectionScreen(getContext());
                break;
            case 2:
                screen = new LinearLayout(getContext());
                screen.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                screen.setOrientation(LinearLayout.VERTICAL);
                for (int i = 0; i < KANJI_TITLES.length; i++)
                {
                    screen.addView(buildHeader(getContext(), KANJI_TITLES[i]));
                    KANJI_FILES[i].populateSelectionScreen(screen);
                }
                break;
            case 3:
                screen = VOCABULARY.getSelectionScreen(getContext());
                break;
            default:
                screen = null;
        }
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.addView(screen);
        return scrollView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        //This block of code could also work in the onResume method
        boolean[] record = getArguments().getBooleanArray(ARG_ITEM_STATES);
        if (record != null)
        {
            int count = Math.min(screen.getChildCount(), record.length);

            for (int i = 0; i < count; i++)
                if (screen.getChildAt(i) instanceof Checkable)
                    ((Checkable) screen.getChildAt(i)).setChecked(record[i]);
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        //This block of code could also work in the onPause and onStop methods
        int count = screen.getChildCount();
        boolean[] record = new boolean[count];

        for (int i = 0; i < count; i++)
            if (screen.getChildAt(i) instanceof Checkable)
                record[i] = ((Checkable) screen.getChildAt(i)).isChecked();

        getArguments().putBooleanArray(ARG_ITEM_STATES, record);
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
