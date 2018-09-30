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

import com.noprestige.kanaquiz.Fraction;
import com.noprestige.kanaquiz.R;

import org.joda.time.LocalDate;

import java.text.DecimalFormat;

public class DailyLogItem extends View
{
    private float correctAnswers = -1;
    private int totalAnswers = -1;
    private LocalDate date;
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

    private static TypedArray defaultAttributes;

    private static final DecimalFormat PERCENT_FORMATTER = new DecimalFormat("#0%");
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
        Context context = getContext();

        if (defaultAttributes == null)
            defaultAttributes = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.textColorTertiary});

        // Load attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DailyLogItem, defStyle, 0);

        setDate(a.getString(R.styleable.DailyLogItem_date));
        setCorrectAnswers(a.getFloat(R.styleable.DailyLogItem_correctAnswers, -1));
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

        internalVerticalPadding = Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, context.getResources().getDisplayMetrics()));
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
        dateYpoint_1 = getPaddingTop() + internalVerticalPadding + (contentHeight - dateHeight) / 2 -
                datePaint.getFontMetrics().descent;
        dateYpoint_2 = dateYpoint_1 + dateHeight;
        dateYpoint_3 = dateYpoint_2 + dateHeight;

        slashXpoint = getPaddingLeft() + (contentWidth - slashWidth) / 2;

        correctXpoint = slashXpoint - correctWidth;
        totalXpoint = slashXpoint + slashWidth;
        percentageXpoint = getPaddingLeft() + contentWidth - percentageWidth;
        dataYpoint = getPaddingTop() + internalVerticalPadding + (contentHeight + dataHeight) / 2 -
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
        int desiredHeight =
                Math.round(Math.max(dateHeight * 3, dataHeight) + linePaint.getStrokeWidth()) + getPaddingTop() +
                        getPaddingBottom() + internalVerticalPadding * 2;

        int width = calculateSize(widthMeasureSpec, desiredWidth);
        int height = calculateSize(heightMeasureSpec, desiredHeight);

        setMeasuredDimension(width, height);
    }

    private int desiredWidth()
    {
        return Math.round(Math.max(Math.max(dateWidth_1, Math.max(dateWidth_2, dateWidth_3)) + correctWidth,
                totalWidth + percentageWidth) * 2 + slashWidth) + getPaddingLeft() + getPaddingRight();
    }

    private static int calculateSize(int measureSpec, int desired)
    {
        switch (MeasureSpec.getMode(measureSpec))
        {
            case MeasureSpec.EXACTLY:
                return MeasureSpec.getSize(measureSpec);
            case MeasureSpec.AT_MOST:
                return Math.min(desired, MeasureSpec.getSize(measureSpec));
            case MeasureSpec.UNSPECIFIED:
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
                setDataFontSize(ratioPaint.getTextSize() - 1);
            repositionItems(getWidth(), getHeight());
        }
    }

    public void setFromRecord(DailyRecord record)
    {
        setDate(record.date);
        setCorrectAnswers(record.correct_answers);
        setTotalAnswers(record.total_answers);
    }

    public LocalDate getDate()
    {
        return date;
    }

    public float getCorrectAnswers()
    {
        return correctAnswers;
    }

    public int getTotalAnswers()
    {
        return totalAnswers;
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
        {
            setDate(LocalDate.parse(date));
            return true;
        }
    }

    public void setDate(LocalDate date)
    {
        this.date = date;


        dateString_1 = date.toString("EEEE"); // Output day of week by full name

        dateString_2 = date.toString("MMMM"); // Output month by full name
        dateString_2 += " ";
        dateString_2 += Integer.toString(date.getDayOfMonth());

        dateString_3 = Integer.toString(date.getYear());

        dateWidth_1 = datePaint.measureText(dateString_1);
        dateWidth_2 = datePaint.measureText(dateString_2);
        dateWidth_3 = datePaint.measureText(dateString_3);
    }

    public void setCorrectAnswers(float correctAnswers)
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

        dateHeight = datePaint.getFontMetrics().descent - datePaint.getFontMetrics().ascent;

        dateWidth_1 = datePaint.measureText(dateString_1);
        dateWidth_2 = datePaint.measureText(dateString_2);
        dateWidth_3 = datePaint.measureText(dateString_3);

        setDataFontSize(fontSize * 3);
    }

    private void setDataFontSize(float fontSize)
    {
        ratioPaint.setTextSize(fontSize);
        percentagePaint.setTextSize(fontSize);

        dataHeight = ratioPaint.getFontMetrics().descent - ratioPaint.getFontMetrics().ascent;

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

    private static String parseCount(float count)
    {
        return count < 100 ? new Fraction(count).toString() : parseCount(Math.round(count));
    }

    private static String parseCount(int count)
    {
        if (count < 1000)
            return Integer.toString(count);
        else if (count < 10000)
            return Float.toString(Math.round((float) count / 100f) / 10f) + "k";
        else //if (count < 100000)
            return Integer.toString(Math.round((float) count / 1000f)) + "k";
    }

    private void updateAnswers()
    {
        if (correctAnswers >= 0 && totalAnswers >= 0)
        {
            correctString = parseCount(correctAnswers);
            totalString = parseCount(totalAnswers);
            float percentage = correctAnswers / (float) totalAnswers;
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
            return resources.getColor(R.color.eighty_to_ninety);
        else //if (tenth >= 9)
            return resources.getColor(R.color.above_ninety);
    }
}
