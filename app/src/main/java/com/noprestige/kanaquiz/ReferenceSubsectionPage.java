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
    private static final String ARG_KANA_TYPE = "kanaType";
    private static final String ARG_REF_CATEGORY = "refCategory";

    public ReferenceSubsectionPage() {}

    public static ReferenceSubsectionPage newInstance(String kanaType, String refCategory)
    {
        ReferenceSubsectionPage screen = new ReferenceSubsectionPage();
        Bundle args = new Bundle();
        args.putString(ARG_KANA_TYPE, kanaType);
        args.putString(ARG_REF_CATEGORY, refCategory);
        screen.setArguments(args);
        return screen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        TableLayout table = null;
        String kanaType = getArguments().getString(ARG_KANA_TYPE, "");
        String refCategory = getArguments().getString(ARG_REF_CATEGORY, "");

        if (kanaType.equals(getContext().getResources().getString(R.string.hiragana)))
        {
            if (refCategory.equals(getContext().getResources().getString(R.string.base_form_title)))
                table = Hiragana.QUESTIONS.getMainReferenceTable(container.getContext());
            else if (refCategory.equals(getContext().getResources().getString(R.string.diacritics_title)))
                table = Hiragana.QUESTIONS.getDiacriticReferenceTable(container.getContext());
            else if (refCategory.equals(getContext().getResources().getString(R.string.digraphs_title)))
                table = Hiragana.QUESTIONS.getDigraphsReferenceTable(container.getContext());
        }
        else if (kanaType.equals(getContext().getResources().getString(R.string.katakana)))
        {
            if (refCategory.equals(getContext().getResources().getString(R.string.base_form_title)))
                table = Katakana.QUESTIONS.getMainReferenceTable(container.getContext());
            else if (refCategory.equals(getContext().getResources().getString(R.string.diacritics_title)))
                table = Katakana.QUESTIONS.getDiacriticReferenceTable(container.getContext());
            else if (refCategory.equals(getContext().getResources().getString(R.string.digraphs_title)))
                table = Katakana.QUESTIONS.getDigraphsReferenceTable(container.getContext());
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
