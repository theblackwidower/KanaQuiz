package com.noprestige.kanaquiz.logs;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.noprestige.kanaquiz.R;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class LogDetailView extends AppCompatActivity
{
    static class FetchLogDetails extends AsyncTask<LogDetailView, LogDetailItem, Integer>
    {
        @SuppressLint("StaticFieldLeak")
        LinearLayout layout;
        @SuppressLint("StaticFieldLeak")
        TextView lblLogMessage;
        @SuppressLint("StaticFieldLeak")
        BarChart logGraph;
        List<BarEntry> graphSeries;
        List<String> staticLabels;

        @Override
        protected void onPreExecute()
        {
            graphSeries = new ArrayList<>();
            staticLabels = new ArrayList<>();
        }

        @Override
        protected Integer doInBackground(LogDetailView... activity)
        {
            layout = activity[0].findViewById(R.id.logViewLayout);
            lblLogMessage = activity[0].findViewById(R.id.lblLogMessage);
            logGraph = activity[0].findViewById(R.id.logGraph);

            QuestionRecord[] records = LogDatabase.DAO.getDatesQuestionRecords(activity[0].date);

            logGraph.getXAxis().setValueFormatter((value, axis) ->
            {
                int key = Math.round(value);
                if ((key >= 0) && (key < staticLabels.size()))
                    return staticLabels.get(key);
                return "";
            });

            if (records.length == 0)
                return 0;

            logGraph.getAxisLeft().setAxisMaximum(100);
            logGraph.getAxisLeft().setAxisMinimum(0);
            logGraph.getAxisRight().setEnabled(false);
            logGraph.getXAxis().setGranularity(1);
            logGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            logGraph.getLegend().setEnabled(false);
            logGraph.getDescription().setText("");
            logGraph.setScaleYEnabled(false);

            for (QuestionRecord record : records)
            {
                LogDetailItem output = new LogDetailItem(activity[0].getBaseContext());
                output.setFromRecord(record);

                graphSeries.add(new BarEntry(staticLabels.size(), (record.getCorrectAnswers() * 100) /
                        (record.getCorrectAnswers() + record.getIncorrectAnswers())));

                staticLabels.add(record.getQuestion());

                if (isCancelled())
                    return null;
                else
                    publishProgress(output);
            }

            BarData data = new BarData(new BarDataSet(graphSeries, null));
            logGraph.setData(data);

            data.setBarWidth(0.9f);
            data.setDrawValues(false);

            logGraph.invalidate();

            return records.length;
        }

        @Override
        protected void onProgressUpdate(LogDetailItem... item)
        {
            layout.addView(item[0], layout.getChildCount() - 1);
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

    FetchLogDetails fetchThread;
    LocalDate date;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail_view);
        onConfigurationChanged(getResources().getConfiguration());

        //ref: https://stackoverflow.com/a/3913735/3582371
        Bundle extras = getIntent().getExtras();
        date = (LocalDate) extras.get("date");

        setTitle("Log Details: " + date);

        fetchThread = new FetchLogDetails();
        fetchThread.execute(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        int viewOrientation;

        int graphWidth;
        int graphHeight;

        int listWidth;
        int listHeight;

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            viewOrientation = LinearLayout.HORIZONTAL;

            graphWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300,
                    getApplicationContext().getResources().getDisplayMetrics()));
            graphHeight = MATCH_PARENT;

            listWidth = 0;
            listHeight = MATCH_PARENT;
        }
        else
        {
            viewOrientation = LinearLayout.VERTICAL;

            graphWidth = MATCH_PARENT;
            graphHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
                    getApplicationContext().getResources().getDisplayMetrics()));

            listWidth = MATCH_PARENT;
            listHeight = 0;
        }
        ((LinearLayout) findViewById(R.id.activity_log_detail_view)).setOrientation(viewOrientation);
        findViewById(R.id.logGraph).setLayoutParams(new LinearLayout.LayoutParams(graphWidth, graphHeight));
        findViewById(R.id.logViewScroll).setLayoutParams(new LinearLayout.LayoutParams(listWidth, listHeight, 1));
    }

    @Override
    protected void onDestroy()
    {
        if (fetchThread != null)
            fetchThread.cancel(true);
        super.onDestroy();
    }
}
