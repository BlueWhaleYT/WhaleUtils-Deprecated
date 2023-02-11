package com.bluewhaleyt.whaleutils.fragments.preferences;

import android.os.Bundle;

import androidx.preference.Preference;

import com.bluewhaleyt.common.ApplicationUtil;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.component.preferences.CustomPreferenceFragment;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.debug.Constants;
import com.bluewhaleyt.network.NetworkUtil;
import com.bluewhaleyt.whaleutils.R;

public class AboutFragment extends CustomPreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_about, rootKey);
        init();
    }

    private void init() {
        try {

            var componentBtnAboutGithub = findPreference("component_btn_about_github");
            var componentBtnAboutVersion = findPreference("component_btn_about_version");

            componentBtnAboutVersion.setSummary(ApplicationUtil.getAppVersionName(requireActivity()));

            intentGithub(componentBtnAboutGithub);

        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(getActivity(), e.getMessage(), e.toString());
        }

    }

    private void intentGithub(Preference pref) {
        pref.setOnPreferenceClickListener(preference -> {
            if (NetworkUtil.isNetworkAvailable(requireActivity())) {
                IntentUtil.intentURL(requireActivity(), Constants.PROJECT_GITHUB_REPOSITORY_URL);
            } else {
                SnackbarUtil.makeErrorSnackbar(requireActivity(), "Network is not yet connected.");
            }
            return true;
        });
    }

}