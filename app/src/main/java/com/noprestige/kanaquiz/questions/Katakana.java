package com.noprestige.kanaquiz.questions;

import android.content.Context;

import com.noprestige.kanaquiz.R;

public class Katakana extends QuestionManagement
{
    public static Katakana QUESTIONS = null;

    private Katakana() {}

    static public void initialize(Context context)
    {
        if (QUESTIONS != null)
        {
            QUESTIONS = new Katakana();
            parseXml(R.xml.katakana, context.getApplicationContext().getResources(), QUESTIONS);
        }
    }
}
