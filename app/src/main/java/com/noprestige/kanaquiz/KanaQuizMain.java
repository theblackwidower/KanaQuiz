package com.noprestige.kanaquiz;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.noprestige.kanaquiz.logs.LogDatabase;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.QuestionManagement;
import com.noprestige.kanaquiz.themes.ThemeManager;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class KanaQuizMain extends Application
{
    static KanaQuizMain app;

    @Override
    public void onCreate()
    {
        super.onCreate();

        app = this;

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

    public static boolean isGooglePlayStoreOnDevice()
    {
        //ref: http://jymden.com/android-check-if-google-play-store-is-installed-on-device/
        PackageManager packageManager = app.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);

        for (PackageInfo packageInfo : packages)
            if ("com.android.vending".equals(packageInfo.packageName))
                return true;

        return false;
    }
}
