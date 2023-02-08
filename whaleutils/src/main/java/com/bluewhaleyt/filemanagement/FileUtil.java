package com.bluewhaleyt.filemanagement;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.bluewhaleyt.common.SDKUtil;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileUtil {

    private static final String TAG = FileUtil.class.getName();

    private static File[] listFiles;
    private static FileFilter fileFilter;

    private static Handler handler = new Handler();
    static Runnable runnable;

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

    public static boolean isFileReadable(String path) {
        return new File(path).canRead();
    }

    public static boolean isFileWritable(String path) {
        return new File(path).canWrite();
    }

    public static boolean isFileExecutable(String path) {
        return new File(path).canExecute();
    }

    public static void setFileReadable(String path) {
        new File(path).setReadable(true);
    }

    public static void setFileWritable(String path) {
        new File(path).setWritable(true);
    }

    public static void setFileExecutable(String path) {
        new File(path).setExecutable(true);
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

    public static String getFileExtensionOfPath(String path) {
        String ext = "";
        path = getFileNameOfPath(path);
        if (path != null && !path.isEmpty()) {
            int dot = path.indexOf('.');
            if ((dot >= 0) && (dot < path.length() - 1)) {
                ext = path.substring(dot + 1);
            }
        }
        return ext;
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

    public static int getOnlyFileAmountOfPath(String path) {
        File dir;
        fileFilter = pathname -> {
            if (pathname.isFile()) {
                return true;
            } else {
                return false;
            }
        };
        try {
            if (!isDirectoryEmpty(path)) {
                dir = new File(path);
                return dir.listFiles(fileFilter).length;
            }
        } catch (Exception e) {
            // ignore
        }
        return 0;
    }

    public static int getOnlyDirectoryAmountOfPath(String path) {
        File dir;
        fileFilter = pathname -> {
            if (pathname.isFile()) {
                return false;
            } else {
                return true;
            }
        };
        try {
            if (!isDirectoryEmpty(path)) {
                dir = new File(path);
                return dir.listFiles(fileFilter).length;
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

    public static String getAndroidDataDirPath() {
        return getExternalStoragePath() + "/Android/data";
    }

    public static String getAndroidObbDirPath() {
        return getExternalStoragePath() + "/Android/obb";
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

    public static String readFile(String path) {
        createNewFile(path);
        StringBuilder sb = new StringBuilder();
        FileReader fr = null;
        try {
            fr = new FileReader(new File(path));
            char[] buff = new char[1024];
            int length = 0;
            while ((length = fr.read(buff)) > 0) {
                sb.append(new String(buff, 0, length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
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

    public static void listOnlyFilesSubDirFiles(String path, List<String> list) {
//        fileFilter = File::isFile;
//        listDir(path, list, fileFilter);
        listDirAllFiles(path, list);
    }

    public static void refreshList(List<String> list) {
        list.clear();
    }

    public static String getFullFileInfo(String path) throws IOException {

        var fileType = isDirectory(path) ? "directory" : "file";

        return
                "File name: " + getFileNameOfPath(path) +
                        "\nFile path: " + path +
                        "\nFile type: " + fileType +
                        "\nFile permission: " + "read: " + isFileReadable(path) + " | write: " + isFileWritable(path) + " | execute: " + isFileExecutable(path) +
                        "\nCreation date: " + getFileCreationTime(path) +
                        "\nLast modified date: " + getFileLastModifiedTime(path);
    }

    private static void listDir(String path, List<String> list, FileFilter fileFilter) {
        list(path, list, fileFilter);
        for (File file : listFiles) {
            list.add(file.getAbsolutePath());
        }
    }

    private static void listDirAllFiles(String path, List<String> list) {
//        list(path, list, null);

        File dir = new File(path);
        if (!dir.exists() || dir.isFile()) return;

        if (fileFilter != null) {
            listFiles = dir.listFiles(fileFilter);
        } else {
            listFiles = dir.listFiles();
        }

        if (listFiles == null || listFiles.length <= 0) return;

        if (list == null) return;

        for (File file : listFiles) {
            if (file.isDirectory()) {
                listDirAllFiles(file.getPath(), list);
                Log.e(TAG, "directory: " + file.getPath());
            } else {
                list.add(file.getPath());
                Log.w(TAG, "file: " + file.getPath());
            }
        }

    }

    private static void list(String path, List<String> list, FileFilter fileFilter) {
        File dir = new File(path);
        if (!dir.exists() || dir.isFile()) return;

        if (fileFilter != null) {
            listFiles = dir.listFiles(fileFilter);
        } else {
            listFiles = dir.listFiles();
        }

        if (listFiles == null || listFiles.length <= 0) return;

        if (list == null) return;

    }

}
