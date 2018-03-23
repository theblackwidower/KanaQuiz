package com.noprestige.kanaquiz.questions;

import android.content.Context;

import com.noprestige.kanaquiz.R;

public class Hiragana extends QuestionManagement
{
    public static Hiragana QUESTIONS;

    private Hiragana() {}

    public static void initialize(Context context)
    {
        if (QUESTIONS == null)
        {
            QUESTIONS = new Hiragana();
            parseXml(R.xml.hiragana, context.getApplicationContext().getResources(), QUESTIONS);
        }
    }
}
