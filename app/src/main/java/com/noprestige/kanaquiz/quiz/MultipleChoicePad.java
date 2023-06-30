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

package com.noprestige.kanaquiz.quiz;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
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
    private AnswerFrame.OnAnswerListener answerListener;

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

    public void setOnAnswerListener(AnswerFrame.OnAnswerListener listener)
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

        //ref: https://www.codespeedy.com/multithreading-in-java/
        new Thread(new NormalizeSizeTask(this)).start();
    }

    static class NormalizeSizeTask implements Runnable
    {
        private final MultipleChoicePad thisPad;

        private int maxWidth;
        private int maxHeight;

        NormalizeSizeTask(MultipleChoicePad pad)
        {
            thisPad = pad;
        }

        @Override
        public void run()
        {
            try
            {
                do
                {
                    Thread.sleep(100);
                    for (Button btnChoice : thisPad.btnChoices)
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
            catch (InterruptedException ignored) {}
            //ref: https://stackoverflow.com/a/11125271
            new Handler(thisPad.getContext().getMainLooper()).post(this::done);
        }

        private void done()
        {
            if (maxWidth > 0)
                for (Button btnChoice : thisPad.btnChoices)
                    btnChoice.setWidth(maxWidth);

            if (maxHeight > 0)
                for (Button btnChoice : thisPad.btnChoices)
                    btnChoice.setHeight(maxHeight);

            thisPad.setVisibility(View.VISIBLE);
        }
    }
}
