package com.bluewhaleyt.whaleutils.fragments.preferences;

import android.os.Bundle;

import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.component.preferences.CustomPreferenceFragment;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.whaleutils.R;

public class SettingsFragment extends CustomPreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        init();
    }

    private void init() {
        try {

            var componentBtnDisplay = findPreference("component_btn_display");
            var componentBtnAbout = findPreference("component_btn_about");

            componentBtnDisplay.setOnPreferenceClickListener(preference -> {
                IntentUtil.intentFragment(requireActivity(), new DisplayFragment());
                return true;
            });

            componentBtnAbout.setOnPreferenceClickListener(preference -> {
                IntentUtil.intentFragment(requireActivity(), new AboutFragment());
                return true;
            });

        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(getActivity(), e.getMessage(), e.toString());
        }

    }
}