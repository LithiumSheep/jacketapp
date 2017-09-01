package com.lithiumsheep.jacketapp.ui.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.lithiumsheep.jacketapp.R;


public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new JacketPreferenceFragment())
                .commit();
    }

    public static class JacketPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}
