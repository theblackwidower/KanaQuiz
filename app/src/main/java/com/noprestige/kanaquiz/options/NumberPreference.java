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

package com.noprestige.kanaquiz.options;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.noprestige.kanaquiz.R;

import androidx.preference.DialogPreference;

class NumberPreference extends DialogPreference
{
    public NumberPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        setDialogLayoutResource(R.layout.number_preference_dialog);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);

        setDialogIcon(null);
    }

    @Override
    protected void onSetInitialValue(Object defaultValue)
    {
        if (defaultValue != null)
            persistInt((Integer) defaultValue);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index)
    {
        return a.getInteger(index, 0);
    }
}
