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
