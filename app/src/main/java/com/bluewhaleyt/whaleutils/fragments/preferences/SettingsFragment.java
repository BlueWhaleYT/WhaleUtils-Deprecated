package com.bluewhaleyt.whaleutils.fragments.preferences;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.bluewhaleyt.common.IntentUtil;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDivider(new ColorDrawable(Color.TRANSPARENT));
        setDividerHeight(0);
    }
}