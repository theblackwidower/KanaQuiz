package com.noprestige.kanaquiz.reference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.QuestionManagement;

import androidx.fragment.app.Fragment;

import static com.noprestige.kanaquiz.questions.QuestionManagement.HIRAGANA;
import static com.noprestige.kanaquiz.questions.QuestionManagement.KANJI;
import static com.noprestige.kanaquiz.questions.QuestionManagement.KATAKANA;
import static com.noprestige.kanaquiz.questions.QuestionManagement.VOCABULARY;

public class ReferenceSubsectionPage extends Fragment
{
    private static final String ARG_QUESTION_TYPE = "questionType";
    private static final String ARG_REF_CATEGORY = "refCategory";

    public static ReferenceSubsectionPage newInstance(String questionType, String refCategory)
    {
        ReferenceSubsectionPage screen = new ReferenceSubsectionPage();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_TYPE, questionType);
        args.putString(ARG_REF_CATEGORY, refCategory);
        screen.setArguments(args);
        return screen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        String questionType = getArguments().getString(ARG_QUESTION_TYPE, "");
        String refCategory = getArguments().getString(ARG_REF_CATEGORY, "");

        View scrollBox = inflater.inflate(R.layout.fragment_reference_subsection_empty, container, false);
        ViewGroup layout = scrollBox.findViewById(R.id.secReference);

        QuestionManagement questions;

        if (questionType.equals(getContext().getResources().getString(R.string.vocabulary)))
        {
            for (int i = 1; i <= VOCABULARY.getCategoryCount(); i++)
                if (refCategory.equals(VOCABULARY.getSetTitle(i).toString()))
                    layout.addView(VOCABULARY.getVocabReferenceTable(container.getContext(), i));
        }
        else if (questionType.equals(getContext().getResources().getString(R.string.kanji)))
            layout.addView(KANJI.getKanjiReferenceTable(container.getContext()));
        else
        {
            if (questionType.equals(getContext().getResources().getString(R.string.hiragana)))
                questions = HIRAGANA;
            else if (questionType.equals(getContext().getResources().getString(R.string.katakana)))
                questions = KATAKANA;
            else
                throw new IllegalArgumentException("questionType '" + questionType + "' is invalid.");

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
        }

        return scrollBox;
    }
}
