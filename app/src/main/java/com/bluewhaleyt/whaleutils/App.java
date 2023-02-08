package com.bluewhaleyt.whaleutils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatDelegate;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.DynamicColorsUtil;

public class App extends Application {

    private static Context context;
    private static App instance;

    public static final String ROOT_DIR = "/WhaleUtils/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        DynamicColorsUtil.setDynamicColorsIfAvailable(this);
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

    public void updateTheme(int nightMode, Activity activity) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        activity.recreate();
    }

}
