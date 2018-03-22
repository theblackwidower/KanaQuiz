package com.noprestige.kanaquiz.questions;

import android.content.Context;

import com.noprestige.kanaquiz.R;

public class Hiragana extends QuestionManagement
{
    public static Hiragana QUESTIONS = null;

    private Hiragana()
    {
    }

    static public void initialize(Context context)
    {
        if (QUESTIONS != null)
        {
            Context appContext = context.getApplicationContext();
            QUESTIONS = new Hiragana();
            parseXml(appContext.getResources().getXml(R.xml.hiragana), appContext.getResources(), QUESTIONS);
        }
    }
}
