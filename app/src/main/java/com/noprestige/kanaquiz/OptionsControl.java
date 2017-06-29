package com.noprestige.kanaquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

abstract class OptionsControl
{
    static private SharedPreferences sharedPreferences;
    static private SharedPreferences.Editor editor;
    static private Resources resources;

    static void initialize(Context context)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        editor = sharedPreferences.edit();
        resources = context.getResources();
        PreferenceManager.setDefaultValues(context, R.xml.preferences, false);
    }

    static boolean getBoolean(int resId)
    {
        return getBoolean(resources.getString(resId));
    }
    static boolean getBoolean(String prefId)
    {
        //Boolean preferences to default to true, all others default to false
        if (prefId.equals(resources.getString(R.string.prefid_hiragana_1)) ||
            prefId.equals(resources.getString(R.string.prefid_diacritics)))
            return sharedPreferences.getBoolean(prefId, true);
        else
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

    static String getString(int resId)
    {
        return getString(resources.getString(resId));
    }
    static String getString(String prefId)
    {
        return sharedPreferences.getString(prefId, "");
    }

    static void setString(int resId, String setting)
    {
        setString(resources.getString(resId), setting);
    }
    static void setString(String prefId, String setting)
    {
        editor.putString(prefId, setting);
        editor.apply();
    }

    static boolean compareStrings(int prefId, int comparator)
    {
        return compareStrings(resources.getString(prefId), resources.getString(comparator));
    }
    static boolean compareStrings(String prefId, String comparator)
    {
        return sharedPreferences.getString(prefId, "").equals(comparator);
    }
}
