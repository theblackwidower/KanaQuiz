package com.noprestige.kanaquiz.questions;

import android.content.Context;

import com.noprestige.kanaquiz.R;

public class Katakana extends QuestionManagement
{
    public static final Katakana QUESTIONS = new Katakana();

    private Katakana()
    {
    }

    static public void initialize(Context context)
    {
        parseXml(context.getResources().getXml(R.xml.katakana), context.getResources(), QUESTIONS);
    }
}
