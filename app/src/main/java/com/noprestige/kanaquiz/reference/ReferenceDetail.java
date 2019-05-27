/*
 *    Copyright 2019 T Duke Perry
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

package com.noprestige.kanaquiz.reference;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.questions.Question;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ReferenceDetail extends DialogFragment
{
    private static final String ARG_SUBJECT = "subject";

    public static ReferenceDetail newInstance(Question question)
    {
        Bundle args = new Bundle();
        ReferenceDetail dialog = new ReferenceDetail();
        args.putString(ARG_SUBJECT, question.getQuestionText());
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View content = requireActivity().getLayoutInflater().inflate(R.layout.reference_detail_dialog, null);
        ((TextView) (content.findViewById(R.id.lblSubject))).setText(getArguments().getString(ARG_SUBJECT));
        builder.setView(content);
        return builder.create();
    }
}
