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

    private static final KanaQuestion[] KANA_SET_1 = {
            new KanaQuestion('あ', "a"),
            new KanaQuestion('い', "i"),
            new KanaQuestion('う', "u"),
            new KanaQuestion('え', "e"),
            new KanaQuestion('お', "o")};

    private static final KanaQuestion[] KANA_SET_2 = {
            new KanaQuestion('か', "ka"),
            new KanaQuestion('き', "ki"),
            new KanaQuestion('く', "ku"),
            new KanaQuestion('け', "ke"),
            new KanaQuestion('こ', "ko"),
            new KanaQuestion('が', "ga"),
            new KanaQuestion('ぎ', "gi"),
            new KanaQuestion('ぐ', "gu"),
            new KanaQuestion('げ', "ge"),
            new KanaQuestion('ご', "go")};

    private static final KanaQuestion[] KANA_SET_3 = {
            new KanaQuestion('さ', "sa"),
            new KanaQuestion('し', "shi"),
            new KanaQuestion('す', "su"),
            new KanaQuestion('せ', "se"),
            new KanaQuestion('そ', "so"),
            new KanaQuestion('ざ', "za"),
            new KanaQuestion('じ', "ji"),
            new KanaQuestion('ず', "zu"),
            new KanaQuestion('ぜ', "ze"),
            new KanaQuestion('ぞ', "zo")};

    private static final KanaQuestion[] KANA_SET_4 = {
            new KanaQuestion('た', "ta"),
            new KanaQuestion('ち', "chi"),
            new KanaQuestion('つ', "tsu"),
            new KanaQuestion('て', "te"),
            new KanaQuestion('と', "to"),
            new KanaQuestion('だ', "da"),
            new KanaQuestion('ぢ', "ji"),
            new KanaQuestion('づ', "zu"),
            new KanaQuestion('で', "de"),
            new KanaQuestion('ど', "do")};

    private static final KanaQuestion[] KANA_SET_5 = {
            new KanaQuestion('な', "na"),
            new KanaQuestion('に', "ni"),
            new KanaQuestion('ぬ', "nu"),
            new KanaQuestion('ね', "ne"),
            new KanaQuestion('の', "no")};

    private static final KanaQuestion[] KANA_SET_6 = {
            new KanaQuestion('は', "ha"),
            new KanaQuestion('ひ', "hi"),
            new KanaQuestion('ふ', "fu"),
            new KanaQuestion('へ', "he"),
            new KanaQuestion('ほ', "ho"),
            new KanaQuestion('ば', "ba"),
            new KanaQuestion('び', "bi"),
            new KanaQuestion('ぶ', "bu"),
            new KanaQuestion('べ', "be"),
            new KanaQuestion('ぼ', "bo"),
            new KanaQuestion('ぱ', "pa"),
            new KanaQuestion('ぴ', "pi"),
            new KanaQuestion('ぷ', "pu"),
            new KanaQuestion('ぺ', "pe"),
            new KanaQuestion('ぽ', "po")};

    private static final KanaQuestion[] KANA_SET_7 = {
            new KanaQuestion('ま', "ma"),
            new KanaQuestion('み', "mi"),
            new KanaQuestion('む', "mu"),
            new KanaQuestion('め', "me"),
            new KanaQuestion('も', "mo")};

    private static final KanaQuestion[] KANA_SET_8 = {
            new KanaQuestion('ら', "ra"),
            new KanaQuestion('り', "ri"),
            new KanaQuestion('る', "ru"),
            new KanaQuestion('れ', "re"),
            new KanaQuestion('ろ', "ro")};

    private static final KanaQuestion[] KANA_SET_9 = {
            new KanaQuestion('や', "ya"),
            new KanaQuestion('ゆ', "yu"),
            new KanaQuestion('よ', "yo"),
            new KanaQuestion('わ', "wa"),
            new KanaQuestion('を', "wo"),
            new KanaQuestion('ん', "n")};

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
        questionBank = new KanaQuestionBank();
        if (sharedPref.getBoolean("kana_set_1", false))
            questionBank.addQuestions(KANA_SET_1);
        if (sharedPref.getBoolean("kana_set_2", false))
            questionBank.addQuestions(KANA_SET_2);
        if (sharedPref.getBoolean("kana_set_3", false))
            questionBank.addQuestions(KANA_SET_3);
        if (sharedPref.getBoolean("kana_set_4", false))
            questionBank.addQuestions(KANA_SET_4);
        if (sharedPref.getBoolean("kana_set_5", false))
            questionBank.addQuestions(KANA_SET_5);
        if (sharedPref.getBoolean("kana_set_6", false))
            questionBank.addQuestions(KANA_SET_6);
        if (sharedPref.getBoolean("kana_set_7", false))
            questionBank.addQuestions(KANA_SET_7);
        if (sharedPref.getBoolean("kana_set_8", false))
            questionBank.addQuestions(KANA_SET_8);
        if (sharedPref.getBoolean("kana_set_9", false))
            questionBank.addQuestions(KANA_SET_9);
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