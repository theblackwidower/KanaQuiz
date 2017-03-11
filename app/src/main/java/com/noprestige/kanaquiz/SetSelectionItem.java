package com.noprestige.kanaquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class SetSelectionItem extends LinearLayout
{
    private SharedPreferences sharedPrefs;

    public SetSelectionItem(Context context, int title, int contents, final String prefId)
    {
        super(context);

        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        LinearLayout textContainer = new LinearLayout(context);
        LinearLayout mainContainer = new LinearLayout(context);
        ImageView imgBorder = new ImageView(context);
        TextView lblTitle = new TextView(context);
        TextView lblContents = new TextView(context);
        final CheckBox chkCheckBox = new CheckBox(context);

        textContainer.setOrientation(LinearLayout.VERTICAL);
        textContainer.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        mainContainer.setOrientation(LinearLayout.HORIZONTAL);
        mainContainer.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        imgBorder.setImageResource(R.drawable.border);

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

        this.setOnClickListener(
            new OnClickListener()
            {
                public void onClick(View view)
                {
                    chkCheckBox.toggle();
                }
            }
        );

        textContainer.addView(lblTitle);
        textContainer.addView(lblContents);

        mainContainer.addView(textContainer);
        mainContainer.addView(chkCheckBox);

        this.addView(mainContainer);
        this.addView(imgBorder);
    }
}
