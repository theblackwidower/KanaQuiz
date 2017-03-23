package com.noprestige.kanaquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class ReferenceScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_screen);

        ((LinearLayout)this.findViewById(R.id.activity_reference_screen)).addView(HiraganaQuestions.getReferenceTable(this));
        ((LinearLayout)this.findViewById(R.id.activity_reference_screen)).addView(KatakanaQuestions.getReferenceTable(this));
    }
}
