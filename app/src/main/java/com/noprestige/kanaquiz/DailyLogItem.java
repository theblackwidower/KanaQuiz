package com.noprestige.kanaquiz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.APRIL;
import static java.util.Calendar.AUGUST;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.FEBRUARY;
import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.JULY;
import static java.util.Calendar.JUNE;
import static java.util.Calendar.MARCH;
import static java.util.Calendar.MAY;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.NOVEMBER;
import static java.util.Calendar.OCTOBER;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SEPTEMBER;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.THURSDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;
import static java.util.Calendar.YEAR;

public class DailyLogItem extends LinearLayout
{
    private String prefId;

    private TextView lblDate;
    private TextView lblRatio;
    private TextView lblPercentage;

    private int correctAnswers = 0;
    private int totalAnswers = 0;

    private static final DecimalFormat PERCENT_FORMATTER = new DecimalFormat("#0.0%");

    public DailyLogItem(Context context)
    {
        super(context);
        init(null, 0);
    }

    public DailyLogItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public DailyLogItem(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        // Set up initial objects
        LayoutInflater.from(getContext()).inflate(R.layout.daily_log_item, this);

        lblDate = findViewById(R.id.lblDate);
        lblRatio = findViewById(R.id.lblRatio);
        lblPercentage = findViewById(R.id.lblPercentage);

    }

    public void setFromRecord(LogDailyRecord record)
    {
        setDate(record.date);
        setCorrectAnswers(record.correct_answers);
        setTotalAnswers(record.correct_answers + record.incorrect_answers);
    }

    public void setDate(Date date)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case SUNDAY:
                lblDate.setText("Sun");
                break;
            case MONDAY:
                lblDate.setText("Mon");
                break;
            case TUESDAY:
                lblDate.setText("Tue");
                break;
            case WEDNESDAY:
                lblDate.setText("Wed");
                break;
            case THURSDAY:
                lblDate.setText("Thu");
                break;
            case FRIDAY:
                lblDate.setText("Fri");
                break;
            case SATURDAY:
                lblDate.setText("Sat");
                break;
            default:
                lblDate.setText("Err");
        }

        lblDate.append(System.getProperty("line.separator"));

        switch (calendar.get(Calendar.MONTH))
        {
            case JANUARY:
                lblDate.append("Jan");
                break;
            case FEBRUARY:
                lblDate.append("Feb");
                break;
            case MARCH:
                lblDate.append("Mar");
                break;
            case APRIL:
                lblDate.append("Apr");
                break;
            case MAY:
                lblDate.append("May");
                break;
            case JUNE:
                lblDate.append("Jun");
                break;
            case JULY:
                lblDate.append("Jul");
                break;
            case AUGUST:
                lblDate.append("Aug");
                break;
            case SEPTEMBER:
                lblDate.append("Sep");
                break;
            case OCTOBER:
                lblDate.append("Oct");
                break;
            case NOVEMBER:
                lblDate.append("Nov");
                break;
            case DECEMBER:
                lblDate.append("Dec");
                break;
            default:
                lblDate.append("Err");
        }

        lblDate.append(" ");
        lblDate.append(Integer.toString(calendar.get(DAY_OF_MONTH)));
        lblDate.append(System.getProperty("line.separator"));
        lblDate.append(Integer.toString(calendar.get(YEAR)));
    }

    public void setCorrectAnswers(int correctAnswers)
    {
        this.correctAnswers = correctAnswers;
        updateAnswers();
    }

    public void setTotalAnswers(int totalAnswers)
    {
        this.totalAnswers = totalAnswers;
        updateAnswers();
    }

    private void updateAnswers()
    {
        if (correctAnswers > 0 && totalAnswers > 0)
        {
            lblRatio.setText(Integer.toString(correctAnswers) + "/" + Integer.toString(totalAnswers));
            float percentage = (float) correctAnswers / (float) totalAnswers;
            lblPercentage.setText(PERCENT_FORMATTER.format(percentage));
            if (percentage < 0.6)
                lblPercentage.setTextColor(getResources().getColor(R.color.below_sixty));
            else if (percentage < 0.7)
                lblPercentage.setTextColor(getResources().getColor(R.color.sixty_to_seventy));
            else if (percentage < 0.8)
                lblPercentage.setTextColor(getResources().getColor(R.color.seventy_to_eighty));
            else if (percentage < 0.9)
                lblPercentage.setTextColor(getResources().getColor(R.color.eighty_to_ninty));
            else
                lblPercentage.setTextColor(getResources().getColor(R.color.above_ninty));
        }
    }
}
