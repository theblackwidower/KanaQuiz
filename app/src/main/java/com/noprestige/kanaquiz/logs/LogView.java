package com.noprestige.kanaquiz.logs;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noprestige.kanaquiz.R;

public class LogView extends AppCompatActivity
{
    @SuppressLint("StaticFieldLeak")
    private class FetchLogs extends AsyncTask<Void, Void, DailyRecord[]>
    {
        LinearLayout layout;
        TextView lblLogMessage;

        @Override
        protected void onPreExecute()
        {
            layout = findViewById(R.id.log_view_layout);
            lblLogMessage = findViewById(R.id.lblLogMessage);
        }

        @Override
        protected DailyRecord[] doInBackground(Void... nothing)
        {
            return LogDatabase.DAO.getAllDailyRecords();
        }

        @Override
        protected void onPostExecute(DailyRecord[] records)
        {
            if (records.length > 0)
            {
                for (DailyRecord record : records)
                {
                    DailyLogItem output = new DailyLogItem(getBaseContext());
                    output.setFromRecord(record);
                    layout.addView(output);
                }
                layout.removeView(lblLogMessage);
            }
            else
            {
                lblLogMessage.setText(R.string.no_logs);
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
