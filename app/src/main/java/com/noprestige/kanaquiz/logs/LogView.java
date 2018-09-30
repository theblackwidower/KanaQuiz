package com.noprestige.kanaquiz.logs;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noprestige.kanaquiz.AppTools;
import com.noprestige.kanaquiz.R;

public class LogView extends AppCompatActivity
{
    static class FetchLogs extends AsyncTask<LogView, DailyLogItem, Integer>
    {
        @SuppressLint("StaticFieldLeak")
        LinearLayout layout;
        @SuppressLint("StaticFieldLeak")
        TextView lblLogMessage;

        @Override
        protected Integer doInBackground(LogView... activity)
        {
            layout = activity[0].findViewById(R.id.log_view_layout);
            lblLogMessage = activity[0].findViewById(R.id.lblLogMessage);

            DailyRecord[] records = LogDatabase.DAO.getAllDailyRecords();

            for (DailyRecord record : records)
            {
                DailyLogItem output = new DailyLogItem(activity[0].getBaseContext());
                output.setFromRecord(record);
                if (isCancelled())
                    return null;
                else
                    publishProgress(output);
            }
            return records.length;
        }

        @Override
        protected void onProgressUpdate(DailyLogItem... item)
        {
            layout.addView(item[0], layout.getChildCount() - 1);
        }

        @Override
        protected void onCancelled()
        {
            layout = null;
            lblLogMessage = null;
        }

        @Override
        protected void onPostExecute(Integer count)
        {
            if (count > 0)
                layout.removeView(lblLogMessage);
            else
                lblLogMessage.setText(R.string.no_logs);
        }
    }

    FetchLogs fetchThread;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);

        AppTools.initializeManagers(getApplicationContext());

        fetchThread = new FetchLogs();
        fetchThread.execute(this);
    }

    @Override
    protected void onDestroy()
    {
        if (fetchThread != null)
            fetchThread.cancel(true);
        super.onDestroy();
    }
}
