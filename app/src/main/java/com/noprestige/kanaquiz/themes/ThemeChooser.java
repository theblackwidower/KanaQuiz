/*
 *    Copyright 2018 T Duke Perry
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

package com.noprestige.kanaquiz.themes;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.noprestige.kanaquiz.R;

import androidx.preference.DialogPreference;

public class ThemeChooser extends DialogPreference
{
    public ThemeChooser(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.theme_chooser_dialog);

        setPositiveButtonText(R.string.select_theme);
        setNegativeButtonText(android.R.string.cancel);

        setDialogIcon(null);
    }

    @Override
    protected void onSetInitialValue(Object defaultValue)
    {
        if (defaultValue != null)
            persistString((String) defaultValue);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index)
    {
        return a.getString(index);
    }
}
