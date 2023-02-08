package com.bluewhaleyt.common;

import android.os.Build;

import androidx.annotation.ChecksSdkIntAtLeast;

public class SDKUtil {

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
    public static boolean isAtLeastSDK24() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
    public static boolean isAtLeastSDK26() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    public static boolean isAtLeastSDK29() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    public static boolean isAtLeastSDK31() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }

}
