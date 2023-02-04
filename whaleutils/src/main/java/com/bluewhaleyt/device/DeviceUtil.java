package com.bluewhaleyt.device;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

public class DeviceUtil {

    private static Context context;

    /* ==== [DEVICE] ==== */

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getModelProduct() {
        return Build.PRODUCT;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getBasebandVersion() {
        return Build.getRadioVersion();
    }

    public static String getRILVersion() {
        return "";
    }

    public static String getBuildNumber() {
        return Build.DISPLAY;
    }

    public static String getBuildFingerprint() {
        return Build.FINGERPRINT;
    }

    public static String getBootLoader() {
        return Build.BOOTLOADER;
    }

    public static String getJavaVMName() {
        return System.getProperty("java.vm.name");
    }

    public static String getJavaVMVersion() {
        return System.getProperty("java.vm.version");
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /* ==== [DISPLAY] ==== */

    public static int getResolutionWidth(Activity activity) {
        var display = activity.getWindowManager().getDefaultDisplay();
        var size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getResolutionHeight(Activity activity) {
        var display = activity.getWindowManager().getDefaultDisplay();
        var size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static float getSoftwareDensity(Activity activity) {
        return activity.getResources().getDisplayMetrics().density;
    }

    public static float getSoftwareDensityDPI(Activity activity) {
        return activity.getResources().getDisplayMetrics().densityDpi;
    }

    public static float getRefreshRate(Activity activity) {
        Display display = ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getRefreshRate();
    }

}