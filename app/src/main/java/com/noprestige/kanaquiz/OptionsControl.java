package com.noprestige.kanaquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

abstract class OptionsControl
{
    final static String CODE_HIRAGANA_1 = "hiragana_set_1";
    final static String CODE_HIRAGANA_2 = "hiragana_set_2";
    final static String CODE_HIRAGANA_3 = "hiragana_set_3";
    final static String CODE_HIRAGANA_4 = "hiragana_set_4";
    final static String CODE_HIRAGANA_5 = "hiragana_set_5";
    final static String CODE_HIRAGANA_6 = "hiragana_set_6";
    final static String CODE_HIRAGANA_7 = "hiragana_set_7";
    final static String CODE_HIRAGANA_8 = "hiragana_set_8";
    final static String CODE_HIRAGANA_9 = "hiragana_set_9";
    final static String CODE_HIRAGANA_10 = "hiragana_set_10";

    final static String CODE_KATAKANA_1 = "katakana_set_1";
    final static String CODE_KATAKANA_2 = "katakana_set_2";
    final static String CODE_KATAKANA_3 = "katakana_set_3";
    final static String CODE_KATAKANA_4 = "katakana_set_4";
    final static String CODE_KATAKANA_5 = "katakana_set_5";
    final static String CODE_KATAKANA_6 = "katakana_set_6";
    final static String CODE_KATAKANA_7 = "katakana_set_7";
    final static String CODE_KATAKANA_8 = "katakana_set_8";
    final static String CODE_KATAKANA_9 = "katakana_set_9";
    final static String CODE_KATAKANA_10 = "katakana_set_10";

    final static String CODE_ON_INCORRECT_ACTION = "on_incorrect";
    final static int CODE_ON_INCORRECT_MOVE_ON = 0;
    final static int CODE_ON_INCORRECT_SHOW_ANSWER = 1;
    final static int CODE_ON_INCORRECT_RETRY = 2;

    static private SharedPreferences sharedPreferences;
    static private SharedPreferences.Editor editor;

    static void initialize(Context context)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        editor = sharedPreferences.edit();
    }

    static boolean getBoolean(String prefId)
    {
        return sharedPreferences.getBoolean(prefId, false);
    }

    static void setBoolean(String prefId, boolean setting)
    {
        editor.putBoolean(prefId, setting);
        editor.apply();
    }

    static int getInt(String prefId)
    {
        return sharedPreferences.getInt(prefId, 0);
    }

    static void setInt(String prefId, int setting)
    {
        editor.putInt(prefId, setting);
        editor.apply();
    }
}
