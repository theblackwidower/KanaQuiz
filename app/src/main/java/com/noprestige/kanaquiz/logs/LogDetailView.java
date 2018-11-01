package com.noprestige.kanaquiz.logs;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;
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
        GraphView logGraph;
        BarGraphSeries<DataPoint> graphSeries;
        List<String> staticLabels;

        @Override
        protected void onPreExecute()
        {
            graphSeries = new BarGraphSeries<>();
            staticLabels = new ArrayList<>();
        }

        @Override
        protected Integer doInBackground(LogDetailView... activity)
        {
            layout = activity[0].findViewById(R.id.logViewLayout);
            lblLogMessage = activity[0].findViewById(R.id.lblLogMessage);
            logGraph = activity[0].findViewById(R.id.logGraph);

            QuestionRecord[] records = LogDatabase.DAO.getDatesQuestionRecords(activity[0].date);

            logGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
            {
                @Override
                public String formatLabel(double value, boolean isValueX)
                {
                    if (isValueX)
                    {
                        int key = (int) Math.floor(value);
                        if ((key >= 0) && (key < staticLabels.size()))
                        {
                            double diff = value - key;
                            if ((diff >= 0.4) && (diff <= 0.6))
                                return staticLabels.get(key);
                        }
                        return null;
                    }
                    else
                        return super.formatLabel(value, isValueX);
                }
            });

            if (records.length == 0)
                return 0;

            graphSeries.setSpacing(10);
            graphSeries.setDataWidth(1.0);

            for (QuestionRecord record : records)
            {
                LogDetailItem output = new LogDetailItem(activity[0].getBaseContext());
                output.setFromRecord(record);

                graphSeries.appendData(new DataPoint(staticLabels.size() + 0.5, (record.getCorrectAnswers() * 100) /
                        (record.getCorrectAnswers() + record.getIncorrectAnswers())), true, 1000, true);

                staticLabels.add(record.getQuestion());

                if (isCancelled())
                    return null;
                else
                    publishProgress(output);
            }
            return records.length;
        }

        @Override
        protected void onProgressUpdate(LogDetailItem... item)
        {
            layout.addView(item[0], layout.getChildCount() - 1);
            logGraph.getViewport().setMaxX(staticLabels.size());
            logGraph.removeAllSeries();
            logGraph.addSeries(graphSeries);
            logGraph.getGridLabelRenderer().setNumHorizontalLabels(staticLabels.size() * 2);
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
            {
                layout.removeView(lblLogMessage);

                PointsGraphSeries<DataPoint> graphPadding = new PointsGraphSeries<>();
                graphPadding.setSize(0);
                graphPadding.appendData(new DataPoint(0, 0), true, 1000, true);
                graphPadding.appendData(new DataPoint(staticLabels.size(), 0), true, 1000, true);
                logGraph.addSeries(graphPadding);
            }
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
        setContentView(R.layout.activity_log_view);
        onConfigurationChanged(getResources().getConfiguration());

        //ref: https://stackoverflow.com/a/3913735/3582371
        Bundle extras = getIntent().getExtras();
        date = (LocalDate) extras.get("date");

        setTitle("Log Details: " + date);

        fetchThread = new FetchLogDetails();
        fetchThread.execute(this);

        GraphView logGraph = findViewById(R.id.logGraph);

        logGraph.getViewport().setYAxisBoundsManual(true);
        logGraph.getViewport().setMinY(0);
        logGraph.getViewport().setMaxY(100);
        logGraph.getViewport().setScalable(true); // X-axis zooming and scrolling
        logGraph.getViewport().setXAxisBoundsManual(true);
        logGraph.getViewport().setMinX(0);
        logGraph.getGridLabelRenderer().setLabelVerticalWidth(Math.round(TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
                        getApplicationContext().getResources().getDisplayMetrics())));
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
