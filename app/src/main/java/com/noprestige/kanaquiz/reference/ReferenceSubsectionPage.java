package com.noprestige.kanaquiz.reference;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.Hiragana;
import com.noprestige.kanaquiz.questions.Katakana;
import com.noprestige.kanaquiz.questions.QuestionManagement;

public class ReferenceSubsectionPage extends Fragment
{
    private static final String ARG_KANA_TYPE = "kanaType";
    private static final String ARG_REF_CATEGORY = "refCategory";

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
        String kanaType = getArguments().getString(ARG_KANA_TYPE, "");
        String refCategory = getArguments().getString(ARG_REF_CATEGORY, "");

        View scrollBox = inflater.inflate(R.layout.fragment_reference_subsection_empty, container, false);
        ViewGroup layout = scrollBox.findViewById(R.id.secReference);

        QuestionManagement questions;

        if (kanaType.equals(getContext().getResources().getString(R.string.hiragana)))
            questions = Hiragana.QUESTIONS;
        else if (kanaType.equals(getContext().getResources().getString(R.string.katakana)))
            questions = Katakana.QUESTIONS;
        else
            throw new IllegalArgumentException();

        if (refCategory.equals(getContext().getResources().getString(R.string.base_form_title)))
            layout.addView(questions.getMainReferenceTable(container.getContext()));
        else if (refCategory.equals(getContext().getResources().getString(R.string.diacritics_title)))
            layout.addView(questions.getDiacriticReferenceTable(container.getContext()));
        else if (refCategory.equals(getContext().getResources().getString(R.string.digraphs_title)))
        {
            layout.addView(questions.getMainDigraphsReferenceTable(container.getContext()));
            if (OptionsControl.getBoolean(R.string.prefid_full_reference) || questions.diacriticDigraphsSelected())
            {
                layout.addView(ReferenceCell.buildHeader(getContext(), R.string.diacritics_title));
                layout.addView(questions.getDiacriticDigraphsReferenceTable(container.getContext()));
            }
        }

        return scrollBox;
    }
}
