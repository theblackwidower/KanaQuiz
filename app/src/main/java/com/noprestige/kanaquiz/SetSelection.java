package com.noprestige.kanaquiz;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SetSelection extends AppCompatActivity {

    SharedPreferences sharedPref;
    ViewGroup layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_selection);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        layout = (ViewGroup) findViewById(R.id.activity_set_selection);

        createItem(R.string.set_1_title, R.string.set_1_contents, R.id.chkSet1Selection, 1);
        createItem(R.string.set_2_title, R.string.set_2_contents, R.id.chkSet2Selection, 2);
        createItem(R.string.set_3_title, R.string.set_3_contents, R.id.chkSet3Selection, 3);
        createItem(R.string.set_4_title, R.string.set_4_contents, R.id.chkSet4Selection, 4);
        createItem(R.string.set_5_title, R.string.set_5_contents, R.id.chkSet5Selection, 5);
        createItem(R.string.set_6_title, R.string.set_6_contents, R.id.chkSet6Selection, 6);
        createItem(R.string.set_7_title, R.string.set_7_contents, R.id.chkSet7Selection, 7);
        createItem(R.string.set_8_title, R.string.set_8_contents, R.id.chkSet8Selection, 8);
        createItem(R.string.set_9_title, R.string.set_9_contents, R.id.chkSet9Selection, 9);
    }

    private void createItem(int title, int contents, int boxId, int idNumber)
    {
        LinearLayout container = new LinearLayout(this);
        LinearLayout textContainer = new LinearLayout(this);
        TextView lblTitle = new TextView(this);
        TextView lblContents = new TextView(this);
        CheckBox chkCheckBox = new CheckBox(this);

        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        textContainer.setOrientation(LinearLayout.VERTICAL);
        textContainer.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        lblTitle.setText(title);
        lblTitle.setTypeface(null, Typeface.BOLD);
        lblTitle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        lblContents.setText(contents);
        lblContents.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        chkCheckBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        chkCheckBox.setChecked(sharedPref.getBoolean("kana_set_" + idNumber, false));
        chkCheckBox.setId(boxId);
        chkCheckBox.setGravity(Gravity.CENTER_VERTICAL);

        textContainer.addView(lblTitle);
        textContainer.addView(lblContents);

        container.addView(textContainer);
        container.addView(chkCheckBox);
        container.setBackgroundResource(R.drawable.border);

        layout.addView(container);
    }

    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("kana_set_1", ((CheckBox) findViewById(R.id.chkSet1Selection)).isChecked());
        editor.putBoolean("kana_set_2", ((CheckBox) findViewById(R.id.chkSet2Selection)).isChecked());
        editor.putBoolean("kana_set_3", ((CheckBox) findViewById(R.id.chkSet3Selection)).isChecked());
        editor.putBoolean("kana_set_4", ((CheckBox) findViewById(R.id.chkSet4Selection)).isChecked());
        editor.putBoolean("kana_set_5", ((CheckBox) findViewById(R.id.chkSet5Selection)).isChecked());
        editor.putBoolean("kana_set_6", ((CheckBox) findViewById(R.id.chkSet6Selection)).isChecked());
        editor.putBoolean("kana_set_7", ((CheckBox) findViewById(R.id.chkSet7Selection)).isChecked());
        editor.putBoolean("kana_set_8", ((CheckBox) findViewById(R.id.chkSet8Selection)).isChecked());
        editor.putBoolean("kana_set_9", ((CheckBox) findViewById(R.id.chkSet9Selection)).isChecked());
        editor.apply();
        finish();
    }
}