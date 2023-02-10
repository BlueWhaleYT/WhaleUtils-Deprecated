package com.bluewhaleyt.common;

import android.os.Build;

import androidx.annotation.ChecksSdkIntAtLeast;

public class SDKUtil {

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean isAtLeastSDK21() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static boolean isAtLeastSDK22() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
    public static boolean isAtLeastSDK23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
    public static boolean isAtLeastSDK24() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N_MR1)
    public static boolean isAtLeastSDK25() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
    public static boolean isAtLeastSDK26() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O_MR1)
    public static boolean isAtLeastSDK27() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
    public static boolean isAtLeastSDK28() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    public static boolean isAtLeastSDK29() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
    public static boolean isAtLeastSDK30() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    public static boolean isAtLeastSDK31() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S_V2)
    public static boolean isAtLeastSDK32() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
    public static boolean isAtLeastSDK33() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU;
    }

}
