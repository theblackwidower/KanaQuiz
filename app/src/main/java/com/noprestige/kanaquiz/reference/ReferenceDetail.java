/*
 *    Copyright 2022 T Duke Perry
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
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.questions.Question;

import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.DialogFragment;

import static android.os.Build.VERSION.SDK_INT;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class ReferenceDetail extends DialogFragment
{
    private static final String ARG_SUBJECT = "subject";
    private static final String ARG_LABELS = "labels";
    private static final String ARG_DETAILS = "details";
    private static final String ARG_HEADER = "header";

    public static ReferenceDetail newInstance(Question question)
    {
        Bundle args = new Bundle();
        ReferenceDetail dialog = new ReferenceDetail();
        args.putString(ARG_SUBJECT, question.getQuestionText());
        args.putString(ARG_HEADER, question.getReferenceHeader());

        Map<String, String> details = question.getReferenceDetails();
        args.putStringArray(ARG_LABELS, details.keySet().toArray(new String[0]));
        args.putStringArray(ARG_DETAILS, details.values().toArray(new String[0]));

        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View content = requireActivity().getLayoutInflater().inflate(R.layout.reference_detail_dialog, null);
        TextView lblSubject = content.findViewById(R.id.lblSubject);

        TextView lblHeader = content.findViewById(R.id.lblHeader);
        lblHeader.setPaintFlags(lblHeader.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        lblHeader.setText(getArguments().getString(ARG_HEADER));

        String subject = getArguments().getString(ARG_SUBJECT);

        lblSubject.setText(subject);

        if (subject.length() > 1)
            ((LinearLayout) content.findViewById(R.id.layDetail)).setOrientation(LinearLayout.VERTICAL);

        if (SDK_INT < Build.VERSION_CODES.O)
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(lblSubject, 12, 128, 2, COMPLEX_UNIT_SP);

        TableLayout detailTable = content.findViewById(R.id.tblReferenceDetail);

        String[] labels = getArguments().getStringArray(ARG_LABELS);
        String[] details = getArguments().getStringArray(ARG_DETAILS);

        int length = Math.min(labels.length, details.length);
        for (int i = 0; i < length; i++)
            detailTable.addView(makeDetailRow(labels[i], details[i]));

        builder.setView(content);
        return builder.create();
    }

    private TableRow makeDetailRow(String label, String text)
    {
        TableRow row = (TableRow) requireActivity().getLayoutInflater().inflate(R.layout.reference_detail_row, null);
        ((TextView) (row.findViewById(R.id.lblItemLabel))).setText(label);
        ((TextView) (row.findViewById(R.id.lblItemLabel))).append(":");
        ((TextView) (row.findViewById(R.id.lblItemText))).setText(text);
        return row;
    }
}
