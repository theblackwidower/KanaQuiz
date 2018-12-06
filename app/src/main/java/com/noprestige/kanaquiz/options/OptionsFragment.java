package com.noprestige.kanaquiz.options;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.logs.LogDao;

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
                    //TODO: Get to work on pre-Marshmallow
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        ((Activity) getContext()).recreate();
                    return true;
                });
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
