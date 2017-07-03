package com.noprestige.kanaquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainQuiz extends AppCompatActivity
{
    private int totalQuestions;
    private int totalCorrect;

    private KanaQuestionBank questionBank;

    private TextView lblResponse;
    private EditText txtAnswer;
    private TextView lblDisplayKana;
    private Button btnSubmit;

    private int oldTextColour;
    private static final DecimalFormat PERCENT_FORMATTER = new DecimalFormat("#0.0%");

    private Handler delayHandler = new Handler();

    private boolean isRetrying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);
        lblResponse = (TextView) findViewById(R.id.lblResponse);
        txtAnswer = (EditText) findViewById(R.id.txtAnswer);
        lblDisplayKana = (TextView) findViewById(R.id.lblDisplayKana);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        oldTextColour = lblResponse.getCurrentTextColor(); //kludge for reverting text colour

        OptionsControl.initialize(this);

        txtAnswer.setOnEditorActionListener(
                new TextView.OnEditorActionListener()
                {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
                    {
                        if (((actionId == EditorInfo.IME_ACTION_GO) || (actionId == EditorInfo.IME_NULL)) && btnSubmit.isEnabled())
                        {
                            submitAnswer();
                            return true;
                        }
                        else
                            return false;
                    }
                }
        );

        resetQuiz();
        nextQuestion();
    }

    private void resetQuiz()
    {
        totalQuestions = 0;
        totalCorrect = 0;

        lblDisplayKana.setText("");
        lblResponse.setText("");

        if (!OptionsControl.compareStrings(R.string.prefid_on_incorrect, R.string.prefid_on_incorrect_default))
            lblResponse.setMinLines(2);

        questionBank = Hiragana.QUESTIONS.getQuestionBank();
        questionBank.addQuestions(Katakana.QUESTIONS.getQuestionBank());
    }

    private void nextQuestion()
    {
        try
        {
            questionBank.newQuestion();
            lblDisplayKana.setText(questionBank.getCurrentKana());
//            txtAnswer.setEnabled(true);
            isRetrying = false;
            ReadyForAnswer();
        }
        catch (NoQuestionsException ex)
        {
            lblDisplayKana.setText("");
            lblResponse.setText(R.string.no_questions);
            txtAnswer.setEnabled(false);
            btnSubmit.setEnabled(false);
            lblResponse.setTextColor(oldTextColour); //kludge for reverting text colours
            txtAnswer.setText("");
        }

        TextView lblScore = (TextView) findViewById(R.id.lblScore);

        if (totalQuestions > 0)
        {
            lblScore.setText(R.string.score_label);
            lblScore.append(": ");

            lblScore.append(PERCENT_FORMATTER.format((float) totalCorrect / (float) totalQuestions));

            lblScore.append(System.getProperty("line.separator"));
            lblScore.append(Integer.toString(totalCorrect));
            lblScore.append(" / ");
            lblScore.append(Integer.toString(totalQuestions));
        }
        else
            lblScore.setText("");
    }

    private void checkAnswer()
    {
        boolean isNewQuestion = true;
        btnSubmit.setEnabled(false);

        if (questionBank.checkCurrentAnswer(txtAnswer.getText().toString()))
        {
            lblResponse.setText(R.string.correct_answer);
            lblResponse.setTextColor(ContextCompat.getColor(this, R.color.correct));
            if (!isRetrying)
                totalCorrect++;
        }
        else
        {
            lblResponse.setText(R.string.incorrect_answer);
            lblResponse.setTextColor(ContextCompat.getColor(this, R.color.incorrect));

            if (OptionsControl.compareStrings(R.string.prefid_on_incorrect, R.string.prefid_on_incorrect_show_answer))
            {
                lblResponse.append(System.getProperty("line.separator"));
                lblResponse.append(getResources().getText(R.string.show_correct_answer));
                lblResponse.append(": ");
                lblResponse.append(questionBank.fetchCorrectAnswer());
            }
            else if (OptionsControl.compareStrings(R.string.prefid_on_incorrect, R.string.prefid_on_incorrect_retry))
            {
                txtAnswer.setText("");
                lblResponse.append(System.getProperty("line.separator"));
                lblResponse.append(getResources().getText(R.string.try_again));
                isRetrying = true;
                isNewQuestion = false;

                delayHandler.postDelayed(
                        new Runnable()
                        {
                            public void run()
                            {
                                ReadyForAnswer();
                            }
                        }, 1000
                );
            }
        }

        if (isNewQuestion)
        {
            totalQuestions++;
            //txtAnswer.setEnabled(false); //TODO: Find a way to disable a textbox without closing the touch keyboard
            delayHandler.postDelayed(
                    new Runnable()
                    {
                        public void run()
                        {
                            nextQuestion();
                        }
                    }, 1000
            );
        }
    }

    private void ReadyForAnswer()
    {
        lblResponse.setText(R.string.request_answer);
        btnSubmit.setEnabled(true);
        txtAnswer.requestFocus();
        lblResponse.setTextColor(oldTextColour); //kludge for reverting text colours
        txtAnswer.setText("");
    }

    public void submitAnswer(View view)
    {
        submitAnswer();
    }

    private void submitAnswer()
    {
        if (!txtAnswer.getText().toString().trim().isEmpty()) //ignore if blank
            checkAnswer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Class destination;

        switch (item.getItemId())
        {
            case R.id.mnuSelection:
                destination = KanaSelection.class;
                break;
            case R.id.mnuReference:
                destination = ReferenceScreen.class;
                break;
            case R.id.mnuOptions:
                destination = OptionsScreen.class;
                break;
            case R.id.mnuAbout:
                destination = AboutScreen.class;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        startActivityForResult(new Intent(MainQuiz.this, destination), 1);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1)
        {
            resetQuiz();
            nextQuestion();
        }
    }
}
