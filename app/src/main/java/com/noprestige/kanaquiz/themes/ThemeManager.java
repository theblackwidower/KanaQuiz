package com.noprestige.kanaquiz.themes;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;

public final class ThemeManager
{
    private static Resources resources;

    private ThemeManager() {}

    public static void initialize(Application context)
    {
        resources = context.getResources();
    }

    public static int getCurrentThemeId()
    {
        return getThemeId(OptionsControl.getString(R.string.prefid_selected_theme));
    }

    public static int getThemeId(String prefId)
    {
        if (prefId.equals(resources.getString(R.string.themeid_cherry_blossom_tree)))
            return R.style.CherryBlossomTree;
        else if (prefId.equals(resources.getString(R.string.themeid_shibuya_night)))
            return R.style.ShibuyaNight;
        else if (prefId.equals(resources.getString(R.string.themeid_hot_spring)))
            return R.style.HotSpringSerenity;
        else if (prefId.equals(resources.getString(R.string.themeid_chrysanthemum)))
            return R.style.ChrysanthemumTwilight;
        else
            return R.style.Theme_AppCompat;
    }

    public static int getThemeColour(Context context, int attr)
    {
        return getThemeColour(context.getTheme(), attr);
    }

    public static int getThemeColour(Resources.Theme theme, int attr)
    {
        TypedArray array = theme.obtainStyledAttributes(new int[]{attr});
        int returnValue = array.getColor(0, 0);
        array.recycle();
        return returnValue;
    }

    public static Typeface getThemeFont(Context context, int attr, int fontStyle)
    {
        return getThemeFont(context.getTheme(), attr, fontStyle);
    }

    public static Typeface getThemeFont(Resources.Theme theme, int attr, int fontStyle)
    {
        TypedArray array = theme.obtainStyledAttributes(new int[]{attr});
        String fontFamily = array.getString(0);
        array.recycle();
        return Typeface.create(fontFamily, fontStyle);
    }

    public static Drawable getThemeDrawable(Context context, int attr)
    {
        return getThemeDrawable(context.getTheme(), attr);
    }

    public static Drawable getThemeDrawable(Resources.Theme theme, int attr)
    {
        TypedArray array = theme.obtainStyledAttributes(new int[]{attr});
        Drawable returnValue = array.getDrawable(0);
        array.recycle();
        return returnValue;
    }

    public static void setTheme(Activity activity)
    {
        activity.setTheme(getCurrentThemeId());
    }
}
