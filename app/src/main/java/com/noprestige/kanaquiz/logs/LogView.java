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
    private class FetchLogs extends AsyncTask<Void, Void, DailyLogItem[]>
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
        protected DailyLogItem[] doInBackground(Void... nothing)
        {
            DailyRecord[] records = LogDatabase.DAO.getAllDailyRecords();

            DailyLogItem[] returnValues = new DailyLogItem[records.length];
            for (int i = 0; i < records.length; i++)
            {
                DailyLogItem output = new DailyLogItem(getBaseContext());
                output.setFromRecord(records[i]);
                returnValues[i] = output;
            }
            return returnValues;
        }

        @Override
        protected void onPostExecute(DailyLogItem[] items)
        {
            if (items.length > 0)
            {
                for (DailyLogItem item : items)
                    layout.addView(item);
                layout.removeView(lblLogMessage);
            }
            else
                lblLogMessage.setText(R.string.no_logs);
        }
    }

    FetchLogs fetchThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);

        fetchThread = new FetchLogs();
        fetchThread.execute();
    }

    @Override
    protected void onDestroy()
    {
        if (fetchThread != null)
            fetchThread.cancel(true);
        super.onDestroy();
    }
}
