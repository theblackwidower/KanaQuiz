package com.noprestige.kanaquiz;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.noprestige.kanaquiz.logs.LogDatabase;
import com.noprestige.kanaquiz.logs.LogView;
import com.noprestige.kanaquiz.options.KanaSelection;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.options.OptionsScreen;
import com.noprestige.kanaquiz.questions.KanaQuestionBank;
import com.noprestige.kanaquiz.questions.NoQuestionsException;
import com.noprestige.kanaquiz.questions.QuestionManagement;
import com.noprestige.kanaquiz.reference.ReferenceScreen;

import java.text.DecimalFormat;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.NORMAL;
import static android.os.Build.VERSION.SDK_INT;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class MainQuiz extends AppCompatActivity
{
    private int totalQuestions;
    private float totalCorrect;
    private boolean canSubmit;

    private KanaQuestionBank questionBank;

    private TextView lblResponse;
    private EditText txtAnswer;
    private TextView lblDisplayKana;
    private MultipleChoicePad btnMultipleChoice;

    private int oldTextColour;
    private static final DecimalFormat PERCENT_FORMATTER = new DecimalFormat("#0.0%");
    private static final DecimalFormat SCORE_FORMATTER = new DecimalFormat("#.##");

    private Handler delayHandler = new Handler();

    private int retryCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);
        lblResponse = findViewById(R.id.lblResponse);
        txtAnswer = findViewById(R.id.txtAnswer);
        lblDisplayKana = findViewById(R.id.lblDisplayKana);
        btnMultipleChoice = findViewById(R.id.btnMultipleChoice);

        oldTextColour = lblResponse.getCurrentTextColor(); // TODO: replace kludge for reverting text colour

        if (SDK_INT < 26)
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(lblDisplayKana, 12, 144, 2, COMPLEX_UNIT_SP);

        onConfigurationChanged(getResources().getConfiguration());

        OptionsControl.initialize(getApplicationContext());
        QuestionManagement.initialize(getApplicationContext());

        //TODO: Figure out how to run the database on a different thread.
        if (LogDatabase.DAO == null)
            LogDatabase.DAO = Room.databaseBuilder(getApplicationContext(),
                    LogDatabase.class, "user-logs").allowMainThreadQueries().build().logDao();

        txtAnswer.setOnEditorActionListener(
                new TextView.OnEditorActionListener()
                {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
                    {
                        String answer = v.getText().toString().trim();
                        if ((actionId == EditorInfo.IME_ACTION_GO) || (actionId == EditorInfo.IME_NULL))
                            checkAnswer(answer);
                        return true;
                    }
                }
        );

        resetQuiz();
        nextQuestion();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (newConfig.keyboard == Configuration.KEYBOARD_NOKEYS &&
                newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES)
            txtAnswer.setHint(R.string.answer_hint_touch);
        else
            txtAnswer.setHint(R.string.answer_hint_hardware);
    }

    private void resetQuiz()
    {
        totalQuestions = 0;
        totalCorrect = 0;

        lblDisplayKana.setText("");
        lblResponse.setText("");

        if (!OptionsControl.compareStrings(R.string.prefid_on_incorrect, R.string.prefid_on_incorrect_default))
            lblResponse.setMinLines(2);

        questionBank = QuestionManagement.getFullQuestionBank();

        txtAnswer.setEnabled(true);

        //TODO: Fix spacing when txtAnswer is rendered invisible
        if (OptionsControl.getBoolean(R.string.prefid_multiple_choice))
        {
            txtAnswer.setVisibility(View.INVISIBLE);
            txtAnswer.setHeight(0);
            btnMultipleChoice.setVisibility(View.VISIBLE);
        }
        else
        {
            txtAnswer.setVisibility(View.VISIBLE);
            txtAnswer.setLines(1);
            btnMultipleChoice.setVisibility(View.INVISIBLE);
        }
    }

    private void nextQuestion()
    {
        try
        {
            questionBank.newQuestion();
            lblDisplayKana.setText(questionBank.getCurrentKana());
            retryCount = 0;
            if (OptionsControl.getBoolean(R.string.prefid_multiple_choice))
                btnMultipleChoice.setChoices(questionBank.getPossibleAnswers());
            ReadyForAnswer();
        }
        catch (NoQuestionsException ex)
        {
            lblDisplayKana.setText("");
            lblResponse.setText(R.string.no_questions);
            canSubmit = false;
            lblResponse.setTypeface(null, NORMAL);
            lblResponse.setTextColor(oldTextColour); // TODO: replace kludge for reverting text colours
            if (OptionsControl.getBoolean(R.string.prefid_multiple_choice))
                btnMultipleChoice.deleteChoices();
            else
            {
                txtAnswer.setEnabled(false);
                txtAnswer.setText("");
            }
        }

        TextView lblScore = findViewById(R.id.lblScore);

        if (totalQuestions > 0)
        {
            lblScore.setText(R.string.score_label);
            lblScore.append(": ");

            lblScore.append(PERCENT_FORMATTER.format(totalCorrect / (float) totalQuestions));

            lblScore.append(System.getProperty("line.separator"));
            lblScore.append(SCORE_FORMATTER.format(totalCorrect));
            lblScore.append(" / ");
            lblScore.append(Integer.toString(totalQuestions));
        }
        else
            lblScore.setText("");
    }

    public void checkAnswer(String answer)
    {
        if (canSubmit && !answer.isEmpty())
        {
            boolean isGetNewQuestion = true;
            canSubmit = false;

            if (questionBank.checkCurrentAnswer(answer))
            {
                lblResponse.setText(R.string.correct_answer);
                lblResponse.setTypeface(null, BOLD);
                lblResponse.setTextColor(ContextCompat.getColor(this, R.color.correct));
                if (retryCount == 0)
                {
                    totalCorrect++;
                    LogDatabase.DAO.reportCorrectAnswer(lblDisplayKana.getText().toString());
                }
                else if (retryCount <= 4) //anything over 4 retrys gets no score at all
                    totalCorrect += Math.pow(0.5f, retryCount);
            }
            else
            {
                lblResponse.setText(R.string.incorrect_answer);
                lblResponse.setTypeface(null, BOLD);
                lblResponse.setTextColor(ContextCompat.getColor(this, R.color.incorrect));

                if (retryCount == 0)
                    LogDatabase.DAO.reportIncorrectAnswer(lblDisplayKana.getText().toString(), answer);
                else
                    LogDatabase.DAO.reportIncorrectRetry(lblDisplayKana.getText().toString(), answer);

                if (OptionsControl.compareStrings(R.string.prefid_on_incorrect, R.string.prefid_on_incorrect_show_answer))
                {
                    lblResponse.append(System.getProperty("line.separator"));
                    lblResponse.append(getResources().getText(R.string.show_correct_answer));
                    lblResponse.append(": ");
                    lblResponse.append(questionBank.fetchCorrectAnswer());
                }
                else if (OptionsControl.compareStrings(R.string.prefid_on_incorrect, R.string.prefid_on_incorrect_retry))
                {
                    lblResponse.append(System.getProperty("line.separator"));
                    lblResponse.append(getResources().getText(R.string.try_again));
                    retryCount++;
                    isGetNewQuestion = false;

                    delayHandler.postDelayed(
                            new Runnable()
                            {
                                public void run()
                                {
                                    ReadyForAnswer();
                                    btnMultipleChoice.enableButtons();
                                }
                            }, 1000
                    );
                }
            }

            if (isGetNewQuestion)
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
    }

    private void ReadyForAnswer()
    {
        if (OptionsControl.getBoolean(R.string.prefid_multiple_choice))
            lblResponse.setText(R.string.request_multiple_choice);
        else
        {
            lblResponse.setText(R.string.request_text_input);
            txtAnswer.requestFocus();
            txtAnswer.setText("");
        }
        canSubmit = true;
        lblResponse.setTypeface(null, NORMAL);
        lblResponse.setTextColor(oldTextColour); // TODO: replace kludge for reverting text colours
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
        int result = 0;

        switch (item.getItemId())
        {
            case R.id.mnuSelection:
                destination = KanaSelection.class;
                result = 1;
                break;
            case R.id.mnuReference:
                destination = ReferenceScreen.class;
                break;
            case R.id.mnuOptions:
                destination = OptionsScreen.class;
                result = 1;
                break;
            case R.id.mnuLogs:
                destination = LogView.class;
                break;
            case R.id.mnuAbout:
                destination = AboutScreen.class;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        startActivityForResult(new Intent(MainQuiz.this, destination), result);
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
