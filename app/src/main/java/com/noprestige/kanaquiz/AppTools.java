package com.noprestige.kanaquiz;

import android.content.Context;

import com.noprestige.kanaquiz.logs.LogDatabase;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.QuestionManagement;

public abstract class AppTools
{
    private static boolean isInitialized; //defaults to false

    public static void initializeManagers(Context context)
    {
        if (!isInitialized)
        {
            OptionsControl.initialize(context.getApplicationContext());
            QuestionManagement.initialize(context.getApplicationContext());
            LogDatabase.initialize(context.getApplicationContext());
            isInitialized = true;
        }
    }
}
