/*
 *    Copyright 2019 T Duke Perry
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.noprestige.kanaquiz.options;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.questions.QuestionManagement;

import org.threeten.bp.LocalDate;

import java.util.Set;

import androidx.preference.PreferenceManager;

public final class OptionsControl
{
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Resources resources;

    private OptionsControl() {}

    @SuppressLint("CommitPrefEdits")
    public static void initialize(Application context)
    {
        if (sharedPreferences == null)
        {
            Context appContext = context;
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
        return sharedPreferences.getBoolean(prefId, QuestionManagement.getHiragana().getPrefId(1).equals(prefId));
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

    public static Set<String> getStringSet(int resId)
    {
        return getStringSet(resources.getString(resId));
    }

    public static Set<String> getStringSet(String prefId)
    {
        return sharedPreferences.getStringSet(prefId, null);
    }

    public static void setStringSet(int resId, Set<String> setting)
    {
        setStringSet(resources.getString(resId), setting);
    }

    public static void setStringSet(String prefId, Set<String> setting)
    {
        editor.putStringSet(prefId, setting);
        editor.apply();
    }

    public static LocalDate getDate(int resId)
    {
        return getDate(resources.getString(resId));
    }

    public static LocalDate getDate(String prefId)
    {
        String record = sharedPreferences.getString(prefId, "");

        if ((record == null) || "".equals(record))
            return null;
        else
            return LocalDate.parse(record);
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
