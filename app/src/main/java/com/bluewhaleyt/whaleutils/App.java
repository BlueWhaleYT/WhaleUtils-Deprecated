package com.bluewhaleyt.whaleutils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.bluewhaleyt.common.CommonUtil;

public class App extends Application {

    private static Context context;

    public static String ROOT_DIR = "/WhaleUtils/";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        CommonUtil.setDynamicColorsIfAvailable(this);
    }

    public static Context getContext() {
        return context;
    }

    public static Resources getRes() {
        return context.getResources();
    }

}
