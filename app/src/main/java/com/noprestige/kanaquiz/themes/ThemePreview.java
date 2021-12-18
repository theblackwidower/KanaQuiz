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

package com.noprestige.kanaquiz.themes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.noprestige.kanaquiz.R;

class ThemePreview extends View
{
    private String prefId;
    private int themeResId;

    private final Paint painter = new Paint();

    private int topColour;
    private int accentColour;
    private int backColour;
    private int textColour;

    int xPointBorderLeft;
    int xPointBorderRight;
    int yPointBorderTop;
    int yPointBorderBottom;

    float xPointLeft;
    float xPointRight;
    float yPointTop;
    float yPointBottom;

    float yPointHeader;

    float yPointText1;
    float yPointText2;

    float xPointText1;
    float xPointText2;

    float lineWidth;

    float xPointCircle;
    float yPointCircle;
    float circleRadius;


    public ThemePreview(Context context)
    {
        super(context);
    }

    public ThemePreview(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ThemePreview(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        int contentWidth = width - getPaddingLeft() - getPaddingRight();
        int contentHeight = height - getPaddingTop() - getPaddingBottom();

        xPointBorderLeft = getPaddingLeft();
        xPointBorderRight = width - getPaddingRight();
        yPointBorderTop = getPaddingTop();
        yPointBorderBottom = height - getPaddingBottom();

        float borderWidth = getResources().getDimension(R.dimen.dividingLine);

        xPointLeft = xPointBorderLeft + borderWidth;
        xPointRight = xPointBorderRight - borderWidth;
        yPointTop = yPointBorderTop + borderWidth;
        yPointBottom = yPointBorderBottom - borderWidth;

        yPointHeader = yPointTop + (contentHeight / 4f);
        yPointText1 = yPointHeader + ((contentHeight * 2f) / 8f);
        yPointText2 = yPointHeader + ((contentHeight * 3f) / 8f);

        xPointText1 = xPointLeft + (contentWidth / 8f);
        xPointText2 = xPointText1 + ((contentWidth * 5f) / 8f);

        lineWidth = contentHeight / 16f;

        xPointCircle = xPointRight - (Math.min(contentHeight, contentWidth) / 4f);
        yPointCircle = yPointBottom - (Math.min(contentHeight, contentWidth) / 4f);
        circleRadius = Math.min(contentHeight, contentWidth) / 8f;
    }

    @Override
    public void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
        painter.setStrokeWidth(0);

        painter.setColor(ThemeManager.getThemeColour(getContext(), android.R.attr.textColorPrimary));
        canvas.drawRect(xPointBorderLeft, yPointBorderTop, xPointBorderRight, yPointBorderBottom, painter);

        painter.setColor(topColour);
        canvas.drawRect(xPointLeft, yPointTop, xPointRight, yPointHeader, painter);

        painter.setColor(backColour);
        canvas.drawRect(xPointLeft, yPointHeader, xPointRight, yPointBottom, painter);

        painter.setColor(textColour);
        painter.setStrokeWidth(lineWidth);
        canvas.drawLine(xPointText1, yPointText1, xPointText2, yPointText1, painter);
        canvas.drawLine(xPointText1, yPointText2, xPointText2, yPointText2, painter);
        painter.setStrokeWidth(0);

        painter.setColor(accentColour);
        canvas.drawCircle(xPointCircle, yPointCircle, circleRadius, painter);
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

            topColour = ThemeManager.getThemeColour(getTheme(), R.attr.colorPrimary);
            accentColour = ThemeManager.getThemeColour(getTheme(), R.attr.colorAccent);
            backColour = ThemeManager.getThemeColour(getTheme(), android.R.attr.colorBackground);
            textColour = ThemeManager.getThemeColour(getTheme(), android.R.attr.textColorTertiary);
        }
    }
}
