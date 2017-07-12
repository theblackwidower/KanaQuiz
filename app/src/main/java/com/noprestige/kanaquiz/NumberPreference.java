package com.noprestige.kanaquiz;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.widget.NumberPicker;

class NumberPreference extends DialogPreference
{
    private int repetitionLimit;
    private NumberPicker numberPicker;

    public NumberPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        setDialogLayoutResource(R.layout.number_preference_dialog);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);

        setDialogIcon(null);
    }

    @Override
    protected void showDialog(Bundle state)
    {
        super.showDialog(state);

        numberPicker = (NumberPicker) getDialog().findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(214); //TODO: implement as property of class
        //Currently set to the number of possible questions
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setValue(repetitionLimit);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
        if (positiveResult)
        {
            repetitionLimit = numberPicker.getValue();
            if (repetitionLimit < 1)
                repetitionLimit = 1;
            persistInt(repetitionLimit);
        }
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue)
    {
        if (restorePersistedValue)
            repetitionLimit = this.getPersistedInt(0);
        else
        {
            repetitionLimit = (Integer) defaultValue;
            persistInt(repetitionLimit);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index)
    {
        return a.getInteger(index, 0);
    }
}
