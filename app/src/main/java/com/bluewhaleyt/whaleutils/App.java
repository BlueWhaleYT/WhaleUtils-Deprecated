package com.bluewhaleyt.whaleutils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.bluewhaleyt.WhaleUtilsApplication;
import com.bluewhaleyt.commonutil.CommonUtil;

public class App extends Application {

    private static Context context;

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
