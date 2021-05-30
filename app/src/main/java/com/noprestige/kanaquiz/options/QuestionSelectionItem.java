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
import com.noprestige.kanaquiz.questions.Question;
import com.noprestige.kanaquiz.questions.WordQuestion;
import com.noprestige.kanaquiz.themes.ThemeManager;

import java.util.Locale;

import static com.noprestige.kanaquiz.questions.QuestionManagement.SUBPREFERENCE_DELIMITER;

public class QuestionSelectionItem extends LinearLayout implements Checkable
{
    private String prefId;
    private String title;
    private String contents;
    private Question[] questions;

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
        setOnLongClickListener(view -> detailView());

        chkCheckBox.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            OptionsControl.setQuestionSetBool(getPrefId(), isChecked);
            buildTextBox();
        });

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

    private boolean detailView()
    {
        if (questions != null)
        {
            Context context = getContext();
            if (context instanceof QuestionSelection)
            {
                QuestionSelectionDetail box = QuestionSelectionDetail.newInstance(prefId, questions);
                box.recordParentCheckbox(this);
                box.show(((QuestionSelection) context).getSupportFragmentManager(), "detailView");
                return true;
            }
        }
        return false;
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

    public void setQuestions(Question[] questions)
    {
        this.questions = questions;
        buildTextBox();
    }

    public static void setHighlightColour(Context context)
    {
        StringBuilder colourString = new StringBuilder(
                Integer.toHexString(ThemeManager.getThemeColour(context, R.attr.colorAccent) & 0xffffff));
        while (colourString.length() < 6)
            colourString.insert(0, '0');

        highlightColour = colourString.toString();
    }

    static final String HIGHLIGHT_HTML = "<font color=\"#%1$s\">%2$s</font>";
    static String highlightColour;

    public void buildTextBox()
    {
        if ((title != null) && (contents != null) && (questions != null))
        {
            String highlightedContents = contents;
            if (OptionsControl.exists(prefId))
            {
                if (OptionsControl.getBoolean(prefId))
                    highlightedContents = String.format(HIGHLIGHT_HTML, highlightColour, contents);
            }
            else
                for (Question question : questions)
                    if (OptionsControl.getBoolean(prefId + SUBPREFERENCE_DELIMITER + question.getDatabaseKey()))
                    {
                        String questionText;
                        if (WordQuestion.class.equals(question.getClass()))
                            questionText = question.fetchCorrectAnswer().replace(' ', '\u00A0');
                        else
                            questionText = question.getQuestionText();

                        highlightedContents = highlightedContents.replace('\u0000' + questionText + '\u0000',
                                String.format(HIGHLIGHT_HTML, highlightColour, questionText));
                    }
            highlightedContents = highlightedContents.replace("</font>,", ",</font>");

            lblText.setText(Html.fromHtml("<b>" + title + "</b><br/>" + highlightedContents));
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

            if (!isInEditMode())
                if (OptionsControl.exists(getPrefId()))
                    setChecked(OptionsControl.getBoolean(getPrefId()));
                else
                    nullify();
        }
    }

    public void nullify()
    {
        chkCheckBox.setVisibility(INVISIBLE);
    }

    public boolean isNeutral()
    {
        return (chkCheckBox.getVisibility() == INVISIBLE);
    }

    @Override
    public boolean isChecked()
    {
        return (chkCheckBox.getVisibility() == VISIBLE) && chkCheckBox.isChecked();
    }

    @Override
    public void setChecked(boolean checked)
    {
        chkCheckBox.setVisibility(VISIBLE);
        chkCheckBox.setChecked(checked);
    }

    @Override
    public void toggle()
    {
        if (chkCheckBox.getVisibility() == VISIBLE)
            chkCheckBox.toggle();
        else
        {
            chkCheckBox.setVisibility(VISIBLE);
            chkCheckBox.setChecked(true);
        }
    }
}
