package com.noprestige.kanaquiz.reference;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.questions.KanaQuestion;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class ReferenceCell extends View
{
    private String kana;
    private String romanji;

    private TextPaint kanaPaint = new TextPaint();
    private TextPaint romanjiPaint = new TextPaint();

    private float kanaXpoint;
    private float kanaYpoint;
    private float romanjiXpoint;
    private float romanjiYpoint;

    private float kanaWidth;
    private float romanjiWidth;

    private float kanaHeight;
    private float romanjiHeight;

    private static TypedArray defaultAttributes = null;

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
        Context context = this.getContext();

        if (defaultAttributes == null)
            defaultAttributes = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.textColorTertiary});

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ReferenceCell, defStyle, 0);

        setKana(a.getString(R.styleable.ReferenceCell_kana));
        setRomanji(a.getString(R.styleable.ReferenceCell_romanji));
        setKanaSize(a.getDimension(R.styleable.ReferenceCell_kanaSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 64, context.getResources().getDisplayMetrics())));
        setRomanjiSize(a.getDimension(R.styleable.ReferenceCell_romanjiSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics())));
        setColour(a.getColor(R.styleable.ReferenceCell_colour, defaultAttributes.getColor(0, 0)));

        a.recycle();

        kanaPaint.setAntiAlias(true);
        romanjiPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        int contentWidth = width - getPaddingLeft() - getPaddingRight();
        int contentHeight = height - getPaddingTop() - getPaddingBottom();

        float fullHeight = kanaHeight + romanjiHeight;

        kanaXpoint = getPaddingLeft() + (contentWidth - kanaWidth) / 2;
        kanaYpoint = getPaddingTop() + (contentHeight - fullHeight) / 2 - kanaPaint.getFontMetrics().ascent;
        romanjiXpoint = getPaddingLeft() + (contentWidth - romanjiWidth) / 2;
        romanjiYpoint = getPaddingTop() + (contentHeight + fullHeight) / 2 - romanjiPaint.getFontMetrics().descent;
    }

    //ref: http://stackoverflow.com/questions/13273838/onmeasure-wrap-content-how-do-i-know-the-size-to-wrap
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int desiredWidth = Math.round(Math.max(kanaWidth, romanjiWidth)) + getPaddingLeft() + getPaddingRight();
        int desiredHeight = Math.round(kanaHeight + romanjiHeight) + getPaddingTop() + getPaddingBottom();

        int width = calculateSize(widthMeasureSpec, desiredWidth);
        int height = calculateSize(heightMeasureSpec, desiredHeight);

        setMeasuredDimension(width, height);
    }

    static private int calculateSize(int measureSpec, int desired)
    {
        switch (MeasureSpec.getMode(measureSpec))
        {
            case MeasureSpec.EXACTLY:
                return MeasureSpec.getSize(measureSpec);
            case MeasureSpec.AT_MOST:
                return Math.min(desired, MeasureSpec.getSize(measureSpec));
            default:
                return desired;
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawText(kana, kanaXpoint, kanaYpoint, kanaPaint);
        canvas.drawText(romanji, romanjiXpoint, romanjiYpoint, romanjiPaint);
    }

    public String getKana()
    {
        return this.kana;
    }

    public float getKanaSize()
    {
        return kanaPaint.getTextSize();
    }

    public String getRomanji()
    {
        return this.romanji;
    }

    public float getRomanjiSize()
    {
        return romanjiPaint.getTextSize();
    }

    public int getColour()
    {
        return kanaPaint.getColor();
        //return romanjiPaint.getColor();
    }

    public void setKana(String kana)
    {
        this.kana = kana == null ? "" : kana;
        kanaWidth = kanaPaint.measureText(this.kana);
    }

    public void setKanaSize(float kanaSize)
    {
        kanaPaint.setTextSize(kanaSize);
        kanaHeight = kanaPaint.getFontMetrics().descent - kanaPaint.getFontMetrics().ascent;
        kanaWidth = kanaPaint.measureText(this.kana);
    }

    public void setRomanji(String romanji)
    {
        this.romanji = romanji == null ? "" : romanji;
        romanjiWidth = romanjiPaint.measureText(this.romanji);
    }

    public void setRomanjiSize(float romanjiSize)
    {
        romanjiPaint.setTextSize(romanjiSize);
        romanjiHeight = romanjiPaint.getFontMetrics().descent - romanjiPaint.getFontMetrics().ascent;
        romanjiWidth = romanjiPaint.measureText(this.romanji);
    }

    public void setColour(int colour)
    {
        kanaPaint.setColor(colour);
        romanjiPaint.setColor(colour);
    }

    public static TableRow buildSpecialRow(Context context, KanaQuestion[] questions)
    {
        if (questions == null)
            return null;
        else
        {
            TableRow row = new TableRow(context);

            switch (questions.length)
            {
                case 1:
                    row.addView(new View(context));
                    row.addView(new View(context));
                    row.addView(questions[0].generateReference(context));
                    break;
                case 2:
                    row.addView(questions[0].generateReference(context));
                    row.addView(new View(context));
                    row.addView(new View(context));
                    row.addView(new View(context));
                    row.addView(questions[1].generateReference(context));
                    break;
                case 3:
                    row.addView(questions[0].generateReference(context));
                    row.addView(new View(context));
                    row.addView(questions[1].generateReference(context));
                    row.addView(new View(context));
                    row.addView(questions[2].generateReference(context));
                    break;
                case 4:
                    row.addView(questions[0].generateReference(context));
                    row.addView(questions[1].generateReference(context));
                    row.addView(new View(context));
                    row.addView(questions[2].generateReference(context));
                    row.addView(questions[3].generateReference(context));
                    break;
                default:
                    for (KanaQuestion question : questions)
                        row.addView(question.generateReference(context));
            }
            return row;
        }
    }

    public static TableRow buildRow(Context context, KanaQuestion[] questions)
    {
        if (questions == null)
            return null;
        else
        {
            TableRow row = new TableRow(context);

            for (KanaQuestion question : questions)
                row.addView(question.generateReference(context));

            return row;
        }
    }

    static TextView buildHeader(Context context, int title)
    {
        TextView header = new TextView(context);
        header.setText(title);
        header.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        header.setTextSize(COMPLEX_UNIT_SP, 14);
        header.setPadding(0, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 28, context
                        .getResources().getDisplayMetrics())),
                0, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources()
                        .getDisplayMetrics())));
        header.setTypeface(header.getTypeface(), 1);
        header.setAllCaps(true);
        return header;
    }
}
