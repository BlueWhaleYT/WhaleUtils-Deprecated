package com.bluewhaleyt.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.documentfile.provider.DocumentFile;

import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.filemanagement.SAFUtil;

import java.io.FileNotFoundException;

public class PermissionUtil {

    public static final String PERMISSION_SAF = "permissionSAF";

    private static SharedPreferences sharedPrefs;

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
        return isAlreadyGrantedAndroidInternalAccess(context, dataDir);
    }

    public static boolean isAlreadyGrantedAndroidObbAccess(Context context) {
        return isAlreadyGrantedAndroidInternalAccess(context, obbDir);
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
        Uri uri = Uri.parse(getDocumentPath() + dataDir);
        request(activity, uri);
    }

    public static void requestAndroidObbAccess(Activity activity) {
        Uri uri = Uri.parse(getDocumentPath() + obbDir);
        request(activity, uri);
    }

    public static void setPermanentAccess(Activity activity, Intent data) {
        Uri uri;
        if (data == null) {
            return;
        }
        if ((uri = data.getData()) != null) {
            final int takeFlags = data.getFlags() & getPermissionFlagOfReadWriteUri();
            activity.getContentResolver().takePersistableUriPermission(uri, takeFlags);
        }
    }

    public static void openDocumentTree(Activity activity, int resultCode, Intent data) throws FileNotFoundException {

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                var uri = data.getData();
                if (!Uri.decode(uri.toString()).endsWith(":")) {
                    final int takeFlags = data.getFlags() & getPermissionFlagOfReadWriteUri();
                    activity.getContentResolver().takePersistableUriPermission(uri, takeFlags);

                    sharedPrefs = activity.getSharedPreferences(PERMISSION_SAF, Context.MODE_PRIVATE);
                    sharedPrefs.edit().putString(SAFUtil.DIRECTORY_URI, uri.toString()).commit();

                    var file1 = DocumentFile.fromTreeUri(activity, uri);
                    var file2 = file1.createFile("*/*", "test.file");
                    var file2Uri = file2.getUri();

                    sharedPrefs.edit().putString(SAFUtil.DIRECT_DIRECTORY_URI, file2Uri.toString().substring((int) (0), (int) (file2Uri.toString().length() - 9))).commit();

                    DocumentsContract.deleteDocument(activity.getContentResolver(), file2Uri);

                }

            }

        }


    }

    public static int getPermissionFlagOfReadUri() {
        return Intent.FLAG_GRANT_READ_URI_PERMISSION;
    }

    public static int getPermissionFlagOfWriteUri() {
        return Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
    }

    public static int getPermissionFlagOfReadWriteUri() {
        return Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void request(Activity activity, Uri uri) {
        Intent i = new Intent();
        i.addFlags(getPermissionFlagOfReadWriteUri());
        i.setAction(Intent.ACTION_OPEN_DOCUMENT_TREE);
        i.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
        activity.startActivityForResult(i, NEW_DIR_REQ_CODE);
    }

    private static String getDocumentPath() {
        return "content://com.android.externalstorage.documents/tree/primary%3AAndroid/document/primary%3AAndroid%2F";
    }

    private static boolean isAlreadyGrantedAndroidInternalAccess(Context context, String dir) {
        for (UriPermission persistedUriPermission : context.getContentResolver().getPersistedUriPermissions()) {
            if (persistedUriPermission.getUri().toString().equals("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2F" + dir)) {
                return true;
            }
        }
        return false;
    }

}
