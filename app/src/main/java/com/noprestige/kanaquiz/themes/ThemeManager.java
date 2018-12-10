package com.noprestige.kanaquiz.themes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.noprestige.kanaquiz.KanaQuiz;
import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;

import org.threeten.bp.LocalDate;

import moe.shizuku.fontprovider.FontProviderClient;
import moe.shizuku.fontprovider.FontRequest;

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
        TypedArray array = context.getTheme().obtainStyledAttributes(new int[]{attr});
        int returnValue = array.getColor(0, 0);
        array.recycle();
        return returnValue;
    }

    public static Typeface getThemeFont(Context context, int attr, int fontStyle)
    {
        TypedArray array = context.getTheme().obtainStyledAttributes(new int[]{attr});
        String fontFamily = array.getString(0);
        array.recycle();
        return Typeface.create(fontFamily, fontStyle);
    }

    public static Drawable getThemeDrawable(Context context, int attr)
    {
        TypedArray array = context.getTheme().obtainStyledAttributes(new int[]{attr});
        Drawable returnValue = array.getDrawable(0);
        array.recycle();
        return returnValue;
    }

    private static boolean isFontInitialized;

    public static void setTheme(Activity activity)
    {
        activity.setTheme(getCurrentThemeId());

        int code = FontProviderClient.checkAvailability(activity);
        if (code == FontProviderClient.FontProviderAvailability.NOT_INSTALLED)
        {
            if (!OptionsControl.getBoolean(R.string.prefid_ignore_font_provider))
            {
                LocalDate remindDate = OptionsControl.getDate(R.string.prefid_font_remind_date);
                if ((remindDate == null) || remindDate.isBefore(LocalDate.now()))
                {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                    dialogBuilder.setMessage(R.string.font_provider_install_request);
                    if (KanaQuiz.isGooglePlayStoreOnDevice())
                        dialogBuilder.setPositiveButton(R.string.get_on_google_play, (dialog, which) -> activity
                                .startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                                        "https://play.google.com/store/apps/details?id=moe.shizuku.fontprovider"))));
                    else
                        dialogBuilder.setPositiveButton(R.string.download_github, (dialog, which) -> activity
                                .startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("https://github.com/RikkaApps/FontProvider/releases/"))));
                    dialogBuilder.setNegativeButton(R.string.remind_tomorrow, (dialog, which) -> OptionsControl
                            .setString(R.string.prefid_font_remind_date, LocalDate.now().toString()));
                    dialogBuilder.setNeutralButton(R.string.ignore,
                            (dialog, which) -> OptionsControl.setBoolean(R.string.prefid_ignore_font_provider, true));
                    dialogBuilder.show();
                }
            }
        }
        else if (code == FontProviderClient.FontProviderAvailability.VERSION_TOO_LOW)
        {
            LocalDate remindDate = OptionsControl.getDate(R.string.prefid_font_update_alert_date);
            if ((remindDate == null) || remindDate.isBefore(LocalDate.now()))
            {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                dialogBuilder.setMessage(R.string.font_provider_update_request);
                if (KanaQuiz.isGooglePlayStoreOnDevice())
                    dialogBuilder.setPositiveButton(R.string.update_on_google_play, (dialog, which) -> activity
                            .startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                                    "https://play.google.com/store/apps/details?id=moe.shizuku.fontprovider"))));
                else
                    dialogBuilder.setPositiveButton(R.string.download_github, (dialog, which) -> activity.startActivity(
                            new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://github.com/RikkaApps/FontProvider/releases/"))));
                dialogBuilder.setNegativeButton(R.string.remind_tomorrow, (dialog, which) -> OptionsControl
                        .setString(R.string.prefid_font_update_alert_date, LocalDate.now().toString()));
                dialogBuilder.show();
            }
        }
        else if ((code == FontProviderClient.FontProviderAvailability.OK) && !isFontInitialized)
        {
            FontProviderClient client = FontProviderClient.create(activity);
            if (client != null)
            {
                client.replace(new FontRequest[]{FontRequest.NOTO_SERIF}, "Noto Serif CJK", "serif", "serif-thin",
                        "serif-light", "serif-medium", "serif-black", "serif-demilight", "serif-bold");

                client.setNextRequestReplaceFallbackFonts(true);

                client.replace("Noto Sans CJK", "sans-serif", "sans-serif-thin", "sans-serif-light",
                        "sans-serif-medium", "sans-serif-black", "sans-serif-demilight", "sans-serif-bold");

                isFontInitialized = true;
            }
        }
    }
}
