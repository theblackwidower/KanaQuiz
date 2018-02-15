package com.noprestige.kanaquiz.logs;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

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

public class DailyLogItem extends View
{
    private int correctAnswers = -1;
    private int totalAnswers = -1;
    private Date date;
    private boolean isDynamicSize;

    private String dateString_1 = "";
    private String dateString_3 = "";
    private String dateString_2 = "";
    private String correctString = "";
    private String totalString = "";
    private String percentageString = "";

    private TextPaint datePaint = new TextPaint();
    private TextPaint ratioPaint = new TextPaint();
    private TextPaint percentagePaint = new TextPaint();
    private Paint linePaint = new Paint();

    private float dateXpoint;
    private float dateYpoint_1;
    private float dateYpoint_2;
    private float dateYpoint_3;
    private float correctXpoint;
    private float slashXpoint;
    private float totalXpoint;
    private float percentageXpoint;
    private float dataYpoint;

    private float lineXpoint_1;
    private float lineXpoint_2;
    private float lineYpoint;

    private float dateWidth_1;
    private float dateWidth_2;
    private float dateWidth_3;
    private float correctWidth;
    private float slashWidth;
    private float totalWidth;
    private float percentageWidth;

    private float dateHeight;
    private float dataHeight;

    private int internalVerticalPadding;

    private static TypedArray defaultAttributes = null;

    private static final DecimalFormat PERCENT_FORMATTER = new DecimalFormat("#0%");
    private static final SimpleDateFormat DATE_INPUT_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final String SLASH = "/";

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

        if (defaultAttributes == null)
            defaultAttributes = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.textColorTertiary});

        // Load attributes
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DailyLogItem, defStyle, 0);

        setDate(a.getString(R.styleable.DailyLogItem_date));
        setCorrectAnswers(a.getInt(R.styleable.DailyLogItem_correctAnswers, -1));
        setTotalAnswers(a.getInt(R.styleable.DailyLogItem_totalAnswers, -1));
        setFontSize(a.getDimension(R.styleable.DailyLogItem_fontSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics())));
        setMainColour(a.getColor(R.styleable.DailyLogItem_mainColour, defaultAttributes.getColor(0, 0)));
        setIsDynamicSize(a.getBoolean(R.styleable.DailyLogItem_isDynamicSize, true));

        a.recycle();

        datePaint.setAntiAlias(true);
        ratioPaint.setAntiAlias(true);
        percentagePaint.setAntiAlias(true);

        linePaint.setColor(context.getResources().getColor(R.color.dividingLine));
        linePaint.setStrokeWidth(context.getResources().getDimension(R.dimen.dividingLine));

        internalVerticalPadding = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, context.getResources().getDisplayMetrics()));
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        repositionItems(width, height);
    }

    private void repositionItems(int width, int height)
    {
        int contentWidth = width - getPaddingLeft() - getPaddingRight();
        int contentHeight = height - getPaddingTop() - getPaddingBottom() - internalVerticalPadding * 2;

        dateXpoint = getPaddingLeft();
        dateYpoint_1 = getPaddingTop() + internalVerticalPadding +
                (contentHeight - dateHeight) / 2 -
                datePaint.getFontMetrics().descent;
        dateYpoint_2 = dateYpoint_1 + dateHeight;
        dateYpoint_3 = dateYpoint_2 + dateHeight;

        slashXpoint = getPaddingLeft() + (contentWidth - slashWidth) / 2;

        correctXpoint = slashXpoint - correctWidth;
        totalXpoint = slashXpoint + slashWidth;
        percentageXpoint = getPaddingLeft() + contentWidth - percentageWidth;
        dataYpoint = getPaddingTop() + internalVerticalPadding +
                (contentHeight + dataHeight) / 2 -
                ratioPaint.getFontMetrics().descent;

        lineXpoint_1 = getPaddingLeft();
        lineXpoint_2 = width - getPaddingRight();
        lineYpoint = height - getPaddingBottom() - linePaint.getStrokeWidth();
    }

    //ref: http://stackoverflow.com/questions/13273838/onmeasure-wrap-content-how-do-i-know-the-size-to-wrap
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int desiredWidth = desiredWidth();
        int desiredHeight = Math.round(Math.max(dateHeight * 3, dataHeight) + linePaint.getStrokeWidth()) +
                getPaddingTop() + getPaddingBottom() + internalVerticalPadding * 2;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = measure(widthMode, widthSize, desiredWidth);
        int height = measure(heightMode, heightSize, desiredHeight);

        setMeasuredDimension(width, height);
    }

    private int desiredWidth()
    {
        return Math.round(
                Math.max(Math.max(dateWidth_1, Math.max(dateWidth_2, dateWidth_3)) + correctWidth,
                        totalWidth + percentageWidth) * 2 + slashWidth) +
                getPaddingLeft() + getPaddingRight();
    }

    static private int measure(int mode, int size, int desired)
    {
        switch (mode)
        {
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.AT_MOST:
                return Math.min(desired, size);
            default:
                return desired;
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        correctFontSize();

        canvas.drawText(dateString_1, dateXpoint, dateYpoint_1, datePaint);
        canvas.drawText(dateString_2, dateXpoint, dateYpoint_2, datePaint);
        canvas.drawText(dateString_3, dateXpoint, dateYpoint_3, datePaint);
        canvas.drawText(correctString, correctXpoint, dataYpoint, ratioPaint);
        canvas.drawText(SLASH, slashXpoint, dataYpoint, ratioPaint);
        canvas.drawText(totalString, totalXpoint, dataYpoint, ratioPaint);
        canvas.drawText(percentageString, percentageXpoint, dataYpoint, percentagePaint);
        canvas.drawLine(lineXpoint_1, lineYpoint, lineXpoint_2, lineYpoint, linePaint);
    }

    public void correctFontSize()
    {
        if (isDynamicSize)
        {
            while (desiredWidth() > getWidth())
                setFontSize(getFontSize() - 1);
            repositionItems(getWidth(), getHeight());
        }
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

    public float getFontSize()
    {
        return datePaint.getTextSize();
    }

    public int getMainColour()
    {
        return datePaint.getColor();
    }

    public boolean getIsDynamicSize()
    {
        return isDynamicSize;
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

        dateString_1 = calendar.getDisplayName(DAY_OF_WEEK, LONG, Locale.getDefault());

        dateString_2 = calendar.getDisplayName(MONTH, LONG, Locale.getDefault());
        dateString_2 += " ";
        dateString_2 += Integer.toString(calendar.get(DAY_OF_MONTH));

        dateString_3 = Integer.toString(calendar.get(YEAR));

        dateWidth_1 = datePaint.measureText(dateString_1);
        dateWidth_2 = datePaint.measureText(dateString_2);
        dateWidth_3 = datePaint.measureText(dateString_3);
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

    public void setFontSize(float fontSize)
    {
        datePaint.setTextSize(fontSize);

        fontSize *= 3;

        ratioPaint.setTextSize(fontSize);
        percentagePaint.setTextSize(fontSize);

        dateHeight = datePaint.getFontMetrics().descent - datePaint.getFontMetrics().ascent;
        dataHeight = ratioPaint.getFontMetrics().descent - ratioPaint.getFontMetrics().ascent;

        dateWidth_1 = datePaint.measureText(dateString_1);
        dateWidth_2 = datePaint.measureText(dateString_2);
        dateWidth_3 = datePaint.measureText(dateString_3);

        correctWidth = ratioPaint.measureText(correctString);
        slashWidth = ratioPaint.measureText(SLASH);
        totalWidth = ratioPaint.measureText(totalString);
        percentageWidth = percentagePaint.measureText(percentageString);
    }

    public void setMainColour(int colour)
    {
        datePaint.setColor(colour);
        ratioPaint.setColor(colour);
    }

    public void setIsDynamicSize(boolean isDynamicSize)
    {
        this.isDynamicSize = isDynamicSize;
    }

    static private String parseCount(int count)
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
            correctString = parseCount(correctAnswers);
            totalString = parseCount(totalAnswers);
            float percentage = (float) correctAnswers / (float) totalAnswers;
            percentageString = PERCENT_FORMATTER.format(percentage);
            percentagePaint.setColor(getPercentageColour(percentage, getResources()));

            correctWidth = ratioPaint.measureText(correctString);
            slashWidth = ratioPaint.measureText(SLASH);
            totalWidth = ratioPaint.measureText(totalString);
            percentageWidth = percentagePaint.measureText(percentageString);
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
