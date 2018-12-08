package com.noprestige.kanaquiz;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.noprestige.kanaquiz.logs.LogDatabase;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.QuestionManagement;
import com.noprestige.kanaquiz.themes.ThemeManager;

import java.lang.reflect.InvocationTargetException;

public abstract class KanaQuizMain extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        AndroidThreeTen.init(this);
        OptionsControl.initialize(this);
        QuestionManagement.initialize(this);
        LogDatabase.initialize(this);
        ThemeManager.initialize(this);

        //ref: https://stackoverflow.com/a/43584678/3582371
        registerReceiver(new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                QuestionManagement.initialize(context);
            }
        }, new IntentFilter(Intent.ACTION_LOCALE_CHANGED));
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
