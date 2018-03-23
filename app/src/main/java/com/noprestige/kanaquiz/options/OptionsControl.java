package com.noprestige.kanaquiz.options;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.noprestige.kanaquiz.R;

public abstract class OptionsControl
{
    static private SharedPreferences sharedPreferences;
    static private SharedPreferences.Editor editor;
    static private Resources resources;

    @SuppressLint("CommitPrefEdits")
    public static void initialize(Context context)
    {
        if (sharedPreferences == null)
        {
            Context appContext = context.getApplicationContext();
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);
            editor = sharedPreferences.edit();
            resources = appContext.getResources();
            PreferenceManager.setDefaultValues(appContext, R.xml.preferences, false);
        }
    }

    public static boolean getBoolean(int resId)
    {
        return getBoolean(resources.getString(resId));
    }

    public static boolean getBoolean(String prefId)
    {
        //Boolean preferences to default to true, all others default to false
        return sharedPreferences.getBoolean(prefId, resources.getString(R.string.prefid_hiragana_1).equals(prefId));
    }

    public static void setBoolean(int resId, boolean setting)
    {
        setBoolean(resources.getString(resId), setting);
    }

    public static void setBoolean(String prefId, boolean setting)
    {
        editor.putBoolean(prefId, setting);
        editor.apply();
    }

    public static int getInt(int resId)
    {
        return getInt(resources.getString(resId));
    }

    public static int getInt(String prefId)
    {
        return sharedPreferences.getInt(prefId, 0);
    }

    public static void setInt(int resId, int setting)
    {
        setInt(resources.getString(resId), setting);
    }

    public static void setInt(String prefId, int setting)
    {
        editor.putInt(prefId, setting);
        editor.apply();
    }

    public static String getString(int resId)
    {
        return getString(resources.getString(resId));
    }

    public static String getString(String prefId)
    {
        return sharedPreferences.getString(prefId, "");
    }

    public static void setString(int resId, String setting)
    {
        setString(resources.getString(resId), setting);
    }

    public static void setString(String prefId, String setting)
    {
        editor.putString(prefId, setting);
        editor.apply();
    }

    public static boolean compareStrings(int prefId, int comparator)
    {
        return compareStrings(resources.getString(prefId), resources.getString(comparator));
    }

    public static boolean compareStrings(String prefId, String comparator)
    {
        return sharedPreferences.getString(prefId, "").equals(comparator);
    }
}
