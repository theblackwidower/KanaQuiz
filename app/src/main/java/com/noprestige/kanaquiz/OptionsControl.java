package com.noprestige.kanaquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

abstract class OptionsControl
{
    final static int CODE_ON_INCORRECT_MOVE_ON = 0;
    final static int CODE_ON_INCORRECT_SHOW_ANSWER = 1;
    final static int CODE_ON_INCORRECT_RETRY = 2;

    static private SharedPreferences sharedPreferences;
    static private SharedPreferences.Editor editor;
    static private Resources resources;

    static void initialize(Context context)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        editor = sharedPreferences.edit();
        resources = context.getResources();
    }

    static boolean getBoolean(int resId)
    {
        return getBoolean(resources.getString(resId));
    }
    static boolean getBoolean(String prefId)
    {
        return sharedPreferences.getBoolean(prefId, false);
    }

    static void setBoolean(int resId, boolean setting)
    {
        setBoolean(resources.getString(resId), setting);
    }
    static void setBoolean(String prefId, boolean setting)
    {
        editor.putBoolean(prefId, setting);
        editor.apply();
    }

    static int getInt(int resId)
    {
        return getInt(resources.getString(resId));
    }
    static int getInt(String prefId)
    {
        return sharedPreferences.getInt(prefId, 0);
    }

    static void setInt(int resId, int setting)
    {
        setInt(resources.getString(resId), setting);
    }
    static void setInt(String prefId, int setting)
    {
        editor.putInt(prefId, setting);
        editor.apply();
    }
}
