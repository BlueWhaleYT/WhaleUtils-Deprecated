package com.bluewhaleyt.component.snackbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;

import com.bluewhaleyt.common.DynamicColorsUtil;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtil {

    private final Snackbar snackbar;
    private static SnackbarUtil snackbarUtil;
    private static DynamicColorsUtil dynamicColors;

    public SnackbarUtil(Activity activity, String text) {
        snackbar = Snackbar.make(activity, activity.findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT);
        snackbar.setText(text);
        snackbar.show();
    }

    public void setText(String text) {
        snackbar.setText(text);
    }

    public void setTextColor(int color) {
        snackbar.setTextColor(color);
    }

    public void setBackgroundColor(int color) {
        snackbar.setBackgroundTint(color);
    }

    public void setView(View v) {
        snackbar.setAnchorView(v);
    }

    public void setDuration(int duration) {
        snackbar.setDuration(duration);
    }

    public void setAction(int text, View.OnClickListener clickListener) {
        if (clickListener == null) clickListener = v -> {};
        snackbar.setAction(text, clickListener);
    }

    public void setAction(String text, View.OnClickListener clickListener) {
        if (clickListener == null) clickListener = v -> {};
        snackbar.setAction(text, clickListener);
    }

    public void setActionTextColor(int color) {
        snackbar.setActionTextColor(color);
    }

    public static void makeSnackbar(Activity activity, String text) {
        snackbarUtil = new SnackbarUtil(activity, text);
        dynamicColors = new DynamicColorsUtil(activity);
    }

    public static void makeErrorSnackbar(Activity activity, String error) {
        makeSnackbar(activity, error);
        snackbarUtil.setBackgroundColor(dynamicColors.getColorErrorContainer());
        snackbarUtil.setTextColor(dynamicColors.getColorOnBackground());
    }

}
