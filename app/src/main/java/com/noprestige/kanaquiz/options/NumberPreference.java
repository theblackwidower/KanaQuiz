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
