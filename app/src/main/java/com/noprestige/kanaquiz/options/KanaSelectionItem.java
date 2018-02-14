package com.noprestige.kanaquiz.options;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noprestige.kanaquiz.R;

public class KanaSelectionItem extends LinearLayout
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

        this.setOnClickListener(
                new OnClickListener()
                {
                    public void onClick(View view)
                    {
                        chkCheckBox.toggle();
                    }
                }
        );

        chkCheckBox.setOnCheckedChangeListener(
                new OnCheckedChangeListener()
                {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        OptionsControl.setBoolean(getPrefId(), isChecked);
                    }
                }
        );

        // Load attributes
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KanaSelectionItem, defStyle, 0);

        String title = a.getString(R.styleable.KanaSelectionItem_title);
        String contents = a.getString(R.styleable.KanaSelectionItem_contents);
        String prefId = a.getString(R.styleable.KanaSelectionItem_prefId);

        if (title != null)
            setTitle(title);
        if (contents != null)
            setContents(contents);
        if (prefId != null)
            setPrefId(prefId);

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
        int desiredWidth = 0;
        int desiredHeight = 0;

        for (int i = 0; i < getChildCount(); i++)
        {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            desiredWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            desiredHeight = Math.max(desiredHeight, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
        }
        desiredWidth += getPaddingLeft() + getPaddingRight();
        desiredHeight += getPaddingTop() + getPaddingBottom() + linePaint.getStrokeWidth();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = measure(widthMode, widthSize, desiredWidth);
        int height = measure(heightMode, heightSize, desiredHeight);

        setMeasuredDimension(width, height);
    }

    static private int measure(int mode, int size, int desired)
    {
        if (mode == MeasureSpec.EXACTLY)
            return size;
        else if (mode == MeasureSpec.AT_MOST)
            return Math.min(desired, size);
        else
            return desired;
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

    public void setTitle(String title)
    {
        lblTitle.setText(title);
    }

    public void setContents(int resId)
    {
        setContents(getResources().getString(resId));
    }

    public void setContents(String contents)
    {
        lblContents.setText(contents);
    }

    public void setPrefId(int resId)
    {
        setPrefId(getResources().getString(resId));
    }

    public void setPrefId(String prefId)
    {
        this.prefId = prefId;

        if (!isInEditMode())
            chkCheckBox.setChecked(OptionsControl.getBoolean(getPrefId()));
    }
}
