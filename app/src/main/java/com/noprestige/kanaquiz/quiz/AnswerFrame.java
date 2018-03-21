package com.noprestige.kanaquiz.quiz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noprestige.kanaquiz.Fraction;
import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.KanaQuestionBank;

import java.text.DecimalFormat;

public class AnswerFrame extends LinearLayout
{
    private EditText txtAnswer;
    private TextView lblScore;
    private MultipleChoicePad btnMultipleChoice;

    private static final DecimalFormat PERCENT_FORMATTER = new DecimalFormat("#0.0%");

    private OnAnswerListener answerListener = null;

    public AnswerFrame(Context context)
    {
        super(context);
        init(null, 0);
    }

    public AnswerFrame(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public AnswerFrame(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        Context context = this.getContext();

        // Set up initial objects
        LayoutInflater.from(context).inflate(R.layout.answer_frame, this);

        txtAnswer = findViewById(R.id.txtAnswer);
        btnMultipleChoice = findViewById(R.id.btnMultipleChoice);
        lblScore = findViewById(R.id.lblScore);

        ((ViewGroup) getChildAt(0)).removeAllViews();
        removeAllViews();

        txtAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                String answer = v.getText().toString().trim();
                if ((actionId == EditorInfo.IME_ACTION_GO) || (actionId == EditorInfo.IME_NULL))
                    checkAnswer(answer);
                return true;
            }
        });

        btnMultipleChoice.setOnAnswerListener(new OnAnswerListener()
        {
            @Override
            public void onAnswer(String answer)
            {
                checkAnswer(answer);
            }
        });
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

    public void setMultipleChoices(KanaQuestionBank questionBank)
    {
        if (OptionsControl.getBoolean(R.string.prefid_multiple_choice))
            btnMultipleChoice.setChoices(questionBank.getPossibleAnswers());
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

    public void updateScore(float totalCorrect, int totalQuestions)
    {
        if (totalQuestions > 0)
        {
            lblScore.setText(R.string.score_label);
            lblScore.append(": ");

            lblScore.append(PERCENT_FORMATTER.format(totalCorrect / (float) totalQuestions));

            lblScore.append(System.getProperty("line.separator"));
            lblScore.append(new Fraction(totalCorrect).toString());
            lblScore.append(" / ");
            lblScore.append(Integer.toString(totalQuestions));
        }
        else
            lblScore.setText("");
    }
}
