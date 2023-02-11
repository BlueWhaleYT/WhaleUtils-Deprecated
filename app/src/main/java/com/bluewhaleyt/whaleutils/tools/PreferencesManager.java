package com.bluewhaleyt.whaleutils.tools;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.whaleutils.App;

public class PreferencesManager {

    public static String getAppTheme() {
        return getPrefs().getString("app_theme", "auto");
    }

    public static boolean isAppDynamicColorEnable() {
        return getPrefs().getBoolean("app_dynamic_color_enable", DynamicColorsUtil.isDynamicColorAvailable());
    }

    public static SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(App.getContext());
    }
}
