package com.noprestige.kanaquiz.logs;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noprestige.kanaquiz.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.LONG;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class DailyLogItem extends LinearLayout
{
    private TextView lblDate;
    private TextView lblCorrect;
    private TextView lblTotal;
    private TextView lblPercentage;

    private int correctAnswers = -1;
    private int totalAnswers = -1;

    private static final DecimalFormat PERCENT_FORMATTER = new DecimalFormat("#0%");
    private static final SimpleDateFormat DATE_INPUT_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

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
        Context context = this.getContext();

        // Set up initial objects
        LayoutInflater.from(context).inflate(R.layout.daily_log_item, this);

        lblDate = findViewById(R.id.lblDate);
        lblCorrect = findViewById(R.id.lblCorrect);
        lblTotal = findViewById(R.id.lblTotal);
        lblPercentage = findViewById(R.id.lblPercentage);

        // Load attributes
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DailyLogItem, defStyle, 0);

        setDate(a.getString(R.styleable.DailyLogItem_date));
        setCorrectAnswers(a.getInt(R.styleable.DailyLogItem_correct_answers, -1));
        setTotalAnswers(a.getInt(R.styleable.DailyLogItem_total_answers, -1));

        a.recycle();
    }

    public void setFromRecord(DailyRecord record)
    {
        setDate(record.date);
        setCorrectAnswers(record.correct_answers);
        setTotalAnswers(record.correct_answers + record.incorrect_answers);
    }

    public boolean setDate(String date)
    {
        if (date == null)
            return false;
        else
            try
            {
                setDate(DATE_INPUT_FORMATTER.parse(date));
                return true;
            }
            catch (ParseException ex)
            {
                return false;
            }
    }

    public void setDate(Date date)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        lblDate.setText(calendar.getDisplayName(DAY_OF_WEEK, LONG, Locale.getDefault()));
        lblDate.append(System.getProperty("line.separator"));
        lblDate.append(calendar.getDisplayName(MONTH, LONG, Locale.getDefault()));
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
        if (correctAnswers >= 0 && totalAnswers >= 0)
        {
            lblCorrect.setText(Integer.toString(correctAnswers) + "/");
            lblTotal.setText(Integer.toString(totalAnswers));
            float percentage = (float) correctAnswers / (float) totalAnswers;
            lblPercentage.setText(PERCENT_FORMATTER.format(percentage));
            if (percentage < 0.5)
                lblPercentage.setTextColor(getResources().getColor(R.color.below_fifty));
            else if (percentage < 0.6)
                lblPercentage.setTextColor(getResources().getColor(R.color.fifty_to_sixty));
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
