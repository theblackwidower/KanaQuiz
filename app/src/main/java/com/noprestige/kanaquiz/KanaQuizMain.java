package com.noprestige.kanaquiz;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.noprestige.kanaquiz.logs.LogDatabase;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.QuestionManagement;

public abstract class KanaQuizMain extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        AndroidThreeTen.init(getApplicationContext());
        OptionsControl.initialize(getApplicationContext());
        QuestionManagement.initialize(getApplicationContext());
        LogDatabase.initialize(getApplicationContext());
    }

    public static boolean isFirebaseInstalled()
    {
        try
        {
            Class.forName("com.google.firebase.analytics.FirebaseAnalytics");
            return true;
        }
        catch (ClassNotFoundException ex)
        {
            return false;
        }
    }
}
