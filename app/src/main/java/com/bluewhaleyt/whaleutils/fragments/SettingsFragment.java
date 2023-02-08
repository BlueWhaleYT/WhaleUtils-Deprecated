package com.bluewhaleyt.whaleutils.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.bluewhaleyt.common.SDKUtil;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.whaleutils.App;
import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.activites.MainActivity;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        init();
    }

    private void init() {

        try {

            var preferenceTheme = findPreference("app_theme");
            var preferenceDynamicColorEnable = findPreference("app_dynamic_color_enable");

            var app = App.getInstance();

            preferenceTheme.setOnPreferenceChangeListener((preference, newValue) -> {
                switch ((String) newValue) {
                        case "dark":
                            app.updateTheme(AppCompatDelegate.MODE_NIGHT_YES, requireActivity());
                            return true;
                        case "light":
                            app.updateTheme(AppCompatDelegate.MODE_NIGHT_NO, requireActivity());
                            return true;
                        case "auto":
                            if (SDKUtil.isAtLeastSDK29()) {
                                app.updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, requireActivity());
                            } else {
                                app.updateTheme(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY, requireActivity());
                            }
                            return true;
                    }
                    return false;
                }
            );

            preferenceDynamicColorEnable.setOnPreferenceChangeListener((preference, newValue) -> {
                App.getInstance().updateDynamicColor(getActivity());
                return true;
            });

        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(getActivity(), e.getMessage(), e.toString());
        }

    }
}