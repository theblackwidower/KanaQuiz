package com.noprestige.kanaquiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ReferencePage extends Fragment
{
    private static final String ARG_PAGE_NUMBER = "position";

    public ReferencePage() {}

    public static ReferencePage newInstance(int id)
    {
        ReferencePage screen = new ReferencePage();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, id);
        screen.setArguments(args);
        return screen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout table = null;
        switch(getArguments().getInt(ARG_PAGE_NUMBER, -1))
        {
            case 0:
                if (HiraganaQuestions.anySelected())
                    table = HiraganaQuestions.getReferenceTable(container.getContext());
                else
                    table = KatakanaQuestions.getReferenceTable(container.getContext());
                break;
            case 1:
                table = KatakanaQuestions.getReferenceTable(container.getContext());
        }

        ScrollView scrollBox = new ScrollView(container.getContext());
        scrollBox.addView(table);

        return scrollBox;
    }
}
