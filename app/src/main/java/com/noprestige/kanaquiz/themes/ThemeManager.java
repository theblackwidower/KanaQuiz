/*
 *    Copyright 2021 T Duke Perry
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

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.noprestige.kanaquiz.KanaQuizMain;
import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;

import java.time.LocalDate;

import androidx.annotation.RequiresApi;
import moe.shizuku.fontprovider.FontProviderClient;
import moe.shizuku.fontprovider.FontRequest;

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
            return R.style.CherryBlossomTree;
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

    public static Typeface getDefaultThemeFont(Context context, int fontStyle)
    {
        return getDefaultThemeFont(context.getTheme(), fontStyle);
    }

    public static Typeface getDefaultThemeFont(Resources.Theme theme, int fontStyle)
    {
        int attr;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            attr = android.R.attr.typeface;
        else
            attr = R.attr.fontFamily;
        return getThemeFont(theme, attr, fontStyle);
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
        //ref: https://developer.android.com/reference/android/widget/TextView.html#attr_android:typeface
        switch (fontFamily)
        {
            case "0":
                fontFamily = "normal";
                break;
            case "1":
                fontFamily = "sans";
                break;
            case "2":
                fontFamily = "serif";
                break;
            case "3":
                fontFamily = "monospace";
        }
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

    public static boolean isLightTheme(Context context)
    {
        return isLightTheme(context.getTheme());
    }

    public static boolean isLightTheme(Resources.Theme theme)
    {
        TypedArray array = theme.obtainStyledAttributes(new int[]{R.attr.isLightTheme});
        boolean returnValue = array.getBoolean(0, false);
        array.recycle();
        return returnValue;
    }

    private static boolean isFontInitialized;

    public static void setTheme(Activity activity)
    {
        activity.setTheme(getCurrentThemeId());

        // These two 'if' statements need to be structured like this to deal with a false positive caused by the
        // 'NewApi' lint inspection.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.P) && !isFontInitialized)
            {
                int code = FontProviderClient.checkAvailability(activity);
                if (code == FontProviderClient.FontProviderAvailability.NOT_INSTALLED)
                {
                    if (!OptionsControl.getBoolean(R.string.prefid_ignore_font_provider))
                        getDownloadDialog(activity, true);
                }
                else if (code == FontProviderClient.FontProviderAvailability.VERSION_TOO_LOW)
                    getDownloadDialog(activity, false);
                else if (code == FontProviderClient.FontProviderAvailability.OK)
                {
                    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M)
                        if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED)
                        {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                            dialogBuilder.setMessage(R.string.marshmallow_font_permission_request);
                            dialogBuilder.setPositiveButton(android.R.string.ok, null);
                            dialogBuilder.setOnDismissListener(dialog -> activity
                                    .requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0));
                            dialogBuilder.show();
                        }
                        else
                            initializeFonts(activity);
                    else
                        initializeFonts(activity);
                }
            }
    }

    public static void permissionRequestReturn(Activity activity, String[] permissions, int[] grantResults)
    {
        int length = Math.min(permissions.length, grantResults.length);
        for (int i = 0; i < length; i++)
            if (permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE) &&
                    (grantResults[i] == PackageManager.PERMISSION_GRANTED))
                activity.recreate();
    }

    @RequiresApi(21)
    private static void initializeFonts(Activity activity)
    {
        FontProviderClient client = FontProviderClient.create(activity);
        if (client != null)
        {
            Typeface[] serifFonts =
                    client.replace(new FontRequest[]{FontRequest.NOTO_SERIF}, "Noto Serif CJK", "serif", "serif-thin",
                            "serif-light", "serif-medium", "serif-black", "serif-demilight", "serif-bold");

            client.setNextRequestReplaceFallbackFonts(true);

            client.replace("Noto Sans CJK", "sans-serif", "sans-serif-thin", "sans-serif-light", "sans-serif-medium",
                    "sans-serif-black", "sans-serif-demilight", "sans-serif-bold");

            if ((serifFonts == null) || (serifFonts.length < 7))
            {
                LocalDate remindDate = OptionsControl.getDate(R.string.prefid_font_download_alert_date);
                if ((remindDate == null) || remindDate.isBefore(LocalDate.now()))
                {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                    dialogBuilder.setMessage(R.string.font_provider_font_request);
                    //ref: https://forums.xamarin.com/discussion/55897/
                    // launch-an-application-from-another-application-on-android
                    dialogBuilder.setPositiveButton(R.string.open_font_provider, (dialog, which) -> activity
                            .startActivity(activity.getPackageManager()
                                    .getLaunchIntentForPackage("moe.shizuku.fontprovider")));
                    dialogBuilder.setNegativeButton(R.string.remind_tomorrow, (dialog, which) -> OptionsControl
                            .setString(R.string.prefid_font_download_alert_date, LocalDate.now().toString()));

                    AlertDialog alertDialog = dialogBuilder.show();

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1)
                    {
                        //ref: https://stackoverflow.com/a/31350049/3582371
                        ViewParent buttonLayout = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).getParent();
                        if (buttonLayout.getClass().equals(LinearLayout.class))
                        {
                            ((LinearLayout) buttonLayout).setOrientation(LinearLayout.VERTICAL);
                            ((LinearLayout) buttonLayout).setGravity(Gravity.RIGHT);
                        }
                    }
                }
            }
            else
                isFontInitialized = true;
        }
    }

    @RequiresApi(21)
    private static void getDownloadDialog(Activity activity, boolean isNewInstall)
    {
        LocalDate remindDate = OptionsControl.getDate(R.string.prefid_font_remind_date);
        if ((remindDate == null) || remindDate.isBefore(LocalDate.now()))
        {
            int downloadMessageRefId;
            String downloadLink;
            if (KanaQuizMain.isGooglePlayStoreOnDevice())
            {
                downloadMessageRefId = isNewInstall ? R.string.get_on_google_play : R.string.update_on_google_play;
                downloadLink = "https://play.google.com/store/apps/details?id=moe.shizuku.fontprovider";
            }
            else
            {
                downloadMessageRefId = R.string.download_github;
                downloadLink = "https://github.com/RikkaApps/FontProvider/releases/";
            }

            int messageRefId =
                    isNewInstall ? R.string.font_provider_install_request : R.string.font_provider_update_request;

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            dialogBuilder.setMessage(messageRefId);
            dialogBuilder.setPositiveButton(downloadMessageRefId,
                    (dialog, which) -> activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(downloadLink))));
            dialogBuilder.setNegativeButton(R.string.remind_tomorrow, (dialog, which) -> OptionsControl
                    .setString(R.string.prefid_font_remind_date, LocalDate.now().toString()));

            if (isNewInstall)
                dialogBuilder.setNeutralButton(R.string.ignore,
                        (dialog, which) -> OptionsControl.setBoolean(R.string.prefid_ignore_font_provider, true));

            AlertDialog alertDialog = dialogBuilder.show();

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1)
            {
                //ref: https://stackoverflow.com/a/31350049/3582371
                ViewParent buttonLayout = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).getParent();
                if (buttonLayout.getClass().equals(LinearLayout.class))
                {
                    ((LinearLayout) buttonLayout).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) buttonLayout).setGravity(Gravity.RIGHT);
                }
            }
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
            //ref: https://stackoverflow.com/a/13866702/3582371
            activity.getWindow().getDecorView()
                    .setBackgroundColor(getThemeColour(activity, android.R.attr.colorBackground));
    }
}
