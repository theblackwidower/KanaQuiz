package com.noprestige.kanaquiz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MultipleChoicePad extends LinearLayout
{
    private ArrayList<String> choices = new ArrayList<>();
    private ArrayList<Button> btnChoices = new ArrayList<>();

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
        ((MainQuiz) getContext()).checkAnswer(userAnswer);
    }

    public String[] getChoices()
    {
        return (String[]) choices.toArray();
    }

    public void deleteChoices()
    {
        choices = new ArrayList<>();
        for (Button button : btnChoices)
            this.removeView(button);
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
                        onAnswerSubmit(answer);
                    }
                }
        );
        btnNewButton.setText(answer);
        this.addView(btnNewButton);
        // TODO: Fix button layout
        btnChoices.add(btnNewButton);
    }

    public void setChoices(String[] answers)
    {
        deleteChoices();
        for (String answer : answers)
            addChoice(answer);
    }
}
