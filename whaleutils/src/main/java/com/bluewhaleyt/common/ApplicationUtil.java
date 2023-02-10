package com.bluewhaleyt.common;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

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

}
