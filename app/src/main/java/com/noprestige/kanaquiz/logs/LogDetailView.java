package com.noprestige.kanaquiz.logs;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.noprestige.kanaquiz.R;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

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
        TextView lblDetailMessage;
        @SuppressLint("StaticFieldLeak")
        BarChart logDetailChart;
        List<BarEntry> chartSeries;
        List<String> staticLabels;

        @Override
        protected void onPreExecute()
        {
            chartSeries = new ArrayList<>();
            staticLabels = new ArrayList<>();
        }

        public String formatXLabel(float value)
        {
            int key = Math.round(value);
            if ((key >= 0) && (key < staticLabels.size()))
                return staticLabels.get(key);
            return "";
        }

        @Override
        protected Integer doInBackground(LogDetailView... activity)
        {
            layout = activity[0].findViewById(R.id.logDetailViewLayout);
            lblDetailMessage = activity[0].findViewById(R.id.lblDetailMessage);
            logDetailChart = activity[0].findViewById(R.id.logDetailChart);

            QuestionRecord[] records = LogDatabase.DAO.getDatesQuestionRecords(activity[0].date);

            logDetailChart.getXAxis().setValueFormatter((value, axis) -> formatXLabel(value));

            if (records.length == 0)
                return 0;

            for (QuestionRecord record : records)
            {
                LogDetailItem output = new LogDetailItem(activity[0]);
                output.setFromRecord(record);

                chartSeries.add(new BarEntry(staticLabels.size(), (record.getCorrectAnswers() * 100) /
                        (record.getCorrectAnswers() + record.getIncorrectAnswers())));

                staticLabels.add(record.getQuestion());

                if (isCancelled())
                    return null;
                else
                    publishProgress(output);
            }

            BarData data = new BarData(new BarDataSet(chartSeries, null));
            logDetailChart.setData(data);

            data.setBarWidth(0.8f);
            data.setDrawValues(false);

            logDetailChart.invalidate();

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
            lblDetailMessage = null;
            logDetailChart = null;
        }

        @Override
        protected void onPostExecute(Integer count)
        {
            if (count > 0)
                layout.removeView(lblDetailMessage);
            else
                lblDetailMessage.setText(R.string.no_logs);
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

        //ref: https://stackoverflow.com/a/22992578/3582371
        //Only needed because the threeten backport has a different package name than java.time, which would require
        // no modification. I blame anyone not Oreo or newer.
        //ref: https://developer.android.com/reference/java/util/Formatter#ddt
        setTitle(getString(R.string.log_detail_title,
                date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000L));

        fetchThread = new FetchLogDetails();
        fetchThread.execute(this);

        BarChart logDetailChart = findViewById(R.id.logDetailChart);

        logDetailChart.getAxisLeft().setAxisMaximum(100);
        logDetailChart.getAxisLeft().setAxisMinimum(0);
        logDetailChart.getAxisRight().setEnabled(false);
        logDetailChart.getXAxis().setGranularity(1);
        logDetailChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        logDetailChart.getXAxis().setDrawGridLines(false);
        logDetailChart.getLegend().setEnabled(false);
        logDetailChart.getDescription().setText("");
        logDetailChart.setScaleYEnabled(false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        int viewOrientation;

        int chartWidth;
        int chartHeight;

        int listWidth;
        int listHeight;

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            viewOrientation = LinearLayout.HORIZONTAL;

            chartWidth = getResources().getDimensionPixelSize(R.dimen.landscapeChartWidth);
            chartHeight = MATCH_PARENT;

            listWidth = 0;
            listHeight = MATCH_PARENT;
        }
        else
        {
            viewOrientation = LinearLayout.VERTICAL;

            chartWidth = MATCH_PARENT;
            chartHeight = getResources().getDimensionPixelSize(R.dimen.portraitChartHeight);

            listWidth = MATCH_PARENT;
            listHeight = 0;
        }
        ((LinearLayout) findViewById(R.id.activity_log_detail_view)).setOrientation(viewOrientation);
        findViewById(R.id.logDetailChart).setLayoutParams(new LinearLayout.LayoutParams(chartWidth, chartHeight));
        findViewById(R.id.logDetailViewScroll).setLayoutParams(new LinearLayout.LayoutParams(listWidth, listHeight, 1));
    }

    @Override
    protected void onDestroy()
    {
        if (fetchThread != null)
            fetchThread.cancel(true);
        super.onDestroy();
    }
}
