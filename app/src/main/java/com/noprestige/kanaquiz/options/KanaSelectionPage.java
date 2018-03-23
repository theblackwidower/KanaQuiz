package com.noprestige.kanaquiz.options;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.noprestige.kanaquiz.questions.Hiragana;
import com.noprestige.kanaquiz.questions.Katakana;

public class KanaSelectionPage extends Fragment
{
    private static final String ARG_PAGE_NUMBER = "position";

    public static KanaSelectionPage newInstance(int id)
    {
        KanaSelectionPage screen = new KanaSelectionPage();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, id);
        screen.setArguments(args);
        return screen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //TODO: Fix reloading issue.
        //      All KanaSelectionItems end up deselected when the Fragment is reloaded,
        //      either through screen rotation, or if there is a third tab.
        LinearLayout screen;
        switch (getArguments().getInt(ARG_PAGE_NUMBER, -1))
        {
            case 0:
                screen = Hiragana.QUESTIONS.getSelectionScreen(getContext());
                break;
            case 1:
                screen = Katakana.QUESTIONS.getSelectionScreen(getContext());
                break;
            default:
                screen = null;
        }
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.addView(screen);
        return scrollView;
    }
}
