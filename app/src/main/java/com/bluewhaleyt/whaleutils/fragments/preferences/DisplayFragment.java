package com.bluewhaleyt.whaleutils.fragments.preferences;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.SwitchPreferenceCompat;

import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.common.SDKUtil;
import com.bluewhaleyt.component.preferences.CustomPreferenceFragment;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.whaleutils.App;
import com.bluewhaleyt.whaleutils.R;

public class DisplayFragment extends CustomPreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_display, rootKey);
        init();
    }

    private void init() {

        try {

            var app = App.getInstance();

            var preferenceTheme = findPreference("app_theme");
            SwitchPreferenceCompat preferenceDynamicColorEnable = findPreference("app_dynamic_color_enable");

            var componentBtnDisplayLanguage = findPreference("component_btn_display_language");

            if (!SDKUtil.isAtLeastSDK31()) {
                preferenceDynamicColorEnable.setEnabled(false);
            }

            preferenceDynamicColorEnable.setChecked(DynamicColorsUtil.isDynamicColorAvailableButNotApplied());

            preferenceTheme.setOnPreferenceChangeListener((preference, newValue) -> {
                try {
                    switch ((String) newValue) {
                        case "dark":
                            app.updateTheme(AppCompatDelegate.MODE_NIGHT_YES, (AppCompatActivity) requireActivity(), this);
                            return true;
                        case "light":
                            app.updateTheme(AppCompatDelegate.MODE_NIGHT_NO, (AppCompatActivity) requireActivity(), this);
                            return true;
                        case "auto":
                            if (SDKUtil.isAtLeastSDK29()) {
                                app.updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, (AppCompatActivity) requireActivity(), this);
                            } else {
                                app.updateTheme(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY, (AppCompatActivity) requireActivity(), this);
                            }
                            return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            });

            preferenceDynamicColorEnable.setOnPreferenceChangeListener((preference, newValue) -> {
                App.getInstance().updateDynamicColor(getActivity());
                return true;
            });

            componentBtnDisplayLanguage.setOnPreferenceClickListener(preference -> {
                IntentUtil.intentFragment(requireActivity(), new LanguageFragment());
                return true;
            });

        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(getActivity(), e.getMessage(), e.toString());
        }

    }
}