package com.noprestige.kanaquiz.options;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.noprestige.kanaquiz.KanaQuiz;
import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.logs.LogDao;

import moe.shizuku.fontprovider.FontProviderClient;

public class OptionsFragment extends PreferenceFragment
{
    static class DeleteAll extends AsyncTask<Preference, Void, Preference>
    {
        @Override
        protected Preference doInBackground(Preference... btnClearLogs)
        {
            LogDao.deleteAll();
            return btnClearLogs[0];
        }

        @Override
        protected void onPostExecute(Preference btnClearLogs)
        {
            btnClearLogs.setEnabled(false);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference onIncorrect = findPreference(getResources().getString(R.string.prefid_on_incorrect));

        setSummary(onIncorrect, OptionsControl.getString(R.string.prefid_on_incorrect));

        onIncorrect.setOnPreferenceChangeListener(this::setSummary);

        //ref: https://stackoverflow.com/questions/5330677/android-preferences-onclick-event
        findPreference("clear_logs").setOnPreferenceClickListener(btnClearLogs ->
        {
            new DeleteAll().execute(btnClearLogs);
            return true;
        });

        findPreference(getResources().getString(R.string.prefid_selected_theme))
                .setOnPreferenceChangeListener((preference, newValue) ->
                {
                    getActivity().recreate();
                    return true;
                });

        Preference fontProviderLink = findPreference("font_provider_link");

        if (FontProviderClient.checkAvailability(getActivity()) ==
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
