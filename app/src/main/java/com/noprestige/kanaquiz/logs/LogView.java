package com.noprestige.kanaquiz.logs;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.noprestige.kanaquiz.AppTools;
import com.noprestige.kanaquiz.R;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import androidx.appcompat.app.AppCompatActivity;

public class LogView extends AppCompatActivity
{
    static class FetchLogs extends AsyncTask<LogView, DailyLogItem, Integer>
    {
        LocalDate startDate;

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

            startDate = records[0].date;

            for (DailyRecord record : records)
            {
                DailyLogItem output = new DailyLogItem(activity[0].getBaseContext());
                output.setFromRecord(record);

                graphSeries.appendData(new DataPoint(Days.daysBetween(startDate, record.date).getDays(),
                        (record.correctAnswers / record.totalAnswers) * 100f), true, 1000, true);

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
            logGraph.getViewport().setMaxX(Days.daysBetween(startDate, item[0].getDate()).getDays());
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

        GraphView logGraph = findViewById(R.id.logGraph);

        logGraph.getViewport().setYAxisBoundsManual(true);
        logGraph.getViewport().setMinY(0);
        logGraph.getViewport().setMaxY(100);
        logGraph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        logGraph.getViewport().setScalable(true); // X-axis zooming and scrolling
        logGraph.getViewport().setXAxisBoundsManual(true);
        logGraph.getViewport().setMinX(0);
    }

    @Override
    protected void onDestroy()
    {
        if (fetchThread != null)
            fetchThread.cancel(true);
        super.onDestroy();
    }
}
