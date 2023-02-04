package com.bluewhaleyt.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

public class PermissionUtil {

    private static String dataDir = "data";
    private static String obbDir = "obb";

    public static final int PERMISSION_REQ_CODE = 100;
    public static final int NEW_DIR_REQ_CODE = 200;

    public static boolean isAlreadyGrantedExternalStorageAccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        return false;
    }

    public static boolean isAlreadyGrantedAndroidDataAccess(Context context) {
        return isAlreadyGrantedAndroidInternalAccess(context, "data");
    }

    public static boolean isAlreadyGrantedAndroidObbAccess(Context context) {
        return isAlreadyGrantedAndroidInternalAccess(context, "obb");
    }

    public static void requestAllFileAccess(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!isAlreadyGrantedExternalStorageAccess()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            }
        } else {
            activity.startActivity(new Intent(activity, activity.getClass()));
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQ_CODE);
        }
    }

    public static void requestDirectoryAccess(Activity activity, Uri uri) {
        request(activity, uri);
    }

    public static void requestAndroidDataAccess(Activity activity) {
        var dir = "data";
        Uri uri = Uri.parse(getDocumentPath() + dir);
        request(activity, uri);
    }

    public static void requestAndroidObbAccess(Activity activity) {
        var dir = "obb";
        Uri uri = Uri.parse(getDocumentPath() + dir);
        request(activity, uri);
    }

    public static void setPermanentAccess(Activity activity, Intent data) {
        Uri uri;
        if (data == null) {
            return;
        }
        if ((uri = data.getData()) != null) {
            activity.getContentResolver().takePersistableUriPermission(uri, data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION));
        }
    }

    private static void request(Activity activity, Uri uri) {
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        i.setAction(Intent.ACTION_OPEN_DOCUMENT_TREE);
        i.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
        activity.startActivityForResult(i, NEW_DIR_REQ_CODE);
    }

    private static String getDocumentPath() {
        return "content://com.android.externalstorage.documents/tree/primary%3AAndroid/document/primary%3AAndroid%2F";
    }

    private static boolean isAlreadyGrantedAndroidInternalAccess(Context context, String dir) {
        for (UriPermission persistedUriPermission: context.getContentResolver().getPersistedUriPermissions()) {
            if (persistedUriPermission.getUri().toString().equals("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2F" + dir)) {
                return true;
            }
        }
        return false;
    }

}
