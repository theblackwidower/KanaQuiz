/*
 *    Copyright 2018 T Duke Perry
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

package com.noprestige.kanaquiz.options;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.themes.ThemeManager;

import java.util.Locale;

public class QuestionSelectionItem extends LinearLayout implements Checkable
{
    private String prefId;
    private String title;
    private String contents;

    private TextView lblText;
    private CheckBox chkCheckBox;

    private Paint linePaint = new Paint();

    private float lineXPoint1;
    private float lineXPoint2;
    private float lineYPoint;

    public QuestionSelectionItem(Context context)
    {
        super(context);
        init(null, 0);
    }

    public QuestionSelectionItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public QuestionSelectionItem(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        Context context = getContext();

        // Set up initial objects
        LayoutInflater.from(context).inflate(R.layout.question_selection_item, this);

        lblText = findViewById(R.id.lblText);
        chkCheckBox = findViewById(R.id.chkCheckBox);

        setOnClickListener(view -> toggle());

        chkCheckBox.setOnCheckedChangeListener(
                (buttonView, isChecked) -> OptionsControl.setBoolean(getPrefId(), isChecked));

        lblText.setTextLocale(Locale.JAPANESE);

        // Load attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QuestionSelectionItem, defStyle, 0);

        setTitle(a.getString(R.styleable.QuestionSelectionItem_title));
        setContents(a.getString(R.styleable.QuestionSelectionItem_contents));
        setPrefId(a.getString(R.styleable.QuestionSelectionItem_prefId));

        a.recycle();

        linePaint.setColor(ThemeManager.getThemeColour(context, android.R.attr.textColorPrimary));
        linePaint.setStrokeWidth(context.getResources().getDimension(R.dimen.dividingLine));
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        lineXPoint1 = getPaddingLeft();
        lineXPoint2 = width - getPaddingRight();
        lineYPoint = height - getPaddingBottom() - linePaint.getStrokeWidth();
    }

    @Override
    public void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);

        canvas.drawLine(lineXPoint1, lineYPoint, lineXPoint2, lineYPoint, linePaint);
    }

    public String getTitle()
    {
        return title;
    }

    public String getContents()
    {
        return contents;
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
        {
            this.title = title.toString();
            buildTextBox();
        }
    }

    public void setContents(int resId)
    {
        setContents(getResources().getString(resId));
    }

    public void setContents(CharSequence contents)
    {
        if (contents != null)
        {
            this.contents = contents.toString();
            buildTextBox();
        }
    }

    private void buildTextBox()
    {
        if ((title != null) && (contents != null))
            lblText.setText(Html.fromHtml("<b>" + title + "</b><br/>" + contents));
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
