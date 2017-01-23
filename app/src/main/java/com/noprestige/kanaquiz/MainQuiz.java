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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainQuiz extends AppCompatActivity {

    private int totalQuestions = 0;
    private int totalCorrect = 0;
    private String expectedAnswer;
    private int currentQuestionNumber;

    private Object[] availableQuestions;
    private Object[] acceptableAnswers;
    private int questionCount;
    private Random rng = new Random();

    private TextView lblResponse;
    private EditText txtAnswer;
    private TextView lblDisplayKana;
    private Button btnSubmit;
    private int oldTextColour;
    private DecimalFormat percentFormatter = new DecimalFormat("#0.0");
    private SharedPreferences sharedPref;

    private Handler delayHandler = new Handler();

    private static final String[] KANA_SET_1_QUESTIONS = {"あ", "い", "う", "え", "お"};
    private static final String[] KANA_SET_1_ANSWERS = {"a", "i", "u", "e", "o"};
    private static final int KANA_SET_1_COUNT = 5;

    private static final String[] KANA_SET_2_QUESTIONS = {"か", "き", "く", "け", "こ", "が", "ぎ", "ぐ", "げ", "ご"};
    private static final String[] KANA_SET_2_ANSWERS = {"ka", "ki", "ku", "ke", "ko", "ga", "gi", "gu", "ge", "go"};
    private static final int KANA_SET_2_COUNT = 10;

    private static final String[] KANA_SET_3_QUESTIONS = {"さ", "し", "す", "せ", "そ", "ざ", "じ", "ず", "ぜ", "ぞ"};
    private static final String[] KANA_SET_3_ANSWERS = {"sa", "shi", "su", "se", "so", "za", "ji", "zu", "ze", "zo"};
    private static final int KANA_SET_3_COUNT = 10;

    private static final String[] KANA_SET_4_QUESTIONS = {"た", "ち", "つ", "て", "と", "だ", "ぢ", "づ", "で", "ど"};
    private static final String[] KANA_SET_4_ANSWERS = {"ta", "chi", "tsu", "te", "to", "da", "ji", "zu", "de", "do"};
    private static final int KANA_SET_4_COUNT = 10;

    private static final String[] KANA_SET_5_QUESTIONS = {"な", "に", "ぬ", "ね", "の"};
    private static final String[] KANA_SET_5_ANSWERS = {"na", "ni", "nu", "ne", "no"};
    private static final int KANA_SET_5_COUNT = 5;

    private static final String[] KANA_SET_6_QUESTIONS = {"は", "ひ", "ふ", "へ", "ほ", "ば", "び", "ぶ", "べ", "ぼ", "ぱ", "ぴ", "ぷ", "ぺ", "ぽ"};
    private static final String[] KANA_SET_6_ANSWERS = {"ha", "hi", "fu", "he", "ho", "ba", "bi", "bu", "be", "bo", "pa", "pi", "pu", "pe", "po"};
    private static final int KANA_SET_6_COUNT = 15;

    private static final String[] KANA_SET_7_QUESTIONS = {"ま", "み", "む", "め", "も"};
    private static final String[] KANA_SET_7_ANSWERS = {"ma", "mi", "mu", "me", "mo"};
    private static final int KANA_SET_7_COUNT = 5;

    private static final String[] KANA_SET_8_QUESTIONS = {"ら", "り", "る", "れ", "ろ"};
    private static final String[] KANA_SET_8_ANSWERS = {"ra", "ri", "ru", "re", "ro"};
    private static final int KANA_SET_8_COUNT = 5;

    private static final String[] KANA_SET_9_QUESTIONS = {"や", "ゆ", "よ", "わ", "を", "ん"};
    private static final String[] KANA_SET_9_ANSWERS = {"ya", "yu", "yo", "wa", "wo", "n"};
    private static final int KANA_SET_9_COUNT = 6;

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
        expectedAnswer = "";
        lblResponse.setText("");
    }

    public void buildQuestionBank()
    {
        questionCount = 0;
        ArrayList<String> questionBuffer = new ArrayList<>();
        ArrayList<String> answerBuffer = new ArrayList<>();
        if (sharedPref.getBoolean("kana_set_1", false))
        {
            questionBuffer.addAll(Arrays.asList(KANA_SET_1_QUESTIONS));
            answerBuffer.addAll(Arrays.asList(KANA_SET_1_ANSWERS));
            questionCount += KANA_SET_1_COUNT;
        }
        if (sharedPref.getBoolean("kana_set_2", false))
        {
            questionBuffer.addAll(Arrays.asList(KANA_SET_2_QUESTIONS));
            answerBuffer.addAll(Arrays.asList(KANA_SET_2_ANSWERS));
            questionCount += KANA_SET_2_COUNT;
        }
        if (sharedPref.getBoolean("kana_set_3", false))
        {
            questionBuffer.addAll(Arrays.asList(KANA_SET_3_QUESTIONS));
            answerBuffer.addAll(Arrays.asList(KANA_SET_3_ANSWERS));
            questionCount += KANA_SET_3_COUNT;
        }
        if (sharedPref.getBoolean("kana_set_4", false))
        {
            questionBuffer.addAll(Arrays.asList(KANA_SET_4_QUESTIONS));
            answerBuffer.addAll(Arrays.asList(KANA_SET_4_ANSWERS));
            questionCount += KANA_SET_4_COUNT;
        }
        if (sharedPref.getBoolean("kana_set_5", false))
        {
            questionBuffer.addAll(Arrays.asList(KANA_SET_5_QUESTIONS));
            answerBuffer.addAll(Arrays.asList(KANA_SET_5_ANSWERS));
            questionCount += KANA_SET_5_COUNT;
        }
        if (sharedPref.getBoolean("kana_set_6", false))
        {
            questionBuffer.addAll(Arrays.asList(KANA_SET_6_QUESTIONS));
            answerBuffer.addAll(Arrays.asList(KANA_SET_6_ANSWERS));
            questionCount += KANA_SET_6_COUNT;
        }
        if (sharedPref.getBoolean("kana_set_7", false))
        {
            questionBuffer.addAll(Arrays.asList(KANA_SET_7_QUESTIONS));
            answerBuffer.addAll(Arrays.asList(KANA_SET_7_ANSWERS));
            questionCount += KANA_SET_7_COUNT;
        }
        if (sharedPref.getBoolean("kana_set_8", false))
        {
            questionBuffer.addAll(Arrays.asList(KANA_SET_8_QUESTIONS));
            answerBuffer.addAll(Arrays.asList(KANA_SET_8_ANSWERS));
            questionCount += KANA_SET_8_COUNT;
        }
        if (sharedPref.getBoolean("kana_set_9", false))
        {
            questionBuffer.addAll(Arrays.asList(KANA_SET_9_QUESTIONS));
            answerBuffer.addAll(Arrays.asList(KANA_SET_9_ANSWERS));
            questionCount += KANA_SET_9_COUNT;
        }

        if (questionBuffer.isEmpty() || answerBuffer.isEmpty())
        {
            availableQuestions = new String[]{};
            acceptableAnswers = new String[]{};
        }
        else
        {
            availableQuestions = questionBuffer.toArray();
            acceptableAnswers = answerBuffer.toArray();
        }
    }

    private void setQuestion()
    {
        if (questionCount > 0)
        {
            int newQuestionNumber;
            do
            {
                newQuestionNumber = rng.nextInt(questionCount);
            } while (currentQuestionNumber == newQuestionNumber);
            currentQuestionNumber = newQuestionNumber;

            lblDisplayKana.setText(availableQuestions[currentQuestionNumber].toString());
            expectedAnswer = acceptableAnswers[currentQuestionNumber].toString();
            lblResponse.setText(R.string.request_answer);
            txtAnswer.setEnabled(true);
            btnSubmit.setEnabled(true);
            txtAnswer.requestFocus();
        }
        else
        {
            lblDisplayKana.setText("");
            expectedAnswer = "";
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
        if (expectedAnswer.equals(txtAnswer.getText().toString().trim().toLowerCase()))
        {
            totalCorrect++;
            lblResponse.setText(R.string.correct_answer);
            lblResponse.setTextColor(ContextCompat.getColor(this, R.color.correct));
        }
        else
        {
            lblResponse.setText(R.string.incorrect_answer);
            lblResponse.append(" the correct answer is: ");
            lblResponse.append(expectedAnswer);
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
        //intent.putExtra("isChanged", false);
        startActivityForResult(intent, 1);
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
