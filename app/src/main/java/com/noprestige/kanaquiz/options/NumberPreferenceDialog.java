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
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.noprestige.kanaquiz.R;

import androidx.preference.PreferenceDialogFragmentCompat;

public class NumberPreferenceDialog extends PreferenceDialogFragmentCompat
{
    private NumberPicker numberPicker;

    static NumberPreferenceDialog newInstance(String key)
    {
        Bundle args = new Bundle();
        NumberPreferenceDialog dialog = new NumberPreferenceDialog();
        args.putString(ARG_KEY, key);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    protected View onCreateDialogView(Context context)
    {
        View dialog = super.onCreateDialogView(context);

        numberPicker = dialog.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(214); //TODO: implement as property of class
        //Currently set to the number of possible questions
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setValue(OptionsControl.getInt(getArguments().getString(ARG_KEY)));

        return dialog;
    }

    @Override
    public void onDialogClosed(boolean positiveResult)
    {
        if (positiveResult)
        {
            int prefValue = numberPicker.getValue();
            if (prefValue < 1)
                prefValue = 1;
            OptionsControl.setInt(getArguments().getString(ARG_KEY), prefValue);
        }
    }
}
