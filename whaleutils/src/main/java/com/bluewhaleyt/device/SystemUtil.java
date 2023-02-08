package com.bluewhaleyt.device;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class SystemUtil {

    /* ==== [PROCESSOR] ==== */

    public static boolean is64Bit() {
        return Build.SUPPORTED_64_BIT_ABIS.length > 0;
    }

    public static String getCPUArchitecture() {
        return Build.CPU_ABI.toUpperCase();
    }

    public static String getBoard() {
        return Build.BOARD;
    }

    public static String getChipset() {
        return Build.BOARD.toUpperCase();
    }

    public static int getCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static double getCoreUsage(double core) {
        return getCoreFrequency(core);
    }

    public static String getInstructionSets() {
        return Build.CPU_ABI;
    }

    public static String getCPUFeatures() throws IOException {
        return getFieldFromCPUinfo("Features");
    }

    public static String getCPUGovernor() throws IOException {
        StringBuffer sb = new StringBuffer();
        String file = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
        if (new File(file).exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File(file)));
                String aLine;
                while ((aLine = br.readLine()) != null)
                    sb.append(aLine + "\n");

                if (br != null)
                    br.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String[] getCPUABI() {
        return Build.SUPPORTED_ABIS;
    }

    public static int getCPUBit() {
        return is64Bit() ? 64 : 32;
    }

    public static String getKernalVersion() {
        return System.getProperty("os.version");
    }

    public static String getKernelArchitecture() {
        return System.getProperty("os.arch");
    }

    public static String getOpenGLRenderer() {
        return GPUInfoUtil.glRenderer;
    }

    public static String getOpenGLVendor() {
        return GPUInfoUtil.glVendor;
    }

    public static String getOpenGLVersion() {
        return GPUInfoUtil.glVersion;
    }

//    public static String getOpenGLSimpleVersion() {
//        var str = getOpenGLVersion().substring(0, getOpenGLVersion().lastIndexOf("("));
//        str = getOpenGLVersion().substring(0, getOpenGLVersion().lastIndexOf(""));
//        return str;
//    }

    public static String getOpenGLExtensions() {
        return GPUInfoUtil.glExtensions;
    }

    public static void clearApplicationDataCache(Activity activity) throws IOException {
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            ((ActivityManager)activity.getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
        } else {
            Runtime.getRuntime().exec("pm clear " + activity.getApplicationContext().getPackageName());
        }
    }

    private static double getCoreFrequency(double currentFreq) {
        try {
            RandomAccessFile readerCurFreq;
            readerCurFreq = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "r");
            String curfreg = readerCurFreq.readLine();
            currentFreq = Double.parseDouble(curfreg) / 1000;
            readerCurFreq.close();
            return currentFreq;
        } catch (IOException ex) {
            ex.printStackTrace();
            return currentFreq;
        }
    }

    private static String getFieldFromCPUinfo(String field) throws IOException {
        return getFieldFromFile("/proc/cpuinfo", field);
    }

    private static String getFieldFromFile(String file, String field) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        Pattern p = Pattern.compile(field + "\\s*:\\s*(.*)");
        try {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher m = p.matcher(line);
                if (m.matches()) {
                    return m.group(1);
                }
            }
        } finally {
            br.close();
        }
        return null;
    }

}
