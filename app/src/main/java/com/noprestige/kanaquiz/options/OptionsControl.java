/*
 *    Copyright 2021 T Duke Perry
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

import java.time.LocalDate;

import java.util.Map;
import java.util.Set;

import androidx.preference.PreferenceManager;

import static com.noprestige.kanaquiz.questions.QuestionManagement.SUBPREFERENCE_DELIMITER;

public final class OptionsControl
{
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Resources resources;

    private static final String DEFAULT_QUESTION_SET = "hiragana_set_1";

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
        return sharedPreferences.getBoolean(prefId, false);
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

    public static Boolean getQuestionSetBool(String prefId)
    {
        if (exists(prefId))
            return getBoolean(prefId);
        else
        {
            boolean hasTrue = false;
            boolean hasFalse = false;
            for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet())
                if (entry.getKey().startsWith(prefId + SUBPREFERENCE_DELIMITER))
                    if ((Boolean) entry.getValue())
                        hasTrue = true;
                    else
                        hasFalse = true;

            if (hasTrue && hasFalse)
                return null;
            else
                return hasTrue;
        }
    }

    public static void setQuestionSetBool(String prefId, boolean setting)
    {
        for (String key : sharedPreferences.getAll().keySet())
            if (key.startsWith(prefId + SUBPREFERENCE_DELIMITER))
                delete(key);
        editor.putBoolean(prefId, setting);
        editor.apply();
    }

    public static void setQuestionSetDefaults(String[] prefIds)
    {
        Set<String> extantPrefs = sharedPreferences.getAll().keySet();
        for (String prefId : prefIds)
            if (!extantPrefs.contains(prefId))
            {
                boolean setDefault = true;
                for (String key : extantPrefs)
                    if (key.startsWith(prefId + SUBPREFERENCE_DELIMITER))
                    {
                        setDefault = false;
                        break;
                    }
                if (setDefault)
                    setBoolean(prefId, (prefId.equals(DEFAULT_QUESTION_SET)));
            }
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

    public static boolean exists(int resId)
    {
        return exists(resources.getString(resId));
    }

    public static boolean exists(String prefId)
    {
        return sharedPreferences.contains(prefId);
    }

    public static void delete(int resId)
    {
        delete(resources.getString(resId));
    }

    public static void delete(String prefId)
    {
        editor.remove(prefId);
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
