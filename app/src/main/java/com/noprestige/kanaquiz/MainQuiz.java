package com.noprestige.kanaquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainQuiz extends AppCompatActivity {

    private int totalQuestions = 0;
    private int totalCorrect = 0;

    private KanaQuestionBank questionBank;

    private TextView lblResponse;
    private EditText txtAnswer;
    private TextView lblDisplayKana;
    private Button btnSubmit;

    private int oldTextColour;
    private DecimalFormat percentFormatter = new DecimalFormat("#0.0");
    private SharedPreferences sharedPref;

    private Handler delayHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);
        lblResponse = (TextView) findViewById(R.id.lblResponse);
        txtAnswer = (EditText) findViewById(R.id.txtAnswer);
        lblDisplayKana = (TextView) findViewById(R.id.lblDisplayKana);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        oldTextColour = lblResponse.getCurrentTextColor(); //kludge for reverting text colour

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        txtAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO && btnSubmit.isEnabled()) {
                    submitAnswer();
                    handled = true;
                }
                return handled;
            }
        });

        buildQuestionBank();
        setQuestion();
        displayScore();
    }

    public void resetQuiz()
    {
        totalQuestions = 0;
        totalCorrect = 0;

        lblDisplayKana.setText("");
        lblResponse.setText("");
    }

    public void buildQuestionBank()
    {
        questionBank = HiraganaQuestions.getQuestionBank(sharedPref);
        questionBank.addQuestions(KatakanaQuestions.getQuestionBank(sharedPref));
    }

    private void setQuestion()
    {
        try
        {
            questionBank.newQuestion();
            lblDisplayKana.setText(Character.toString(questionBank.getCurrentKana()));
            lblResponse.setText(R.string.request_answer);
            txtAnswer.setEnabled(true);
            btnSubmit.setEnabled(true);
            txtAnswer.requestFocus();
        }
        catch (NoQuestionsException ex)
        {
            lblDisplayKana.setText("");
            lblResponse.setText(R.string.no_questions);
            txtAnswer.setEnabled(false);
            btnSubmit.setEnabled(false);
        }
        lblResponse.setTextColor(oldTextColour); //kludge for reverting text colours

        txtAnswer.setText("");
    }

    private void displayScore()
    {
        TextView lblScore = (TextView) findViewById(R.id.lblScore);

        if (totalQuestions > 0)
        {
            lblScore.setText(R.string.score_label);
            lblScore.append(": ");

            lblScore.append(percentFormatter.format(((float)totalCorrect / (float)totalQuestions) * 100));

            lblScore.append("%\n");
            lblScore.append(Integer.toString(totalCorrect));
            lblScore.append(" / ");
            lblScore.append(Integer.toString(totalQuestions));
        }
        else
            lblScore.setText("");
    }

    private void checkAnswer()
    {
        if (questionBank.checkCurrentAnswer(txtAnswer.getText().toString()))
        {
            totalCorrect++;
            lblResponse.setText(R.string.correct_answer);
            lblResponse.setTextColor(ContextCompat.getColor(this, R.color.correct));
        }
        else
        {
            lblResponse.setText(R.string.incorrect_answer);
            lblResponse.setTextColor(ContextCompat.getColor(this, R.color.incorrect));
        }
        totalQuestions++;
        txtAnswer.setText("");
        btnSubmit.setEnabled(false);
    }

    public void submitAnswer()
    {
        submitAnswer(findViewById(R.id.activity_main_quiz));
    }
    public void submitAnswer(View view)
    {
        if (!txtAnswer.getText().toString().trim().isEmpty()) //ignore if blank
        {
            checkAnswer();

            delayHandler.postDelayed(new Runnable() {
                public void run() {
                    displayScore();
                    setQuestion();
                }
            }, 1000);
        }
    }

    public void openOptions(View view)
    {
        Intent intent = new Intent(MainQuiz.this, SetSelection.class);
        startActivityForResult(intent, 1);
    }

    public void openAbout(View view)
    {
        Intent intent = new Intent(MainQuiz.this, AboutScreen.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1)
        {
            resetQuiz();
            displayScore();
            delayHandler.postDelayed(new Runnable()
            {
                public void run()
                {
                    buildQuestionBank();
                    setQuestion();
                }
            }, 500);
        }
    }
}