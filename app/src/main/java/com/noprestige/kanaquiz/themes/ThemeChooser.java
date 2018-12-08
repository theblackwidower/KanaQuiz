package com.noprestige.kanaquiz.themes;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.noprestige.kanaquiz.R;

class ThemeChooser extends DialogPreference
{
    private String currentThemeId;
    private ViewGroup layout;

    public ThemeChooser(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.theme_chooser_dialog);

        setPositiveButtonText(R.string.save);
        setNegativeButtonText(android.R.string.cancel);

        setDialogIcon(null);
    }

    @Override
    protected void showDialog(Bundle state)
    {
        super.showDialog(state);

        layout = getDialog().findViewById(R.id.themeChooserLayout);

        for (int i = 0; i < layout.getChildCount(); i++)
            if (layout.getChildAt(i).getClass().equals(ThemeSelectionItem.class))
            {
                ThemeSelectionItem item = (ThemeSelectionItem) layout.getChildAt(i);
                if (item.getPrefId().equals(currentThemeId))
                {
                    item.select();
                    break;
                }
            }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
        if (positiveResult)
        {
            for (int i = 0; i < layout.getChildCount(); i++)
                if (layout.getChildAt(i).getClass().equals(ThemeSelectionItem.class))
                {
                    ThemeSelectionItem item = (ThemeSelectionItem) layout.getChildAt(i);
                    if (item.isSelected())
                    {
                        currentThemeId = item.getPrefId();
                        break;
                    }
                }
            persistString(currentThemeId);
            callChangeListener(currentThemeId);
        }
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue)
    {
        if (restorePersistedValue)
            currentThemeId = getPersistedString("");
        else
        {
            currentThemeId = (String) defaultValue;
            persistString(currentThemeId);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index)
    {
        return a.getString(index);
    }
}
