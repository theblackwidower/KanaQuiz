package com.noprestige.kanaquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import static android.graphics.Color.BLACK;

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

        TextView title_one = new TextView(getBaseContext());
        title_one.setTextColor(BLACK);
        title_one.setTextSize(24f);
        title_one.setText("Daily Records");
        layout.addView(title_one);

        for (LogDailyRecord record : records)
        {
            TextView output = new TextView(getBaseContext());
            output.setTextColor(BLACK);

            SimpleDateFormat Formatter = new SimpleDateFormat("yyyy-MM-dd");

            output.setText(Formatter.format(record.date) + ": " + Integer.toString(record.correct_answers) + "/" + Integer.toString(record.correct_answers + record.incorrect_answers));
            layout.addView(output);
        }

        TextView title_two = new TextView(getBaseContext());
        title_two.setTextColor(BLACK);
        title_two.setTextSize(24f);
        title_two.setText("Kana Records");
        layout.addView(title_two);

        LogKanaRecord[] kanaRecords = LogDatabase.DAO.getAllKanaRecords();

        for (LogKanaRecord record : kanaRecords)
        {
            TextView output = new TextView(getBaseContext());
            output.setTextColor(BLACK);

            output.setText(record.kana + ": " + Integer.toString(record.correct_answers) + "/" + Integer.toString(record.correct_answers + record.incorrect_answers));
            layout.addView(output);
        }

        TextView title_three = new TextView(getBaseContext());
        title_three.setTextColor(BLACK);
        title_three.setTextSize(24f);
        title_three.setText("Incorrect Answer Records");
        layout.addView(title_three);

        LogIncorrectAnswer[] answerRecords = LogDatabase.DAO.getAllAnswerRecords();

        for (LogIncorrectAnswer record : answerRecords)
        {
            TextView output = new TextView(getBaseContext());
            output.setTextColor(BLACK);

            output.setText(record.kana + " - " + record.incorrect_romanji + ": " + Integer.toString(record.occurrences));
            layout.addView(output);
        }

        setContentView(scrollbox);
    }
}
