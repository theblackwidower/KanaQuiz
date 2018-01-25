package com.noprestige.kanaquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class LogView extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);

        LogDailyRecord[] records = LogDatabase.DAO.getAllDailyRecords();

        ScrollView scrollbox = new ScrollView(getBaseContext());
        LinearLayout layout = new LinearLayout(getBaseContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        setContentView(scrollbox);
        scrollbox.addView(layout);

        for (LogDailyRecord record : records)
        {
            TextView output = new TextView(getBaseContext());
            output.setText(record.date.toString() + ": " + Integer.toString(record.correct_answers) + "/" + Integer.toString(record.correct_answers + record.incorrect_answers));
            layout.addView(output);
        }

        setContentView(scrollbox);
    }
}
