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
            QUESTIONS = new Hiragana();
            parseXml(R.xml.hiragana, context.getApplicationContext().getResources(), QUESTIONS);
        }
    }
}
