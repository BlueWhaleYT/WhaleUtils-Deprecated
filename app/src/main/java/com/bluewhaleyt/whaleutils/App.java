package com.bluewhaleyt.whaleutils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.whaleutils.tools.PreferencesManager;
import com.google.android.material.color.DynamicColors;
import com.jakewharton.processphoenix.ProcessPhoenix;

public class App extends Application {

    private static Context context;
    private static App instance;

    public static final String ROOT_DIR = "/WhaleUtils/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        init();
    }

    private void init() {
        updateDynamicColor();
    }

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public static Resources getRes() {
        return context.getResources();
    }

    public void updateTheme(int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }

    public void updateTheme(int nightMode, AppCompatActivity activity, Fragment fragment) throws IllegalAccessException, InstantiationException {
        updateTheme(nightMode);
        activity.getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment.getClass().newInstance()).commit();
    }

    public void updateDynamicColor() {
        if (PreferencesManager.isAppDynamicColorEnable()) {
            DynamicColorsUtil.setDynamicColorsIfAvailable(this);
        }
    }

    public void updateDynamicColor(Activity activity) {
        if (PreferencesManager.isAppDynamicColorEnable()) {
            updateDynamicColor();
        }
        ProcessPhoenix.triggerRebirth(activity);
    }

}
