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

package com.noprestige.kanaquiz.options;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.noprestige.kanaquiz.KanaQuiz;
import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.logs.LogDao;
import com.noprestige.kanaquiz.themes.ThemeChooser;
import com.noprestige.kanaquiz.themes.ThemeChooserDialog;

import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import moe.shizuku.fontprovider.FontProviderClient;

public class OptionsFragment extends PreferenceFragmentCompat
{
    static class DeleteAll implements Runnable
    {
        private Preference btnClearLogs;

        DeleteAll(Preference btnClearLogs)
        {
            this.btnClearLogs = btnClearLogs;
        }

        @Override
        public void run()
        {
            LogDao.deleteAll();
            //ref: https://stackoverflow.com/a/11125271
            new Handler(btnClearLogs.getContext().getMainLooper()).post(this::done);
        }

        private void done()
        {
            btnClearLogs.setEnabled(false);
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        addPreferencesFromResource(R.xml.preferences);

        Preference onIncorrect = findPreference(getResources().getString(R.string.prefid_on_incorrect));

        setSummary(onIncorrect, OptionsControl.getString(R.string.prefid_on_incorrect));

        onIncorrect.setOnPreferenceChangeListener(this::setSummary);

        //ref: https://stackoverflow.com/questions/5330677/android-preferences-onclick-event
        findPreference("clear_logs").setOnPreferenceClickListener(btnClearLogs ->
        {
            //ref: https://www.codespeedy.com/multithreading-in-java/
            new Thread(new DeleteAll(btnClearLogs)).start();
            return true;
        });

        findPreference(getResources().getString(R.string.prefid_selected_theme))
                .setOnPreferenceChangeListener((preference, newValue) ->
                {
                    getActivity().recreate();
                    return true;
                });

        Preference fontProviderLink = findPreference("font_provider_link");

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P && FontProviderClient.checkAvailability(getActivity()) ==
                FontProviderClient.FontProviderAvailability.NOT_INSTALLED)
        {
            String downloadLink;
            if (KanaQuiz.isGooglePlayStoreOnDevice())
            {
                fontProviderLink.setSummary(R.string.get_on_google_play);
                downloadLink = "https://play.google.com/store/apps/details?id=moe.shizuku.fontprovider";
            }
            else
            {
                fontProviderLink.setSummary(R.string.download_github);
                downloadLink = "https://github.com/RikkaApps/FontProvider/releases/";
            }
            fontProviderLink.setOnPreferenceClickListener(preference ->
            {
                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(downloadLink)));
                return true;
            });
        }
        else
            getPreferenceScreen().removePreference(fontProviderLink);
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference)
    {
        DialogFragment dialog = null;

        //ref: https://stackoverflow.com/a/53290775/3582371
        if (preference.getClass().equals(NumberPreference.class))
            dialog = NumberPreferenceDialog.newInstance(preference.getKey());
        else if (preference.getClass().equals(ThemeChooser.class))
            dialog = ThemeChooserDialog.newInstance(preference.getKey());

        if (dialog != null)
        {
            dialog.setTargetFragment(this, 0);
            dialog.show(getParentFragmentManager(), null);
        }
        else
            super.onDisplayPreferenceDialog(preference);
    }

    private boolean setSummary(Preference preference, Object newValue)
    {
        int refId;
        if (newValue.equals(getResources().getString(R.string.prefid_on_incorrect_default)))
            refId = R.string.incorrect_option_move_on;
        else if (newValue.equals(getResources().getString(R.string.prefid_on_incorrect_show_answer)))
            refId = R.string.incorrect_option_show_answer;
        else if (newValue.equals(getResources().getString(R.string.prefid_on_incorrect_retry)))
            refId = R.string.incorrect_option_retry;
        else
            return false;
        preference.setSummary(refId);
        return true;
    }
}
