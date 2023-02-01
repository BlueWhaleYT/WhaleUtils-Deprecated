package com.bluewhaleyt;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class WhaleUtilsApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        try {
            return context;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Resources getRes() {
        try {
            return context.getResources();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
