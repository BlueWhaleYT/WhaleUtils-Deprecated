package com.bluewhaleyt.whaleutils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.debug.ClassExtractUtil;
import com.bluewhaleyt.debug.SystemResourceUtil;
import com.bluewhaleyt.whaleutils.databinding.ActivityMainBinding;
import com.bluewhaleyt.whaleutils.databinding.ActivityUtilsCodeBinding;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class UtilsCodeActivity extends AppCompatActivity {

    private ActivityUtilsCodeBinding binding;

    private String clsPrefix = "com.bluewhaleyt.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityUtilsCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DynamicColorsUtil dynamicColors = new DynamicColorsUtil(this);

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
            SnackbarUtil.makeErrorSnackbar(this, e.toString());
        }
    }

    private void init() throws Exception {

        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);

        var cls = clsPrefix + "common.DynamicColorsUtil";
        ClassExtractUtil classExtract = new ClassExtractUtil(cls);
        binding.lv.setAdapter(new ArrayAdapter<>(this, R.layout.layout_simple_list_item, classExtract.getDeclaredMethods()));
        ((BaseAdapter)binding.lv.getAdapter()).notifyDataSetChanged();

        getSupportActionBar().setSubtitle(cls);


    }

}