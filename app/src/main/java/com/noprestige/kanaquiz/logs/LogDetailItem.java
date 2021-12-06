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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.questions.QuestionType;
import com.noprestige.kanaquiz.themes.ThemeManager;

import java.text.DecimalFormat;
import java.util.Locale;

public class LogDetailItem extends View
{
    private int correctAnswers = -1;
    private int totalAnswers = -1;
    private String question = "";
    private QuestionType type;
    private boolean isDynamicSize = true;

    private String correctString = "";
    private String totalString = "";
    private String percentageString = "";

    private TextPaint mainPaint = new TextPaint();
    private TextPaint percentagePaint = new TextPaint();
    private TextPaint typePaint = new TextPaint();
    private Paint linePaint = new Paint();

    private float originalFontSize;
    private int desiredHeight;

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
        setMainColour(ThemeManager.getThemeColour(context, android.R.attr.textColorTertiary));

        mainPaint.setAntiAlias(true);
        typePaint.setAntiAlias(true);
        percentagePaint.setAntiAlias(true);

        Typeface font = ThemeManager.getDefaultThemeFont(context, Typeface.NORMAL);

        mainPaint.setTypeface(font);
        typePaint.setTypeface(font);
        percentagePaint.setTypeface(font);

        mainPaint.setTextLocale(Locale.JAPANESE);

        linePaint.setColor(ThemeManager.getThemeColour(context, android.R.attr.textColorPrimary));
        typePaint.setColor(ThemeManager.getThemeColour(context, android.R.attr.textColorTertiary));
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

        int width = calculateSize(widthMeasureSpec, desiredWidth);
        int height = calculateSize(heightMeasureSpec, desiredHeight);

        setMeasuredDimension(width, height);
    }

    private int desiredWidth()
    {
        return Math.round((Math.max(questionWidth + correctWidth +
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()),
                totalWidth + percentageWidth) * 2) + slashWidth) + getPaddingLeft() + getPaddingRight();
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

        if ((type != QuestionType.KANA) && (type != QuestionType.VOCABULARY))
            paintType(canvas);
    }

    private void paintType(Canvas canvas)
    {
        String typeString = null;
        if (type == QuestionType.KANJI)
            typeString = getResources().getString(R.string.kanji_question_type_option_meaning);
        else if (type == QuestionType.KUN_YOMI)
            typeString = getResources().getString(R.string.kanji_question_type_option_kunyomi);
        else if (type == QuestionType.ON_YOMI)
            typeString = getResources().getString(R.string.kanji_question_type_option_onyomi);

        typeString = '(' + typeString + ')';
        float padding = mainPaint.measureText(" ");
        float typeXPoint = questionXPoint + questionWidth + padding;
        float availableSpace = correctXPoint - typeXPoint - padding;

        typePaint.setTextSize(originalFontSize / 4f);
        while (typePaint.measureText(typeString) > availableSpace)
            typePaint.setTextSize(typePaint.getTextSize() - 1);

        canvas.drawText(typeString, typeXPoint, dataYPoint, typePaint);
    }

    public void correctFontSize()
    {
        if (mainPaint.getTextSize() != originalFontSize)
            forceFontSize(originalFontSize);
        if (isDynamicSize)
        {
            while (desiredWidth() > getWidth())
                forceFontSize(mainPaint.getTextSize() - 1);
            repositionItems(getWidth(), getHeight());
        }
    }

    public void setFromRecord(QuestionRecord record)
    {
        setQuestion(record.getQuestion());
        setType(record.getType());
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
        return originalFontSize;
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

    public void setType(QuestionType type)
    {
        this.type = type;
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
        originalFontSize = fontSize;
        forceFontSize(fontSize);
        desiredHeight = Math.round(textHeight + linePaint.getStrokeWidth()) + getPaddingTop() + getPaddingBottom() +
                (internalVerticalPadding * 2);
    }

    private void forceFontSize(float fontSize)
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
            percentagePaint.setColor(DailyLogItem.getPercentageColour(percentage, getContext()));

            questionWidth = mainPaint.measureText(question);
            correctWidth = mainPaint.measureText(correctString);
            slashWidth = mainPaint.measureText(SLASH);
            totalWidth = mainPaint.measureText(totalString);
            percentageWidth = percentagePaint.measureText(percentageString);
        }
    }
}
