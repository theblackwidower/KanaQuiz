/*
 *    Copyright 2021 T Duke Perry
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.noprestige.kanaquiz.logs;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.themes.ThemeManager;

import java.time.LocalDate;

import androidx.appcompat.app.AppCompatActivity;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static java.time.temporal.ChronoUnit.DAYS;

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
            layout = activity[0].findViewById(R.id.logViewLayout);
            lblLogMessage = activity[0].findViewById(R.id.lblLogMessage);
            logGraph = activity[0].findViewById(R.id.logGraph);

            DailyRecord[] records = LogDatabase.DAO.getAllDailyRecords();

            if (records.length == 0)
                return 0;

            graphSeries.setColor(ThemeManager.getThemeColour(activity[0], R.attr.colorAccent));
            startDate = records[0].getDate();

            for (DailyRecord record : records)
            {
                DailyLogItem output = new DailyLogItem(activity[0]);
                output.setFromRecord(record);

                graphSeries.appendData(new DataPoint(startDate.until(record.getDate(), DAYS),
                        (record.getCorrectAnswers().getDecimal() / record.getTotalAnswers()) * 100f), true, 1000, true);

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
            logGraph.getViewport().setMaxX(startDate.until(item[0].getDate(), DAYS));
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
        ThemeManager.setTheme(this);
        setContentView(R.layout.activity_log_view);
        onConfigurationChanged(getResources().getConfiguration());

        fetchThread = new FetchLogs();
        fetchThread.execute(this);

        GraphView logGraph = findViewById(R.id.logGraph);

        logGraph.getViewport().setYAxisBoundsManual(true);
        logGraph.getViewport().setMinY(0);
        logGraph.getViewport().setMaxY(100);
        logGraph.getViewport().setScalable(true); // X-axis zooming and scrolling
        logGraph.getViewport().setXAxisBoundsManual(true);
        logGraph.getViewport().setMinX(0);
        logGraph.getGridLabelRenderer().setLabelVerticalWidth(Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics())));
        logGraph.getGridLabelRenderer().setLabelHorizontalHeight(0);

        StaticLabelsFormatter labelFormatter = new StaticLabelsFormatter(logGraph);
        labelFormatter.setHorizontalLabels(new String[]{" ", " "});
        logGraph.getGridLabelRenderer().setLabelFormatter(labelFormatter);
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

            graphWidth = getResources().getDimensionPixelSize(R.dimen.landscapeChartWidth);
            graphHeight = MATCH_PARENT;

            listWidth = 0;
            listHeight = MATCH_PARENT;
        }
        else
        {
            viewOrientation = LinearLayout.VERTICAL;

            graphWidth = MATCH_PARENT;
            graphHeight = getResources().getDimensionPixelSize(R.dimen.portraitChartHeight);

            listWidth = MATCH_PARENT;
            listHeight = 0;
        }
        ((LinearLayout) findViewById(R.id.activity_log_view)).setOrientation(viewOrientation);
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
