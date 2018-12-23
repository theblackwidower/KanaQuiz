package com.noprestige.kanaquiz.reference;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.questions.Question;
import com.noprestige.kanaquiz.themes.ThemeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.noprestige.kanaquiz.questions.KanjiQuestion.MEANING_DELIMITER;

public class ReferenceCell extends View
{
    private String subject;
    private String description;
    private String[] multiLineDescription;

    private TextPaint subjectPaint = new TextPaint();
    private TextPaint descriptionPaint = new TextPaint();

    private float subjectXPoint;
    private float subjectYPoint;
    private float descriptionXPoint;
    private float descriptionYPoint;

    private float subjectWidth;
    private float descriptionWidth;

    private float subjectHeight;
    private float descriptionHeight;

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

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ReferenceCell, defStyle, 0);

        setSubject(a.getString(R.styleable.ReferenceCell_subject));
        setDescription(a.getString(R.styleable.ReferenceCell_description));
        setSubjectSize(a.getDimension(R.styleable.ReferenceCell_subjectSize,
                getResources().getDimension(R.dimen.defaultReferenceSubjectSize)));
        setDescriptionSize(a.getDimension(R.styleable.ReferenceCell_descriptionSize,
                getResources().getDimension(R.dimen.defaultReferenceDescriptionSize)));
        setColour(a.getColor(R.styleable.ReferenceCell_colour,
                ThemeManager.getThemeColour(context, android.R.attr.textColorTertiary)));

        a.recycle();

        setOnLongClickListener(view -> copyItem());

        subjectPaint.setAntiAlias(true);
        descriptionPaint.setAntiAlias(true);

        subjectPaint.setTextLocale(Locale.JAPANESE);

        Typeface font = ThemeManager.getDefaultThemeFont(context, Typeface.NORMAL);

        subjectPaint.setTypeface(font);
        descriptionPaint.setTypeface(font);
    }

    private boolean copyItem()
    {
        //TODO: Allow the ability to select and copy multiple items at once.
        String data = subject + " - " + description;
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

        float fullHeight = subjectHeight + descriptionHeight;

        subjectXPoint = getPaddingLeft() + ((contentWidth - subjectWidth) / 2);
        subjectYPoint = getPaddingTop() + (((contentHeight - fullHeight) / 2) - subjectPaint.getFontMetrics().ascent);
        descriptionXPoint = getPaddingLeft() + ((contentWidth - descriptionWidth) / 2);
        descriptionYPoint =
                getPaddingTop() + (((contentHeight + fullHeight) / 2) - descriptionPaint.getFontMetrics().descent);
    }

    //ref: http://stackoverflow.com/questions/13273838/onmeasure-wrap-content-how-do-i-know-the-size-to-wrap
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int desiredWidth = Math.round(Math.max(subjectWidth, descriptionWidth)) + getPaddingLeft() + getPaddingRight();
        int desiredHeight = Math.round(subjectHeight + descriptionHeight) + getPaddingTop() + getPaddingBottom();

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

        canvas.drawText(subject, subjectXPoint, subjectYPoint, subjectPaint);
        if ((multiLineDescription != null) && (multiLineDescription.length > 1))
        {
            float descriptionLineHeight = descriptionHeight / multiLineDescription.length;
            int contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            for (int i = 0; i < multiLineDescription.length; i++)
            {
                canvas.drawText(multiLineDescription[i],
                        getPaddingLeft() + ((contentWidth - descriptionPaint.measureText(multiLineDescription[i])) / 2),
                        descriptionYPoint - ((multiLineDescription.length - i - 1) * descriptionLineHeight),
                        descriptionPaint);
            }
        }
        else
            canvas.drawText(description, descriptionXPoint, descriptionYPoint, descriptionPaint);
    }

    public String getSubject()
    {
        return subject;
    }

    public float getSubjectSize()
    {
        return subjectPaint.getTextSize();
    }

    public String getDescription()
    {
        return description;
    }

    public float getDescriptionSize()
    {
        return descriptionPaint.getTextSize();
    }

    public int getColour()
    {
        return subjectPaint.getColor();
        //return descriptionPaint.getColor();
    }

    public void setSubject(String subject)
    {
        this.subject = (subject == null) ? "" : subject;
        subjectWidth = subjectPaint.measureText(this.subject);
    }

    public void setSubjectSize(float subjectSize)
    {
        subjectPaint.setTextSize(subjectSize);
        subjectHeight = subjectPaint.getFontMetrics().descent - subjectPaint.getFontMetrics().ascent;
        subjectWidth = subjectPaint.measureText(subject);
    }

    public void setDescription(String description)
    {
        this.description = (description == null) ? "" : description;
        measureDescription();
    }

    public void setDescriptionSize(float descriptionSize)
    {
        descriptionPaint.setTextSize(descriptionSize);
        measureDescription();
    }

    private void measureDescription()
    {
        descriptionHeight = descriptionPaint.getFontMetrics().descent - descriptionPaint.getFontMetrics().ascent;
        if (description.contains(MEANING_DELIMITER) || description.contains(" "))
        {
            multiLineDescription = description.replace(MEANING_DELIMITER, MEANING_DELIMITER + "\u0000").split("\u0000");
            descriptionWidth = 0;
            List<String> tempMultiLine = new ArrayList<>();
            for (String part : multiLineDescription)
            {
                float partWidth = descriptionPaint.measureText(part);
                if ((partWidth > (subjectWidth * 1.1f)) && part.contains(" "))
                {
                    String[] subParts = part.split(" ");

                    for (String subPart : subParts)
                    {
                        float subPartWidth = descriptionPaint.measureText(subPart);
                        if (subPartWidth > descriptionWidth)
                            descriptionWidth = subPartWidth;
                        tempMultiLine.add(subPart);
                    }
                }
                else if (partWidth > descriptionWidth)
                {
                    descriptionWidth = partWidth;
                    tempMultiLine.add(part);
                }
                else
                    tempMultiLine.add(part);
            }
            if (tempMultiLine.size() != multiLineDescription.length)
                multiLineDescription = tempMultiLine.toArray(new String[0]);
            descriptionHeight *= multiLineDescription.length;
        }
        else
        {
            multiLineDescription = null;
            descriptionWidth = descriptionPaint.measureText(description);
        }
    }

    public void setColour(int colour)
    {
        subjectPaint.setColor(colour);
        descriptionPaint.setColor(colour);
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

    public static TextView buildHeader(Context context, CharSequence title)
    {
        TextView header = new TextView(context);
        header.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        header.setText(title);
        header.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.headerTextSize));
        header.setPadding(0, context.getResources().getDimensionPixelSize(R.dimen.headerTopPaddingReference), 0,
                context.getResources().getDimensionPixelSize(R.dimen.headerBottomPadding));
        header.setTypeface(header.getTypeface(), Typeface.BOLD);
        header.setAllCaps(true);
        return header;
    }
}
