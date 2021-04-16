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
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class QuestionSelectionDetail extends DialogFragment
{
    private static final String ARG_PREFID = "prefId";
    private static final String ARG_QUESTIONS = "questions";

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
        for (String question : questions)
        {
            String prefId = prefIdStart + "_" + question;
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(question);
            checkBox.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            checkBox.setChecked(OptionsControl.getBoolean(prefId));
            checkBox.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> OptionsControl.setBoolean(prefId, isChecked));
        }

        builder.setView(content);
        return builder.create();
    }

}
