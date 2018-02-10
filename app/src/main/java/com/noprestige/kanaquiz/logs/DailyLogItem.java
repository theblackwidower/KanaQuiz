package com.noprestige.kanaquiz.logs;

import android.content.Context;
import android.content.res.Resources;
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
    private Date date;

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

    public Date getDate()
    {
        return this.date;
    }

    public int getCorrectAnswers()
    {
        return this.correctAnswers;
    }

    public int getTotalAnswers()
    {
        return this.totalAnswers;
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
        this.date = date;

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

    private String parseCount(int count)
    {
        if (count < 1000)
            return Integer.toString(count);
        else if (count < 10000)
            return Float.toString(
                    Math.round((float) count / 100f)
                            / 10f) + "k";
        else //if (count < 100000)
            return Integer.toString(
                    Math.round((float) count / 1000f)) + "k";
    }
    private void updateAnswers()
    {
        if (correctAnswers >= 0 && totalAnswers >= 0)
        {
            lblCorrect.setText(parseCount(correctAnswers) + "/");
            lblTotal.setText(parseCount(totalAnswers));
            float percentage = (float) correctAnswers / (float) totalAnswers;
            lblPercentage.setText(PERCENT_FORMATTER.format(percentage));
            lblPercentage.setTextColor(getPercentageColour(percentage, getResources()));
        }
    }

    private static int getPercentageColour(float percentage, Resources resources)
    {
        int tenth = Math.round(percentage * 100) / 10;
        if (tenth <= 4)
            return resources.getColor(R.color.below_fifty);
        else if (tenth == 5)
            return resources.getColor(R.color.fifty_to_sixty);
        else if (tenth == 6)
            return resources.getColor(R.color.sixty_to_seventy);
        else if (tenth == 7)
            return resources.getColor(R.color.seventy_to_eighty);
        else if (tenth == 8)
            return resources.getColor(R.color.eighty_to_ninty);
        else //if (tenth >= 9)
            return resources.getColor(R.color.above_ninty);
    }
}
