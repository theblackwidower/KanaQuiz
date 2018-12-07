package com.noprestige.kanaquiz.themes;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;

public final class ThemeManager
{
    private ThemeManager() {}

    public static int getCurrentThemeId()
    {
        if (OptionsControl.compareStrings(R.string.prefid_selected_theme, R.string.themeid_cherry_blossom_tree))
            return R.style.CherryBlossomTree;
        else if (OptionsControl.compareStrings(R.string.prefid_selected_theme, R.string.themeid_shibuya_night))
            return R.style.ShibuyaNight;
        else
            return R.style.CherryBlossomTree;
    }
}
