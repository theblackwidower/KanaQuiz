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

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class QuestionSelectionDetail extends DialogFragment
{
    private static final String ARG_PREFID = "prefId";
    private static final String ARG_QUESTIONS = "questions";

    private CheckBox[] checkBoxes;

    private QuestionSelectionItem parent;

    public static QuestionSelectionDetail newInstance(String prefId, String[] questions)
    {
        Bundle args = new Bundle();
        QuestionSelectionDetail dialog = new QuestionSelectionDetail();
        args.putString(ARG_PREFID, prefId);
        args.putStringArray(ARG_QUESTIONS, questions);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout content = new LinearLayout(getContext());
        content.setOrientation(LinearLayout.VERTICAL);
        content.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        String prefIdStart = getArguments().getString(ARG_PREFID);
        String[] questions = getArguments().getStringArray(ARG_QUESTIONS);
        checkBoxes = new CheckBox[questions.length];
        for (int i = 0; i < questions.length; i++)
        {
            String prefId = prefIdStart + "_" + questions[i];
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(questions[i]);
            checkBox.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            checkBox.setChecked(OptionsControl.exists(prefIdStart) ? OptionsControl.getBoolean(prefIdStart) :
                    OptionsControl.getBoolean(prefId));
            checkBox.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> OptionsControl.setBoolean(prefId, isChecked));
            checkBoxes[i] = checkBox;
            content.addView(checkBox);
        }

        builder.setView(content);
        return builder.create();
    }

    public void recordParentCheckbox(QuestionSelectionItem checkbox)
    {
        parent = checkbox;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog)
    {
        super.onDismiss(dialog);
        Boolean overallPref = null;

        for (CheckBox box : checkBoxes)
            if (overallPref == null)
                overallPref = box.isChecked();
            else if (!overallPref.equals(box.isChecked()))
            {
                overallPref = null;
                break;
            }
        if (overallPref == null)
            OptionsControl.delete(parent.getPrefId());
        else
            parent.setChecked(overallPref);
    }
}
