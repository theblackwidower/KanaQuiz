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
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noprestige.kanaquiz.Fraction;
import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.QuestionBank;

import java.text.DecimalFormat;

public class AnswerFrame extends LinearLayout
{
    private EditText txtAnswer;
    private TextView lblScore;
    private MultipleChoicePad btnMultipleChoice;

    private static final DecimalFormat PERCENT_FORMATTER = new DecimalFormat("#0.0%");

    private OnAnswerListener answerListener;

    public AnswerFrame(Context context)
    {
        super(context);
        init();
    }

    public AnswerFrame(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public AnswerFrame(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        Context context = getContext();

        // Set up initial objects
        LayoutInflater.from(context).inflate(R.layout.answer_frame, this);

        txtAnswer = findViewById(R.id.txtAnswer);
        btnMultipleChoice = findViewById(R.id.btnMultipleChoice);
        lblScore = findViewById(R.id.lblScore);

        ((ViewGroup) getChildAt(0)).removeAllViews();
        removeAllViews();

        txtAnswer.setOnEditorActionListener((v, actionId, event) ->
        {
            String answer = v.getText().toString().trim();
            if ((actionId == EditorInfo.IME_ACTION_GO) || (actionId == EditorInfo.IME_NULL))
                checkAnswer(answer);
            return true;
        });

        btnMultipleChoice.setOnAnswerListener(this::checkAnswer);
    }

    @FunctionalInterface
    public interface OnAnswerListener
    {
        void onAnswer(String answer);
    }

    public void setOnAnswerListener(OnAnswerListener listener)
    {
        answerListener = listener;
    }

    private void checkAnswer(String answer)
    {
        if (answerListener != null)
            answerListener.onAnswer(answer);
    }

    public void setTextHint(int refId)
    {
        txtAnswer.setHint(refId);
    }

    public void resetQuiz()
    {
        removeAllViews();
        txtAnswer.setEnabled(true);

        if (OptionsControl.getBoolean(R.string.prefid_multiple_choice))
        {
            addView(lblScore);
            addView(btnMultipleChoice);
        }
        else
        {
            addView(txtAnswer);
            addView(lblScore);
        }
    }

    public void setMultipleChoices(QuestionBank questionBank)
    {
        if (OptionsControl.getBoolean(R.string.prefid_multiple_choice))
        {
            //ref: https://www.codespeedy.com/multithreading-in-java/
            new Thread(() ->
            {
                String[] answers = questionBank.getPossibleAnswers();
                new Handler(getContext().getMainLooper()).post(() -> btnMultipleChoice.setChoices(answers));
            }).start();
        }
    }

    public void onNoQuestions()
    {
        btnMultipleChoice.deleteChoices();
        txtAnswer.setEnabled(false);
        txtAnswer.setText("");
    }

    public void enableButtons()
    {
        btnMultipleChoice.enableButtons();
    }

    public void readyForTextAnswer()
    {
        txtAnswer.requestFocus();
        txtAnswer.setText("");
    }

    public void updateScore(Fraction totalCorrect, int totalQuestions)
    {
        if (totalQuestions > 0)
        {
            lblScore.setText(R.string.score_label);
            lblScore.append(": ");

            lblScore.append(PERCENT_FORMATTER.format(totalCorrect.getDecimal() / (float) totalQuestions));

            lblScore.append(System.getProperty("line.separator"));
            lblScore.append(totalCorrect.toString());
            lblScore.append(" / ");
            lblScore.append(Integer.toString(totalQuestions));
        }
        else
            lblScore.setText("");
    }
}
