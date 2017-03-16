package com.noprestige.kanaquiz;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Interpolator;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

class NumberPreference extends DialogPreference
{
    int repetitionLimit;
    EditText txtNumber;

    public NumberPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        setDialogLayoutResource(R.layout.number_preference_dialog);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);

        setDialogIcon(null);
    }

    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);

        txtNumber = (EditText) getDialog().findViewById(R.id.txtNumber);
        txtNumber.setText(Integer.toString(repetitionLimit));
    }

    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
        if (positiveResult)
        {
            try
            {
                repetitionLimit = Integer.parseInt(txtNumber.getText().toString());
                if (repetitionLimit < 1)
                    repetitionLimit = 1;
                persistInt(repetitionLimit);
            }
            catch (NumberFormatException e)
            {
                //ignore
            }
        }
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue)
    {
        if (restorePersistedValue)
        {
            repetitionLimit = this.getPersistedInt(0);
        }
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
