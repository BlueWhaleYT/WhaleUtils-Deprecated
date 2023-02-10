package com.bluewhaleyt.whaleutils.activites.components;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.common.SDKUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.whaleutils.App;
import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.tools.PreferencesManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class WhaleUtilsActivity extends AppCompatActivity {

    public App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        app = App.getInstance();
        init();
    }

    @Override
    public void finish() {
        super.finish();
        IntentUtil.finishTransition(this);
    }

    private void init() {

        switch (PreferencesManager.getAppTheme()) {
            case "auto":
                if (SDKUtil.isAtLeastSDK29()) {
                    App.getInstance().updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                } else {
                    App.getInstance().updateTheme(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }
                break;
            case "light":
                App.getInstance().updateTheme(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                App.getInstance().updateTheme(AppCompatDelegate.MODE_NIGHT_YES);
                break;

        }

    }

}
