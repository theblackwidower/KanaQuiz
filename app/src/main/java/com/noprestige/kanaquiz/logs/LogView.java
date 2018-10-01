package com.noprestige.kanaquiz.logs;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.noprestige.kanaquiz.AppTools;
import com.noprestige.kanaquiz.R;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class LogView extends AppCompatActivity
{
    static class FetchLogs extends AsyncTask<LogView, DailyLogItem, Integer>
    {
        static final LocalDate GRAPH_EPOCH = new LocalDate(2015, 1, 1);

        @SuppressLint("StaticFieldLeak")
        LinearLayout layout;
        @SuppressLint("StaticFieldLeak")
        TextView lblLogMessage;
        @SuppressLint("StaticFieldLeak")
        GraphView logGraph;
        LineGraphSeries<DataPoint> graphSeries;

        @Override
        protected void onPreExecute()
        {
            graphSeries = new LineGraphSeries<>();
        }

        @Override
        protected Integer doInBackground(LogView... activity)
        {
            layout = activity[0].findViewById(R.id.log_view_layout);
            lblLogMessage = activity[0].findViewById(R.id.lblLogMessage);
            logGraph = activity[0].findViewById(R.id.logGraph);

            DailyRecord[] records = LogDatabase.DAO.getAllDailyRecords();

            for (DailyRecord record : records)
            {
                DailyLogItem output = new DailyLogItem(activity[0].getBaseContext());
                output.setFromRecord(record);

                graphSeries.appendData(new DataPoint(Days.daysBetween(GRAPH_EPOCH, record.date).getDays(),
                        (record.correct_answers / record.total_answers) * 100f), true, 1000, true);

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
            logGraph.addSeries(graphSeries);
        }

        @Override
        protected void onCancelled()
        {
            layout = null;
            lblLogMessage = null;
            logGraph = null;
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
