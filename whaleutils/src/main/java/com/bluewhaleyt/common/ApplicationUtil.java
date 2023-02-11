package com.bluewhaleyt.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.IOException;

public class ApplicationUtil {

    public static String getAppVersionName(Activity activity) throws PackageManager.NameNotFoundException {
        return getPackageInfo(activity).versionName;
    }

    public static int getAppVersionCode(Activity activity) throws PackageManager.NameNotFoundException {
        return getPackageInfo(activity).versionCode;
    }

    private static PackageInfo getPackageInfo(Activity activity) throws PackageManager.NameNotFoundException {
        return activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
    }

    public static void clearApplicationData(Context context) {
        ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
    }

    public static void clearApplicationCache(Context context) throws IOException {
        Runtime.getRuntime().exec("pm clear " + context.getApplicationContext().getPackageName());
    }

}
