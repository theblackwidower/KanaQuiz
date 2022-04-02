/*
 *    Copyright 2022 T Duke Perry
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

package com.noprestige.kanaquiz.reference;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class AutoSizedTextView extends AppCompatTextView
{
    public AutoSizedTextView(Context context)
    {
        super(context);
    }

    public AutoSizedTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AutoSizedTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int desiredWidth;
        int desiredHeight;
        int width;
        int height;

        TextPaint paint = getPaint();
        float textSize = paint.getTextSize();

        do
        {
            paint.setTextSize(textSize);

            desiredWidth = (int) (paint.measureText(getText().toString()) + getPaddingLeft() + getPaddingRight());
            desiredHeight = (int) ((paint.getFontMetrics().descent - getPaint().getFontMetrics().ascent) + getPaddingTop() + getPaddingBottom());

            width = calculateSize(widthMeasureSpec, desiredWidth);
            height = calculateSize(heightMeasureSpec, desiredHeight);

            textSize -= 0.5;
        }
        while ((desiredWidth > width) || (desiredHeight > height));

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
}
