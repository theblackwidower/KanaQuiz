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
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;

import androidx.preference.PreferenceDialogFragmentCompat;

public class ThemeChooserDialog extends PreferenceDialogFragmentCompat
{
    private ViewGroup layout;

    public static ThemeChooserDialog newInstance(String key)
    {
        Bundle args = new Bundle();
        ThemeChooserDialog dialog = new ThemeChooserDialog();
        args.putString(ARG_KEY, key);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    protected View onCreateDialogView(Context context)
    {
        View dialog = super.onCreateDialogView(context);

        String currentThemeId = OptionsControl.getString(getArguments().getString(ARG_KEY));

        layout = dialog.findViewById(R.id.themeChooserLayout);

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

        return dialog;
    }

    @Override
    public void onDialogClosed(boolean positiveResult)
    {
        if (positiveResult)
            for (int i = 0; i < layout.getChildCount(); i++)
                if (layout.getChildAt(i).getClass().equals(ThemeSelectionItem.class))
                {
                    ThemeSelectionItem item = (ThemeSelectionItem) layout.getChildAt(i);
                    if (item.isSelected())
                    {
                        String currentThemeId = item.getPrefId();
                        OptionsControl.setString(getArguments().getString(ARG_KEY), currentThemeId);
                        getPreference().callChangeListener(currentThemeId);
                        break;
                    }
                }
    }
}
