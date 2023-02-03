package com.bluewhaleyt.filemanagement;

import android.os.Environment;

import java.io.File;

public class FileUtil {

    public static boolean isFileExist(String path) {
        return new File(path).exists();
    }

    public static String getFileNameOfPath(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static String getInternalStoragePath() {
        return Environment.getDataDirectory().getPath();
    }

    public static String getExternalStoragePath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static void deleteFile(String path) {
        File file = new File(path);

        if (!isFileExist(path)) return;

        if (file.isFile()) {
            file.delete();
            return;
        }

        File[] fileArr = file.listFiles();

        if (fileArr != null) {
            for (File subFile : fileArr) {
                if (subFile.isDirectory()) {
                    deleteFile(subFile.getAbsolutePath());
                }

                if (subFile.isFile()) {
                    subFile.delete();
                }
            }
        }

        file.delete();

    }

}
