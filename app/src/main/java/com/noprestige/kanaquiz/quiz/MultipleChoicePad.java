package com.noprestige.kanaquiz.quiz;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.Collection;

public class MultipleChoicePad extends FlowLayout
{
    private Collection<Button> btnChoices = new ArrayList<>();
    private OnAnswerListener answerListener;

    public MultipleChoicePad(Context context)
    {
        super(context);
    }

    public MultipleChoicePad(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MultipleChoicePad(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public void setOnAnswerListener(OnAnswerListener listener)
    {
        answerListener = listener;
    }

    public void onAnswerSubmit(String userAnswer)
    {
        for (Button btnChoice : btnChoices)
            btnChoice.setEnabled(false);
        if (answerListener != null)
            answerListener.onAnswer(userAnswer);
    }

    public void enableButtons()
    {
        for (Button btnChoice : btnChoices)
            btnChoice.setEnabled(true);
    }

    public void deleteChoices()
    {
        this.removeAllViews();
        btnChoices = new ArrayList<>();
    }

    private void submitAnswer(View view, String answer)
    {
        //ref: https://stackoverflow.com/questions/1521640/standard-android-button-with-a-different-color
        view.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        onAnswerSubmit(answer);
        btnChoices.remove(view); //prevents selected button from being re-enabled
    }

    public void addChoice(String answer)
    {
        Button btnNewButton = new Button(this.getContext());
        btnNewButton.setOnClickListener((view) -> submitAnswer(view, answer));
        btnNewButton.setText(answer);
        this.addView(btnNewButton);
        btnChoices.add(btnNewButton);
    }

    public void setChoices(String[] answers)
    {
        deleteChoices();
        for (String answer : answers)
            addChoice(answer);
    }
}
