package com.gdg.jamescha.gdgwerewolf;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.gdg.jamescha.gdgwerewolf.data.WerewolfContract;
import com.gdg.jamescha.gdgwerewolf.sync.WerewolfSyncAdapter;


public class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    boolean mBindingPrefernce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

       // bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_region_key)));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        mBindingPrefernce = true;

        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(),""));

        mBindingPrefernce = false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();

        if ( !mBindingPrefernce) {
            if (preference.getKey().equals(R.string.pref_region_key)) {
                WerewolfSyncAdapter.syncImmediately(this);
            } else {
                getContentResolver().notifyChange(WerewolfContract.WhoEntry.CONTENT_URI, null);
            }
        }

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if(prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(stringValue);
        }
        return true;
    }
}
