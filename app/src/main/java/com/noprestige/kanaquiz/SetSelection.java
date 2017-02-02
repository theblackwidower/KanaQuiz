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

        createItem(R.string.set_1_title, R.string.set_1_hiragana, R.id.chkHiraSet1Selection, "hiragana_set_1");
        createItem(R.string.set_2_title, R.string.set_2_hiragana, R.id.chkHiraSet2Selection, "hiragana_set_2");
        createItem(R.string.set_3_title, R.string.set_3_hiragana, R.id.chkHiraSet3Selection, "hiragana_set_3");
        createItem(R.string.set_4_title, R.string.set_4_hiragana, R.id.chkHiraSet4Selection, "hiragana_set_4");
        createItem(R.string.set_5_title, R.string.set_5_hiragana, R.id.chkHiraSet5Selection, "hiragana_set_5");
        createItem(R.string.set_6_title, R.string.set_6_hiragana, R.id.chkHiraSet6Selection, "hiragana_set_6");
        createItem(R.string.set_7_title, R.string.set_7_hiragana, R.id.chkHiraSet7Selection, "hiragana_set_7");
        createItem(R.string.set_8_title, R.string.set_8_hiragana, R.id.chkHiraSet8Selection, "hiragana_set_8");
        createItem(R.string.set_9_title, R.string.set_9_hiragana, R.id.chkHiraSet9Selection, "hiragana_set_9");
    }

    private void createItem(int title, int contents, int boxId, String prefId)
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
        chkCheckBox.setChecked(sharedPref.getBoolean(prefId, false));
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
        editor.putBoolean("hiragana_set_1", ((CheckBox) findViewById(R.id.chkHiraSet1Selection)).isChecked());
        editor.putBoolean("hiragana_set_2", ((CheckBox) findViewById(R.id.chkHiraSet2Selection)).isChecked());
        editor.putBoolean("hiragana_set_3", ((CheckBox) findViewById(R.id.chkHiraSet3Selection)).isChecked());
        editor.putBoolean("hiragana_set_4", ((CheckBox) findViewById(R.id.chkHiraSet4Selection)).isChecked());
        editor.putBoolean("hiragana_set_5", ((CheckBox) findViewById(R.id.chkHiraSet5Selection)).isChecked());
        editor.putBoolean("hiragana_set_6", ((CheckBox) findViewById(R.id.chkHiraSet6Selection)).isChecked());
        editor.putBoolean("hiragana_set_7", ((CheckBox) findViewById(R.id.chkHiraSet7Selection)).isChecked());
        editor.putBoolean("hiragana_set_8", ((CheckBox) findViewById(R.id.chkHiraSet8Selection)).isChecked());
        editor.putBoolean("hiragana_set_9", ((CheckBox) findViewById(R.id.chkHiraSet9Selection)).isChecked());
        editor.apply();
        finish();
    }
}