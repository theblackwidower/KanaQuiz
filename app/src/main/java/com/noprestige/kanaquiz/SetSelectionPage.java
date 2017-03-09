package com.noprestige.kanaquiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SetSelectionPage extends Fragment
{
    private static final String ARG_PAGE_NUMBER = "position";

    public SetSelectionPage() {}

    public static SetSelectionPage newInstance(int id)
    {
        SetSelectionPage screen = new SetSelectionPage();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, id);
        screen.setArguments(args);
        return screen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup display = (ViewGroup) inflater.inflate(R.layout.fragment_set_selection_page, container, false);

        switch(getArguments().getInt(ARG_PAGE_NUMBER, -1))
        {
            case 0:
                display.addView(new SetSelectionItem(getContext(), R.string.set_1_title, R.string.set_1_hiragana, "hiragana_set_1"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_2_title, R.string.set_2_hiragana, "hiragana_set_2"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_3_title, R.string.set_3_hiragana, "hiragana_set_3"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_4_title, R.string.set_4_hiragana, "hiragana_set_4"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_5_title, R.string.set_5_hiragana, "hiragana_set_5"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_6_title, R.string.set_6_hiragana, "hiragana_set_6"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_7_title, R.string.set_7_hiragana, "hiragana_set_7"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_8_title, R.string.set_8_hiragana, "hiragana_set_8"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_9_title, R.string.set_9_hiragana, "hiragana_set_9"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_10_title, R.string.set_10_hiragana, "hiragana_set_10"));
                break;
            case 1:
                display.addView(new SetSelectionItem(getContext(), R.string.set_1_title, R.string.set_1_katakana, "katakana_set_1"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_2_title, R.string.set_2_katakana, "katakana_set_2"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_3_title, R.string.set_3_katakana, "katakana_set_3"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_4_title, R.string.set_4_katakana, "katakana_set_4"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_5_title, R.string.set_5_katakana, "katakana_set_5"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_6_title, R.string.set_6_katakana, "katakana_set_6"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_7_title, R.string.set_7_katakana, "katakana_set_7"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_8_title, R.string.set_8_katakana, "katakana_set_8"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_9_title, R.string.set_9_katakana, "katakana_set_9"));
                display.addView(new SetSelectionItem(getContext(), R.string.set_10_title, R.string.set_10_katakana, "katakana_set_10"));
        }
        return display;
    }
}
