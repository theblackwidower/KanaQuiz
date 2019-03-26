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
