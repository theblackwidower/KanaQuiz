package com.noprestige.kanaquiz.themes;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

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

    public static int getThemeColour(Context context, int attr)
    {
        return context.getTheme().obtainStyledAttributes(new int[]{attr}).getColor(0, 0);
    }

    public static Typeface getThemeFont(Context context, int attr, int fontStyle)
    {
        String fontFamily = context.getTheme().obtainStyledAttributes(new int[]{attr}).getString(0);
        return Typeface.create(fontFamily, fontStyle);
    }

    public static Drawable getThemeDrawable(Context context, int attr)
    {
        return context.getTheme().obtainStyledAttributes(new int[]{attr}).getDrawable(0);
    }

}
