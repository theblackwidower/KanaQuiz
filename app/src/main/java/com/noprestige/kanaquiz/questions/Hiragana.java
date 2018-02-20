package com.noprestige.kanaquiz.questions;

import android.content.Context;

import com.noprestige.kanaquiz.R;

public class Hiragana extends QuestionManagement
{
    public static final Hiragana QUESTIONS = new Hiragana();

    private Hiragana()
    {
    }

    static public void initialize(Context context)
    {
        parseXml(context.getResources().getXml(R.xml.hiragana), context.getResources(), QUESTIONS);
    }
}
