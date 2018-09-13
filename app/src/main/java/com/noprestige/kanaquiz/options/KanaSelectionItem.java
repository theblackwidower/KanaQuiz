package com.noprestige.kanaquiz.options;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.noprestige.kanaquiz.R;

public class KanaSelectionItem extends RelativeLayout implements Checkable
{
    private String prefId;

    private TextView lblTitle;
    private TextView lblContents;
    private CheckBox chkCheckBox;

    private Paint linePaint = new Paint();

    private float lineXpoint_1;
    private float lineXpoint_2;
    private float lineYpoint;

    public KanaSelectionItem(Context context)
    {
        super(context);
        init(null, 0);
    }

    public KanaSelectionItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public KanaSelectionItem(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        Context context = this.getContext();

        // Set up initial objects
        LayoutInflater.from(context).inflate(R.layout.kana_selection_item, this);

        lblTitle = findViewById(R.id.lblTitle);
        lblContents = findViewById(R.id.lblContents);
        chkCheckBox = findViewById(R.id.chkCheckBox);

        this.setOnClickListener(view -> toggle());

        chkCheckBox.setOnCheckedChangeListener(
                (buttonView, isChecked) -> OptionsControl.setBoolean(getPrefId(), isChecked));

        // Load attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KanaSelectionItem, defStyle, 0);

        setTitle(a.getString(R.styleable.KanaSelectionItem_title));
        setContents(a.getString(R.styleable.KanaSelectionItem_contents));
        setPrefId(a.getString(R.styleable.KanaSelectionItem_prefId));

        a.recycle();

        linePaint.setColor(context.getResources().getColor(R.color.dividingLine));
        linePaint.setStrokeWidth(context.getResources().getDimension(R.dimen.dividingLine));
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        lineXpoint_1 = getPaddingLeft();
        lineXpoint_2 = width - getPaddingRight();
        lineYpoint = height - getPaddingBottom() - linePaint.getStrokeWidth();
    }

    //ref: http://stackoverflow.com/questions/13273838/onmeasure-wrap-content-how-do-i-know-the-size-to-wrap
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        //Need to position objects within the layout
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildWithMargins(lblTitle, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(lblContents, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(chkCheckBox, widthMeasureSpec, 0, heightMeasureSpec, 0);

        LayoutParams titleLayout = (LayoutParams) lblTitle.getLayoutParams();
        LayoutParams contentsLayout = (LayoutParams) lblContents.getLayoutParams();
        LayoutParams boxLayout = (LayoutParams) chkCheckBox.getLayoutParams();

        int desiredWidth = Math.max(lblTitle.getMeasuredWidth() + titleLayout.leftMargin + titleLayout.rightMargin,
                lblContents.getMeasuredWidth() + contentsLayout.leftMargin + contentsLayout.rightMargin) +
                chkCheckBox.getMeasuredWidth() + boxLayout.leftMargin + boxLayout.rightMargin + getPaddingLeft() +
                getPaddingRight();
        int desiredHeight = Math.max(lblTitle.getMeasuredHeight() + titleLayout.topMargin + titleLayout.bottomMargin +
                        lblContents.getMeasuredHeight() + contentsLayout.topMargin + contentsLayout.bottomMargin,
                chkCheckBox.getMeasuredHeight() + boxLayout.topMargin + boxLayout.bottomMargin) + getPaddingTop() +
                getPaddingBottom() + Math.round(linePaint.getStrokeWidth());

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
    public void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);

        canvas.drawLine(lineXpoint_1, lineYpoint, lineXpoint_2, lineYpoint, linePaint);
    }

    public String getTitle()
    {
        return (String) lblTitle.getText();
    }

    public String getContents()
    {
        return (String) lblContents.getText();
    }

    public String getPrefId()
    {
        return prefId;
    }

    public void setTitle(int resId)
    {
        setTitle(getResources().getString(resId));
    }

    public void setTitle(CharSequence title)
    {
        if (title != null)
            lblTitle.setText(title);
    }

    public void setContents(int resId)
    {
        setContents(getResources().getString(resId));
    }

    public void setContents(CharSequence contents)
    {
        if (contents != null)
            lblContents.setText(contents);
    }

    public void setPrefId(int resId)
    {
        setPrefId(getResources().getString(resId));
    }

    public void setPrefId(String prefId)
    {
        if (prefId != null)
        {
            this.prefId = prefId;

            if (!isInEditMode())
                setChecked(OptionsControl.getBoolean(getPrefId()));
        }
    }

    @Override
    public boolean isChecked()
    {
        return chkCheckBox.isChecked();
    }

    @Override
    public void setChecked(boolean checked)
    {
        chkCheckBox.setChecked(checked);
    }

    @Override
    public void toggle()
    {
        chkCheckBox.toggle();
    }
}
