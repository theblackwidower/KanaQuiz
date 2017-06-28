package com.noprestige.kanaquiz;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class ReferenceCell extends View
{
    private String kana;
    private String romanji;
    private float kanaSize;
    private float romanjiSize;
    private int colour;

    private TextPaint kanaPaint = new TextPaint();
    private TextPaint romanjiPaint = new TextPaint();

    private float kanaXpoint;
    private float kanaYpoint;
    private float romanjiXpoint;
    private float romanjiYpoint;

    private float kanaWidth;
    private float romanjiWidth;

    private float fullHeight;

    public ReferenceCell(Context context)
    {
        super(context);
        init(null, 0);
    }

    public ReferenceCell(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public ReferenceCell(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ReferenceCell, defStyle, 0);

        Context context = this.getContext();

        kana = a.getString(R.styleable.ReferenceCell_kana);
        romanji = a.getString(R.styleable.ReferenceCell_romanji);
        kanaSize = a.getDimension(R.styleable.ReferenceCell_kanaSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 64, context.getResources().getDisplayMetrics()));
        romanjiSize = a.getDimension(R.styleable.ReferenceCell_romanjiSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics()));
        colour = a.getColor(R.styleable.ReferenceCell_colour,
                new TextView(context).getTextColors().getDefaultColor()); // TODO: Fix this kludge used to fetch default colour

        a.recycle();

        if (kana == null)
            kana = "";
        if (romanji == null)
            romanji = "";

        kanaPaint.setAntiAlias(true);
        romanjiPaint.setAntiAlias(true);

        updateObject();
    }

    private void updateObject()
    {
        kanaPaint.setTextSize(kanaSize);
        romanjiPaint.setTextSize(romanjiSize);

        kanaPaint.setColor(colour);
        romanjiPaint.setColor(colour);

        kanaWidth = kanaPaint.measureText(kana);
        romanjiWidth = romanjiPaint.measureText(romanji);
        float kanaHeight = kanaPaint.getFontMetrics().descent - kanaPaint.getFontMetrics().ascent;
        float romanjiHeight = romanjiPaint.getFontMetrics().descent - romanjiPaint.getFontMetrics().ascent;
        fullHeight = kanaHeight + romanjiHeight;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = width - paddingLeft - paddingRight;
        int contentHeight = height - paddingTop - paddingBottom;

        kanaXpoint = paddingLeft + (contentWidth - kanaWidth) / 2;
        kanaYpoint = paddingTop + (contentHeight - fullHeight) / 2 - kanaPaint.getFontMetrics().ascent;
        romanjiXpoint = paddingLeft + (contentWidth - romanjiWidth) / 2;
        romanjiYpoint = paddingTop + (contentHeight + fullHeight) / 2 - romanjiPaint.getFontMetrics().descent;
    }

    //ref: http://stackoverflow.com/questions/13273838/onmeasure-wrap-content-how-do-i-know-the-size-to-wrap
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int desiredWidth = Math.round(Math.max(kanaWidth, romanjiWidth));
        int desiredHeight = Math.round(fullHeight);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY)
            width = widthSize;
        else if (widthMode == MeasureSpec.AT_MOST)
            width = Math.min(desiredWidth, widthSize);
        else
            width = desiredWidth;

        if (heightMode == MeasureSpec.EXACTLY)
            height = heightSize;
        else if (heightMode == MeasureSpec.AT_MOST)
            height = Math.min(desiredHeight, heightSize);
        else
            height = desiredHeight;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawText(kana, kanaXpoint, kanaYpoint, kanaPaint);
        canvas.drawText(romanji, romanjiXpoint, romanjiYpoint, romanjiPaint);
    }

    void setKana(String kana)
    {
        this.kana = kana;
        updateObject();
    }

    void setRomanji(String romanji)
    {
        this.romanji = romanji;
        updateObject();
    }

    static TableRow buildRow(Context context, KanaQuestion[] questions)
    {
        TableRow row = new TableRow(context);

        if (questions.length == 1)
        {
            row.addView(new TextView(context));
            row.addView(new TextView(context));
            row.addView(questions[0].generateReference(context));
        }
        else if (questions.length == 2)
        {
            row.addView(questions[0].generateReference(context));
            row.addView(new TextView(context));
            row.addView(new TextView(context));
            row.addView(new TextView(context));
            row.addView(questions[1].generateReference(context));
        }
        else if (questions.length == 3)
        {
            row.addView(questions[0].generateReference(context));
            row.addView(new TextView(context));
            row.addView(questions[1].generateReference(context));
            row.addView(new TextView(context));
            row.addView(questions[2].generateReference(context));
        }
        else
        {
            for (KanaQuestion question : questions)
            {
                row.addView(question.generateReference(context));
            }
        }
        return row;
    }

    static TextView buildHeader(Context context, int title)
    {
        TextView header = new TextView(context);
        header.setText(title);
        header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setTextSize(COMPLEX_UNIT_SP, 14);
        header.setPadding(0, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics())),
                0, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics())));
        header.setTypeface(header.getTypeface(), 1);
        header.setAllCaps(true);
        return header;
    }
}
