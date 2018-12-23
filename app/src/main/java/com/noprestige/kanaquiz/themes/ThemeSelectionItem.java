package com.noprestige.kanaquiz.themes;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noprestige.kanaquiz.R;

import java.util.Locale;

import static android.graphics.Typeface.NORMAL;

public class ThemeSelectionItem extends LinearLayout
{
    private String prefId;
    private String themeName;
    private int themeResId;

    private TextView lblThemeName;
    private TextView lblThemeSample;
    private ThemePreview imgPreview;

    private Paint linePaint = new Paint();

    private boolean isSelected;

    private float lineXPoint1;
    private float lineXPoint2;
    private float lineYPoint;

    public ThemeSelectionItem(Context context)
    {
        super(context);
        init(null, 0);
    }

    public ThemeSelectionItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public ThemeSelectionItem(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        Context context = getContext();

        // Set up initial objects
        LayoutInflater.from(context).inflate(R.layout.theme_selection_item, this);

        lblThemeName = findViewById(R.id.lblThemeName);
        lblThemeSample = findViewById(R.id.lblThemeSample);
        imgPreview = findViewById(R.id.imgPreview);

        setOnClickListener(view -> select());

        // Load attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ThemeSelectionItem, defStyle, 0);

        setThemeName(a.getString(R.styleable.ThemeSelectionItem_themeName));
        setPrefId(a.getString(R.styleable.ThemeSelectionItem_themePrefId));

        a.recycle();

        lblThemeSample.setTextLocale(Locale.JAPANESE);

        linePaint.setColor(ThemeManager.getThemeColour(context, android.R.attr.textColorPrimary));
        linePaint.setStrokeWidth(context.getResources().getDimension(R.dimen.dividingLine));
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        lineXPoint1 = getPaddingLeft();
        lineXPoint2 = width - getPaddingRight();
        lineYPoint = height - getPaddingBottom();
    }

    @Override
    public void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);

        canvas.drawLine(lineXPoint1, lineYPoint, lineXPoint2, lineYPoint, linePaint);
        if (equals(((ViewGroup) getParent()).getChildAt(0)))
            canvas.drawLine(lineXPoint1, 0, lineXPoint2, 0, linePaint);
    }

    public String getThemeName()
    {
        return themeName;
    }

    public String getPrefId()
    {
        return prefId;
    }

    public Resources.Theme getTheme()
    {
        Resources.Theme theme = getResources().newTheme();
        theme.applyStyle(themeResId, true);
        return theme;
    }

    public void setThemeName(int resId)
    {
        setThemeName(getResources().getString(resId));
    }

    public void setThemeName(CharSequence themeName)
    {
        if (themeName != null)
        {
            this.themeName = themeName.toString();
            lblThemeName.setText(themeName);
        }
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
            themeResId = ThemeManager.getThemeId(prefId);
            imgPreview.setPrefId(prefId);
            lblThemeSample.setTypeface(ThemeManager.getThemeFont(getTheme(), R.attr.fontFamily, NORMAL));
        }
    }

    public void select()
    {
        if (!isSelected)
        {
            isSelected = true;
            setBackgroundColor(ThemeManager.getThemeColour(getContext(), R.attr.colorPrimary));

            ViewGroup parent = (ViewGroup) getParent();
            for (int i = 0; i < parent.getChildCount(); i++)
            {
                View item = parent.getChildAt(i);
                if ((!item.equals(this)) && item.getClass().equals(ThemeSelectionItem.class))
                    ((ThemeSelectionItem) item).deselect();
            }
        }
    }

    public void deselect()
    {
        if (isSelected)
        {
            isSelected = false;
            setBackgroundColor(ThemeManager.getThemeColour(getContext(), R.attr.background));
        }
    }

    @Override
    public boolean isSelected()
    {
        return isSelected;
    }
}
