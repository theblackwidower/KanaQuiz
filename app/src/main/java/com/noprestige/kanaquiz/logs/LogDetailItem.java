package com.noprestige.kanaquiz.logs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.noprestige.kanaquiz.R;

import java.text.DecimalFormat;
import java.util.Locale;

public class LogDetailItem extends View
{
    private int correctAnswers = -1;
    private int totalAnswers = -1;
    private String question = "";
    private boolean isDynamicSize = true;

    private String correctString = "";
    private String totalString = "";
    private String percentageString = "";

    private TextPaint mainPaint = new TextPaint();
    private TextPaint percentagePaint = new TextPaint();
    private Paint linePaint = new Paint();

    private float questionXPoint;
    private float correctXPoint;
    private float slashXPoint;
    private float totalXPoint;
    private float percentageXPoint;
    private float dataYPoint;

    private float lineXPoint1;
    private float lineXPoint2;
    private float lineYPoint;

    private float questionWidth;
    private float correctWidth;
    private float slashWidth;
    private float totalWidth;
    private float percentageWidth;

    private float textHeight;

    private int internalVerticalPadding;

    private static final DecimalFormat PERCENT_FORMATTER = new DecimalFormat("#0%");
    private static final String SLASH = "/";

    public LogDetailItem(Context context)
    {
        super(context);
        init(null, 0);
    }

    public LogDetailItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public LogDetailItem(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        Context context = getContext();

        setFontSize(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 42, context.getResources().getDisplayMetrics()));
        setMainColour(
                context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.textColorTertiary}).getColor(0, 0));

        mainPaint.setAntiAlias(true);
        percentagePaint.setAntiAlias(true);

        mainPaint.setTextLocale(Locale.JAPANESE);

        linePaint.setColor(
                context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.textColorPrimary}).getColor(0, 0));
        linePaint.setStrokeWidth(context.getResources().getDimension(R.dimen.dividingLine));

        internalVerticalPadding = getResources().getDimensionPixelSize(R.dimen.logItemInternalVerticalPadding);
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
        int contentHeight = height - getPaddingTop() - getPaddingBottom() - (internalVerticalPadding * 2);

        questionXPoint = getPaddingLeft();
        slashXPoint = getPaddingLeft() + ((contentWidth - slashWidth) / 2);
        correctXPoint = slashXPoint - correctWidth;
        totalXPoint = slashXPoint + slashWidth;
        percentageXPoint = getPaddingLeft() + (contentWidth - percentageWidth);
        dataYPoint = getPaddingTop() + internalVerticalPadding +
                (((contentHeight + textHeight) / 2) - mainPaint.getFontMetrics().descent);

        lineXPoint1 = getPaddingLeft();
        lineXPoint2 = width - getPaddingRight();
        lineYPoint = height - getPaddingBottom() - linePaint.getStrokeWidth();
    }

    //ref: http://stackoverflow.com/questions/13273838/onmeasure-wrap-content-how-do-i-know-the-size-to-wrap
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int desiredWidth = desiredWidth();
        int desiredHeight = Math.round(textHeight + linePaint.getStrokeWidth()) + getPaddingTop() + getPaddingBottom() +
                (internalVerticalPadding * 2);

        int width = calculateSize(widthMeasureSpec, desiredWidth);
        int height = calculateSize(heightMeasureSpec, desiredHeight);

        setMeasuredDimension(width, height);
    }

    private int desiredWidth()
    {
        return Math.round((Math.max(questionWidth + correctWidth, totalWidth + percentageWidth) * 2) + slashWidth) +
                getPaddingLeft() + getPaddingRight();
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

        canvas.drawText(question, questionXPoint, dataYPoint, mainPaint);
        canvas.drawText(correctString, correctXPoint, dataYPoint, mainPaint);
        canvas.drawText(SLASH, slashXPoint, dataYPoint, mainPaint);
        canvas.drawText(totalString, totalXPoint, dataYPoint, mainPaint);
        canvas.drawText(percentageString, percentageXPoint, dataYPoint, percentagePaint);
        canvas.drawLine(lineXPoint1, lineYPoint, lineXPoint2, lineYPoint, linePaint);
    }

    public void correctFontSize()
    {
        if (isDynamicSize)
        {
            while (desiredWidth() > getWidth())
                setFontSize(mainPaint.getTextSize() - 1);
            repositionItems(getWidth(), getHeight());
        }
    }

    public void setFromRecord(QuestionRecord record)
    {
        setQuestion(record.getQuestion());
        setCorrectAnswers(record.getCorrectAnswers());
        setTotalAnswers(record.getCorrectAnswers() + record.getIncorrectAnswers());
    }

    public String getQuestion()
    {
        return question;
    }

    public int getCorrectAnswers()
    {
        return correctAnswers;
    }

    public int getTotalAnswers()
    {
        return totalAnswers;
    }

    public float getFontSize()
    {
        return mainPaint.getTextSize();
    }

    public int getMainColour()
    {
        return mainPaint.getColor();
    }

    public boolean getIsDynamicSize()
    {
        return isDynamicSize;
    }

    public void setQuestion(String question)
    {
        this.question = question;
        updateAnswers();
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
        mainPaint.setTextSize(fontSize);
        percentagePaint.setTextSize(fontSize);

        textHeight = mainPaint.getFontMetrics().descent - mainPaint.getFontMetrics().ascent;

        questionWidth = mainPaint.measureText(question);
        correctWidth = mainPaint.measureText(correctString);
        slashWidth = mainPaint.measureText(SLASH);
        totalWidth = mainPaint.measureText(totalString);
        percentageWidth = percentagePaint.measureText(percentageString);
    }

    public void setMainColour(int colour)
    {
        mainPaint.setColor(colour);
    }

    public void setIsDynamicSize(boolean isDynamicSize)
    {
        this.isDynamicSize = isDynamicSize;
    }

    private void updateAnswers()
    {
        if ((correctAnswers >= 0) && (totalAnswers >= 0))
        {
            correctString = DailyLogItem.parseCount(correctAnswers);
            totalString = DailyLogItem.parseCount(totalAnswers);
            float percentage = correctAnswers / (float) totalAnswers;
            percentageString = PERCENT_FORMATTER.format(percentage);
            percentagePaint.setColor(DailyLogItem.getPercentageColour(percentage, getResources()));

            questionWidth = mainPaint.measureText(question);
            correctWidth = mainPaint.measureText(correctString);
            slashWidth = mainPaint.measureText(SLASH);
            totalWidth = mainPaint.measureText(totalString);
            percentageWidth = percentagePaint.measureText(percentageString);
        }
    }
}
