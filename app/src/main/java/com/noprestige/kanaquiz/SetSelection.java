package com.noprestige.kanaquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;


public class SetSelection extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_selection);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_set_selection);

        layout.addView(new SetSelectionItem(this, R.string.set_1_title, R.string.set_1_hiragana, "hiragana_set_1"));
        layout.addView(new SetSelectionItem(this, R.string.set_2_title, R.string.set_2_hiragana, "hiragana_set_2"));
        layout.addView(new SetSelectionItem(this, R.string.set_3_title, R.string.set_3_hiragana, "hiragana_set_3"));
        layout.addView(new SetSelectionItem(this, R.string.set_4_title, R.string.set_4_hiragana, "hiragana_set_4"));
        layout.addView(new SetSelectionItem(this, R.string.set_5_title, R.string.set_5_hiragana, "hiragana_set_5"));
        layout.addView(new SetSelectionItem(this, R.string.set_6_title, R.string.set_6_hiragana, "hiragana_set_6"));
        layout.addView(new SetSelectionItem(this, R.string.set_7_title, R.string.set_7_hiragana, "hiragana_set_7"));
        layout.addView(new SetSelectionItem(this, R.string.set_8_title, R.string.set_8_hiragana, "hiragana_set_8"));
        layout.addView(new SetSelectionItem(this, R.string.set_9_title, R.string.set_9_hiragana, "hiragana_set_9"));
    }
}