package com.noprestige.kanaquiz.logs;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.noprestige.kanaquiz.R;

public class LogView extends AppCompatActivity
{
    @SuppressLint("StaticFieldLeak")
    private class FetchLogs extends AsyncTask<Void, Void, DailyRecord[]>
    {
        LinearLayout layout;

        @Override
        protected void onPreExecute()
        {
            layout = findViewById(R.id.log_view_layout);
        }

        @Override
        protected DailyRecord[] doInBackground(Void... nothing)
        {
            return LogDatabase.DAO.getAllDailyRecords();
        }

        @Override
        protected void onPostExecute(DailyRecord[] records)
        {
            for (DailyRecord record : records)
            {
                DailyLogItem output = new DailyLogItem(getBaseContext());
                output.setFromRecord(record);
                layout.addView(output);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);

        new FetchLogs().execute();
    }
}
