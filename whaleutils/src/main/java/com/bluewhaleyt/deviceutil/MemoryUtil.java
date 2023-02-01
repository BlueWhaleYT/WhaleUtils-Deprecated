package com.bluewhaleyt.deviceutil;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;

import com.bluewhaleyt.unitutil.UnitUtil;

import java.io.File;


public class MemoryUtil {

    public static long getTotalRAM(Context context) {
        return getMemoryInfo(context).totalMem;
    }

    public static long getAvailableRAM(Context context) {
        return getMemoryInfo(context).availMem;
    }

    public static long getJavaHeap() {
        return Runtime.getRuntime().maxMemory();
    }

    public static long getInternalStorageTotalMemory() {
        var stat = new StatFs(Environment.getDataDirectory().getPath());
        var totalBlocks = stat.getBlockCountLong();
        var blockSize = stat.getBlockSizeLong();
        return totalBlocks * blockSize;
    }

    public static long getInternalStorageAvailableMemory() {
        var stat = new StatFs(Environment.getDataDirectory().getPath());
        var availableBlocks = stat.getAvailableBlocksLong();
        var blockSize = stat.getBlockSizeLong();
        return availableBlocks * blockSize;
    }

    public static long getExternalStorageTotalMemory() {
        var stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        var totalBlocks = stat.getBlockCountLong();
        var blockSize = stat.getBlockSizeLong();
        return totalBlocks * blockSize;
    }

    public static long getExternalStorageAvailableMemory() {
        var stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        var availableBlocks = stat.getAvailableBlocksLong();
        var blockSize = stat.getBlockSizeLong();
        return availableBlocks * blockSize;
    }

    public static boolean isUSBHostSupported(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_USB_HOST);
    }

    private static ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        assert actManager != null;
        actManager.getMemoryInfo(memInfo);
        return memInfo;
    }

}
