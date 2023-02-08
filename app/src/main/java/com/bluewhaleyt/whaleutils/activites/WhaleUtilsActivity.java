package com.bluewhaleyt.whaleutils.activites;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bluewhaleyt.common.SDKUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.whaleutils.App;
import com.bluewhaleyt.whaleutils.tools.PreferencesManager;

public class WhaleUtilsActivity extends AppCompatActivity {

    public App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        app = App.getInstance();
        init();
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
