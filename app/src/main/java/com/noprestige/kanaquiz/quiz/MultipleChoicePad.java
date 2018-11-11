package com.noprestige.kanaquiz.quiz;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.Collection;

import static com.noprestige.kanaquiz.questions.KanjiQuestion.MEANING_DELIMITER;

public class MultipleChoicePad extends FlowLayout
{
    Collection<Button> btnChoices = new ArrayList<>();
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
        removeAllViews();
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
        Button btnNewButton = new Button(getContext());
        btnNewButton.setOnClickListener((view) -> submitAnswer(view, answer));
        btnNewButton.setText(answer.replace(" ", System.getProperty("line.separator"))
                .replace(MEANING_DELIMITER, MEANING_DELIMITER + System.getProperty("line.separator")));
        addView(btnNewButton);
        btnChoices.add(btnNewButton);
    }

    public void setChoices(String[] answers)
    {
        deleteChoices();
        setVisibility(View.INVISIBLE);
        for (String answer : answers)
            addChoice(answer);

        new NormalizeSizeTask().execute(this);
    }

    static class NormalizeSizeTask extends AsyncTask<MultipleChoicePad, Void, MultipleChoicePad>
    {
        private int maxWidth;
        private int maxHeight;

        @Override
        protected MultipleChoicePad doInBackground(MultipleChoicePad... item)
        {
            try
            {
                do
                {
                    Thread.sleep(100);
                    for (Button btnChoice : item[0].btnChoices)
                    {
                        int thisWidth = btnChoice.getWidth();
                        if (thisWidth > maxWidth)
                            maxWidth = thisWidth;

                        int thisHeight = btnChoice.getHeight();
                        if (thisHeight > maxHeight)
                            maxHeight = thisHeight;
                    }
                }
                while ((maxWidth == 0) || (maxHeight == 0));
            }
            catch (InterruptedException ignored)
            {
                cancel(true);
            }
            return item[0];
        }

        @Override
        protected void onPostExecute(MultipleChoicePad item)
        {
            if (maxWidth > 0)
                for (Button btnChoice : item.btnChoices)
                    btnChoice.setWidth(maxWidth);

            if (maxHeight > 0)
                for (Button btnChoice : item.btnChoices)
                    btnChoice.setHeight(maxHeight);

            item.setVisibility(View.VISIBLE);
        }
    }
}
