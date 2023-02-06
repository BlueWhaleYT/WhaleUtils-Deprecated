package com.bluewhaleyt.common;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;

import com.bluewhaleyt.filemanagement.FileUtil;

public class IntentUtil {

    private static Intent intent;

    public static void intent(Activity activity, Class<?> targetActivity) {
        activity.startActivity(new Intent(activity, targetActivity));
    }

    public static void intentApplicationInfoScreen(Activity activity) {
        intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }

    public static void intentInternalStorageScreen(Activity activity) {
        intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
//        activity.startActivityForResult(intent, 0);
        activity.startActivity(intent);
    }

    public static void intentDirectorySelectScreen(Activity activity) {
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        activity.startActivity(intent);
    }

}
