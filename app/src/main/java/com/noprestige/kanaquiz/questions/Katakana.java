package com.noprestige.kanaquiz.questions;

import android.content.Context;

import com.noprestige.kanaquiz.R;

public class Katakana extends QuestionManagement
{
    public static Katakana QUESTIONS = null;

    private Katakana()
    {
    }

    static public void initialize(Context context)
    {
        if (QUESTIONS != null)
        {
            Context appContext = context.getApplicationContext();
            QUESTIONS = new Katakana();
            parseXml(appContext.getResources().getXml(R.xml.katakana), appContext.getResources(), QUESTIONS);
        }
    }
}
