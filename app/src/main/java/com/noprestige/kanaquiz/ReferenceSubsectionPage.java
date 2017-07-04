package com.noprestige.kanaquiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;

public class ReferenceSubsectionPage extends Fragment
{
    private static final String ARG_PAGE_NUMBER = "position";
    private static final String ARG_KANA_TYPE = "kanaType";

    public ReferenceSubsectionPage() {}

    public static ReferenceSubsectionPage newInstance(int id, String kanaType)
    {
        ReferenceSubsectionPage screen = new ReferenceSubsectionPage();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, id);
        args.putString(ARG_KANA_TYPE, kanaType);
        screen.setArguments(args);
        return screen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        TableLayout table = null;
        String kanaType = getArguments().getString(ARG_KANA_TYPE, "");
        switch (getArguments().getInt(ARG_PAGE_NUMBER, -1))
        {
            case 0:
                if (kanaType.equals(getContext().getResources().getString(R.string.hiragana)))
                    table = Hiragana.QUESTIONS.getMainReferenceTable(container.getContext());
                else if (kanaType.equals(getContext().getResources().getString(R.string.katakana)))
                    table = Katakana.QUESTIONS.getMainReferenceTable(container.getContext());
                break;
            case 1:
                if (kanaType.equals(getContext().getResources().getString(R.string.hiragana)))
                {
                    if (Hiragana.QUESTIONS.diacriticsSelected())
                        table = Hiragana.QUESTIONS.getDiacriticReferenceTable(container.getContext());
                    else
                        table = Hiragana.QUESTIONS.getDigraphsReferenceTable(container.getContext());
                }
                else if (kanaType.equals(getContext().getResources().getString(R.string.katakana)))
                {
                    if (Katakana.QUESTIONS.diacriticsSelected())
                        table = Katakana.QUESTIONS.getDiacriticReferenceTable(container.getContext());
                    else
                        table = Katakana.QUESTIONS.getDigraphsReferenceTable(container.getContext());
                }
                break;
            case 2:
                if (kanaType.equals(getContext().getResources().getString(R.string.hiragana)))
                    table = Hiragana.QUESTIONS.getDigraphsReferenceTable(container.getContext());
                else if (kanaType.equals(getContext().getResources().getString(R.string.katakana)))
                    table = Katakana.QUESTIONS.getDigraphsReferenceTable(container.getContext());
                break;
        }

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.addView(table);

        ScrollView scrollBox = new ScrollView(container.getContext());
        scrollBox.addView(layout);

        return scrollBox;
    }
}
