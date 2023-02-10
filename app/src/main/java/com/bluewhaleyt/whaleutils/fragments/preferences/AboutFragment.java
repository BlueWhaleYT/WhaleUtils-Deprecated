package com.bluewhaleyt.whaleutils.fragments.preferences;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.bluewhaleyt.common.ApplicationUtil;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.common.SDKUtil;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.debug.Constants;
import com.bluewhaleyt.network.NetworkUtil;
import com.bluewhaleyt.whaleutils.App;
import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.activites.MainActivity;

import java.util.Objects;

public class AboutFragment extends PreferenceFragmentCompat {
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDivider(new ColorDrawable(Color.TRANSPARENT));
        setDividerHeight(0);
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