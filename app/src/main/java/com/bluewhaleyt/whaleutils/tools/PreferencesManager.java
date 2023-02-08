package com.bluewhaleyt.whaleutils.tools;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.bluewhaleyt.whaleutils.App;

public class PreferencesManager {

    public static String getAppTheme() {
        return getPrefs().getString("app_theme", "3");
    }

    public static SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(App.getContext());
    }
}
