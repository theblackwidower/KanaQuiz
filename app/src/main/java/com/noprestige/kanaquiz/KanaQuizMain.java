package com.noprestige.kanaquiz;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.noprestige.kanaquiz.logs.LogDatabase;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.QuestionManagement;

import java.lang.reflect.InvocationTargetException;

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

    public static boolean isAcraInstalled()
    {
        try
        {
            return (boolean) Class.forName("org.acra.ACRA").getMethod("isInitialised").invoke(null);
        }
        catch (ClassNotFoundException ignored) { }
        catch (NoSuchMethodException ignored) { }
        catch (IllegalAccessException ignored) { }
        catch (InvocationTargetException ignored) { }
        return false;
    }
}
