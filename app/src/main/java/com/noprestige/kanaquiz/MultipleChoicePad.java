package com.noprestige.kanaquiz;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static android.view.Gravity.CENTER_HORIZONTAL;

public class MultipleChoicePad extends LinearLayout
{
    private ArrayList<Button> btnChoices = new ArrayList<>();

    private LinearLayout lastRow = null;
    private static final int MAX_COLUMNS = 3;

    public MultipleChoicePad(Context context)
    {
        super(context);
        init(null, 0);
    }

    public MultipleChoicePad(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public MultipleChoicePad(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
    }

    public void onAnswerSubmit(String userAnswer)
    {
        for (Button btnChoice : btnChoices)
            btnChoice.setEnabled(false);
        ((MainQuiz) getContext()).checkAnswer(userAnswer);
    }

    public void enableButtons()
    {
        for (Button btnChoice : btnChoices)
            btnChoice.setEnabled(true);
    }

    public void deleteChoices()
    {
        lastRow = null;
        this.removeAllViews();
        btnChoices = new ArrayList<>();
    }

    public void addChoice(final String answer)
    {
        Button btnNewButton = new Button(this.getContext());
        btnNewButton.setOnClickListener(
                new OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //ref: https://stackoverflow.com/questions/1521640/standard-android-button-with-a-different-color
                        view.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                        onAnswerSubmit(answer);
                        btnChoices.remove(view); //prevents selected button from being re-enabled
                    }
                }
        );
        btnNewButton.setText(answer);
        if (lastRow == null || lastRow.getChildCount() >= MAX_COLUMNS)
        {
            lastRow = new LinearLayout(getContext());
            lastRow.setOrientation(HORIZONTAL);
            lastRow.setGravity(CENTER_HORIZONTAL);
            this.addView(lastRow);
        }
        lastRow.addView(btnNewButton);
        btnChoices.add(btnNewButton);
    }

    public void setChoices(String[] answers)
    {
        deleteChoices();
        for (String answer : answers)
            addChoice(answer);
    }
}
