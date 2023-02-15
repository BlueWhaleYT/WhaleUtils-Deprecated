package com.bluewhaleyt.common;

public class UrlUtil {

    public static String getFileProtocol() {
        return "file://";
    }

    public static String getFileAssetsPrefix() {
        return getFileProtocol() + "/android_asset";
    }

}
