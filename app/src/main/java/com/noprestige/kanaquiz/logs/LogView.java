package com.noprestige.kanaquiz.logs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.noprestige.kanaquiz.R;

public class LogView extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);

        DailyRecord[] records = LogDatabase.DAO.getAllDailyRecords();

        LinearLayout layout = findViewById(R.id.log_view_layout);

        for (DailyRecord record : records)
        {
            DailyLogItem output = new DailyLogItem(getBaseContext());
            output.setFromRecord(record);
            layout.addView(output);
        }
    }
}
