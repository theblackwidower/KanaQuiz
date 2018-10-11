package com.noprestige.kanaquiz.reference;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.questions.Question;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class ReferenceCell extends View
{
    private String kana;
    private String romanji;

    private TextPaint kanaPaint = new TextPaint();
    private TextPaint romanjiPaint = new TextPaint();

    private float kanaXPoint;
    private float kanaYPoint;
    private float romanjiXPoint;
    private float romanjiYPoint;

    private float kanaWidth;
    private float romanjiWidth;

    private float kanaHeight;
    private float romanjiHeight;

    private static TypedArray defaultAttributes;

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
        Context context = getContext();

        if (defaultAttributes == null)
            defaultAttributes = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.textColorTertiary});

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ReferenceCell, defStyle, 0);

        setKana(a.getString(R.styleable.ReferenceCell_kana));
        setRomanji(a.getString(R.styleable.ReferenceCell_romanji));
        setKanaSize(a.getDimension(R.styleable.ReferenceCell_kanaSize,
                TypedValue.applyDimension(COMPLEX_UNIT_SP, 64, context.getResources().getDisplayMetrics())));
        setRomanjiSize(a.getDimension(R.styleable.ReferenceCell_romanjiSize,
                TypedValue.applyDimension(COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics())));
        setColour(a.getColor(R.styleable.ReferenceCell_colour, defaultAttributes.getColor(0, 0)));

        a.recycle();

        setOnLongClickListener(view -> copyItem());

        kanaPaint.setAntiAlias(true);
        romanjiPaint.setAntiAlias(true);
    }

    private boolean copyItem()
    {
        //TODO: Allow the ability to select and copy multiple items at once.
        String data = kana + " - " + romanji;
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText("Kana Reference", data));

        String message = "'" + data + "' " + getContext().getResources().getString(R.string.clipboard_message);
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        int contentWidth = width - getPaddingLeft() - getPaddingRight();
        int contentHeight = height - getPaddingTop() - getPaddingBottom();

        float fullHeight = kanaHeight + romanjiHeight;

        kanaXPoint = getPaddingLeft() + ((contentWidth - kanaWidth) / 2);
        kanaYPoint = getPaddingTop() + (((contentHeight - fullHeight) / 2) - kanaPaint.getFontMetrics().ascent);
        romanjiXPoint = getPaddingLeft() + ((contentWidth - romanjiWidth) / 2);
        romanjiYPoint = getPaddingTop() + (((contentHeight + fullHeight) / 2) - romanjiPaint.getFontMetrics().descent);
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

        canvas.drawText(kana, kanaXPoint, kanaYPoint, kanaPaint);
        canvas.drawText(romanji, romanjiXPoint, romanjiYPoint, romanjiPaint);
    }

    public String getKana()
    {
        return kana;
    }

    public float getKanaSize()
    {
        return kanaPaint.getTextSize();
    }

    public String getRomanji()
    {
        return romanji;
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
        this.kana = (kana == null) ? "" : kana;
        kanaWidth = kanaPaint.measureText(this.kana);
    }

    public void setKanaSize(float kanaSize)
    {
        kanaPaint.setTextSize(kanaSize);
        kanaHeight = kanaPaint.getFontMetrics().descent - kanaPaint.getFontMetrics().ascent;
        kanaWidth = kanaPaint.measureText(kana);
    }

    public void setRomanji(String romanji)
    {
        this.romanji = (romanji == null) ? "" : romanji;
        romanjiWidth = romanjiPaint.measureText(this.romanji);
    }

    public void setRomanjiSize(float romanjiSize)
    {
        romanjiPaint.setTextSize(romanjiSize);
        romanjiHeight = romanjiPaint.getFontMetrics().descent - romanjiPaint.getFontMetrics().ascent;
        romanjiWidth = romanjiPaint.measureText(romanji);
    }

    public void setColour(int colour)
    {
        kanaPaint.setColor(colour);
        romanjiPaint.setColor(colour);
    }

    public static TableRow buildSpecialRow(Context context, Question[] questions)
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
                    for (Question question : questions)
                        row.addView(question.generateReference(context));
            }
            return row;
        }
    }

    public static TableRow buildRow(Context context, Question[] questions)
    {
        if (questions == null)
            return null;
        else
        {
            TableRow row = new TableRow(context);

            for (Question question : questions)
                row.addView(question.generateReference(context));

            return row;
        }
    }

    static TextView buildHeader(Context context, int title)
    {
        TextView header = new TextView(context);
        header.setText(title);
        header.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setTextSize(COMPLEX_UNIT_SP, 14);
        header.setPadding(0,
                Math.round(TypedValue.applyDimension(COMPLEX_UNIT_SP, 28, context.getResources().getDisplayMetrics())),
                0,
                Math.round(TypedValue.applyDimension(COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics())));
        header.setTypeface(header.getTypeface(), Typeface.BOLD);
        header.setAllCaps(true);
        return header;
    }
}
