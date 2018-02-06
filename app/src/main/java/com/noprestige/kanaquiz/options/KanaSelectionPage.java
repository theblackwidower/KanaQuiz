package com.noprestige.kanaquiz.options;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noprestige.kanaquiz.questions.Hiragana;
import com.noprestige.kanaquiz.questions.Katakana;

public class KanaSelectionPage extends Fragment
{
    private static final String ARG_PAGE_NUMBER = "position";

    public KanaSelectionPage() {}

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
        switch (getArguments().getInt(ARG_PAGE_NUMBER, -1))
        {
            case 0:
                return Hiragana.QUESTIONS.getSelectionScreen(getContext());
            case 1:
                return Katakana.QUESTIONS.getSelectionScreen(getContext());
            default:
                return null;
        }
    }
}
