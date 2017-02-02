package com.noprestige.kanaquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

class SetSelectionItem extends LinearLayout
{
    private SharedPreferences sharedPrefs;

    public SetSelectionItem(Context context, int title, int contents, final String prefId)
    {
        super(context);

        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        this.setBackgroundResource(R.drawable.border);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        LinearLayout textContainer = new LinearLayout(context);
        TextView lblTitle = new TextView(context);
        TextView lblContents = new TextView(context);
        CheckBox chkCheckBox = new CheckBox(context);

        textContainer.setOrientation(LinearLayout.VERTICAL);
        textContainer.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        lblTitle.setText(title);
        lblTitle.setTypeface(null, Typeface.BOLD);
        lblTitle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        lblContents.setText(contents);
        lblContents.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        chkCheckBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        chkCheckBox.setChecked(sharedPrefs.getBoolean(prefId, false));
        chkCheckBox.setGravity(Gravity.CENTER_VERTICAL);
        chkCheckBox.setOnCheckedChangeListener(
            new android.widget.CompoundButton.OnCheckedChangeListener()
            {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(prefId, isChecked);
                    editor.apply();
                }
            }
        );

        textContainer.addView(lblTitle);
        textContainer.addView(lblContents);

        this.addView(textContainer);
        this.addView(chkCheckBox);
    }
}
