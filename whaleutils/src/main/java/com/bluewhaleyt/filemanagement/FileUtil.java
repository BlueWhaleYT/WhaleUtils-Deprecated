package com.bluewhaleyt.filemanagement;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.bluewhaleyt.common.SDKUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class FileUtil {

    private static FileFilter fileFilter;

    public static boolean isFileExist(String path) {
        return new File(path).exists();
    }

    public static boolean isFile(String path) {
        if (!isFileExist(path)) return false;
        return new File(path).isFile();
    }

    public static boolean isDirectory(String path) {
        if (!isFileExist(path)) return false;
        return new File(path).isDirectory();
    }

    public static boolean isDirectoryEmpty(String path) {
        try {
            File file = new File(path);
            File[] content = file.listFiles();

            return content.length == 0;
        } catch (Exception e) {
            // ignore
        }
        return false;
    }

    public static boolean isFileHidden(String path) {
        return new File(path).isHidden();
    }

    public static String getParentDirectoryOfPath(String path) {
        return path.substring(0, path.lastIndexOf("/"));
    }

//    public static String getFileNameOfPath(String path) {
//        return path.substring(path.lastIndexOf("/") + 1);
//    }

    public static String getFileNameOfPath(String path) {
        return Uri.parse(path).getLastPathSegment();
    }

    public static double getFileSizeOfPath(String path) {
        return new File(path).length();
    }

    public static int getFileAmountOfPath(String path) {
        File dir;
        try {
            if (!isDirectoryEmpty(path)) {
                dir = new File(path);
                return dir.listFiles().length;
            }
        } catch (Exception e) {
            // ignore
        }
        return 0;
    }

    public static int getFileAmount(List listMap) {
        return listMap.size();
    }

    public static String getInternalStoragePath() {
        return Environment.getDataDirectory().getPath();
    }

    public static String getExternalStoragePath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static FileTime getFileCreationTime(String path) throws IOException {
        BasicFileAttributes attr = null;
        if (SDKUtil.isAtLeastSDK26()) {
            attr = Files.readAttributes(Paths.get(path), BasicFileAttributes.class);
            return attr.creationTime();
        }
        return null;
    }

    public static FileTime getFileLastModifiedTime(String path) throws IOException {
        BasicFileAttributes attr = null;
        if (SDKUtil.isAtLeastSDK26()) {
            attr = Files.readAttributes(Paths.get(path), BasicFileAttributes.class);
            return attr.lastModifiedTime();
        }
        return null;
    }

    public static String getFileLastModifiedTimeFormatString(String path, String format) {
        Date lastModified = new Date(new File(path).lastModified());
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(lastModified);
    }

    public static FileTime getFileLastAccessTime(String path) throws IOException {
        BasicFileAttributes attr = null;
        if (SDKUtil.isAtLeastSDK26()) {
            attr = Files.readAttributes(Paths.get(path), BasicFileAttributes.class);
            return attr.lastAccessTime();
        }
        return null;
    }

    private static void createNewFile(String path) {
        int lastSep = path.lastIndexOf(File.separator);
        if (lastSep > 0) {
            String dirPath = path.substring(0, lastSep);
            makeDirectory(dirPath);
        }
        File file = new File(path);
        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String path, String str) {
        createNewFile(path);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path, false);
            fileWriter.write(str);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public static void makeDirectory(String path) {
        if (!isFileExist(path)) {
            File file = new File(path);
            file.mkdirs();
        }
    }

    public static void listDirectories(String path, List list) {
        fileFilter = pathname -> true;
        listDir(path, list, fileFilter);
    }

    public static void listNonHiddenDirectories(String path, List list) {
        fileFilter = pathname -> {
            if (pathname.isHidden()) {
                return false;
            } else {
                return true;
            }
        };
        listDir(path, list, fileFilter);
    }

    public static void listOnlyHiddenFiles(String path, List list) {
        fileFilter = pathname -> {
            if (pathname.isHidden()) {
                return true;
            } else {
                return false;
            }
        };
        listDir(path, list, fileFilter);
    }

    public static void listOnlyFiles(String path, List list) {
        fileFilter = pathname -> {
            if (pathname.isFile()) {
                return true;
            } else {
                return false;
            }
        };
        listDir(path, list, fileFilter);
    }

    private static void listDir(String path, List list, FileFilter fileFilter) {
        File dir = new File(path);
        if (!dir.exists() || dir.isFile()) return;

        File[] listFiles = dir.listFiles(fileFilter);
        if (listFiles == null || listFiles.length <= 0) return;

        if (list == null) return;
        list.clear();

        for (File file : listFiles) {
            list.add(file.getAbsolutePath());
        }
    }

}
