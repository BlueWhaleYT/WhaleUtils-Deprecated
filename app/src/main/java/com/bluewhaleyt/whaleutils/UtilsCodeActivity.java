package com.bluewhaleyt.whaleutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.component.dialog.DialogUtil;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.debug.SystemResourceUtil;
import com.bluewhaleyt.whaleutils.debug.adapters.ColorResListAdapter;
import com.bluewhaleyt.whaleutils.debug.adapters.DrawableResListAdapter;
import com.bluewhaleyt.whaleutils.debug.adapters.StringResListAdapter;
import com.bluewhaleyt.whaleutils.debug.models.ColorResModel;
import com.bluewhaleyt.whaleutils.debug.models.DrawableResModel;
import com.bluewhaleyt.whaleutils.debug.models.StringResModel;
import com.bluewhaleyt.whaleutils.databinding.ActivityUtilsCodeBinding;

import java.util.ArrayList;
import java.util.List;

public class UtilsCodeActivity extends AppCompatActivity {

    private ActivityUtilsCodeBinding binding;

    private String clsPrefix = "com.bluewhaleyt.";
    private String cls;

    private List<StringResModel> listStringRes = new ArrayList<>();
    private StringResModel modelStringRes;
    private StringResListAdapter adapterStringRes =new StringResListAdapter((ArrayList<StringResModel>) listStringRes);

    private List<ColorResModel> listColorRes = new ArrayList<>();
    private ColorResModel modelColorRes;
    private ColorResListAdapter adapterColorRes = new ColorResListAdapter((ArrayList<ColorResModel>) listColorRes);

    private List<DrawableResModel> listDrawableRes = new ArrayList<>();
    private DrawableResModel modelDrawableRes;
    private DrawableResListAdapter adapterDrawableRes = new DrawableResListAdapter((ArrayList<DrawableResModel>) listDrawableRes);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityUtilsCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
            SnackbarUtil.makeErrorSnackbar(this, e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.debug_code_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_available_res:
                showAvailableRes();
                break;
            case R.id.menu_string_res:
                showStringRes();
                break;
            case R.id.menu_color_res:
                showColorRes();
                break;
            case R.id.menu_drawable_res:
                showDrawableRes();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() throws Exception {

        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        cls = clsPrefix + "common.DynamicColorsUtil";
//        ClassExtractUtil classExtract = new ClassExtractUtil(cls);
//        binding.lv.setAdapter(new ArrayAdapter<>(this, R.layout.layout_simple_list_item, classExtract.getDeclaredMethods()));
//        ((BaseAdapter)binding.lv.getAdapter()).notifyDataSetChanged();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvList.setLayoutManager(linearLayoutManager);

        showColorRes();

    }

    private void showAvailableRes() {
//        try {
//            binding.lv.setAdapter(new ArrayAdapter<>(this, R.layout.layout_simple_list_item, SystemResourceUtil.getAvailableResources(this)));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        DialogUtil dialog = new DialogUtil(this, getResources().getString(R.string.debug_available_resources), "");
        try {
            dialog.setMessage(TextUtils.join(", ", SystemResourceUtil.getAvailableResources(this)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dialog.setNegativeButton(android.R.string.cancel, null);
        dialog.build();
    }

    private void showStringRes() {
        clearAllLists();
        binding.rvList.setAdapter(adapterStringRes);
        try {
            var x = SystemResourceUtil.getStringResources(this);
            for (int i = 0; i < x.size(); i++) {
                modelStringRes = new StringResModel();
                modelStringRes.setCount(i+1);
                modelStringRes.setStringRes(x.get(i));
                var temp = SystemResourceUtil.getParsedXMLResource(this, modelStringRes.getStringRes(), "string");
                modelStringRes.setStringPreview(getString(temp));

                listStringRes.add(modelStringRes);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showColorRes() {
        clearAllLists();
        binding.rvList.setAdapter(adapterColorRes);
        try {
            var x = SystemResourceUtil.getColorResources(this);
            for (int i = 0; i < x.size(); i++) {
                modelColorRes = new ColorResModel();
                modelColorRes.setCount(i+1);
                modelColorRes.setColorRes(x.get(i));
                var temp = SystemResourceUtil.getParsedXMLResource(this, modelColorRes.getColorRes(), "color");
                modelColorRes.setColorHex(getString(temp));

                listColorRes.add(modelColorRes);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showDrawableRes() {
        clearAllLists();
        binding.rvList.setAdapter(adapterDrawableRes);
        try {
            var x = SystemResourceUtil.getDrawableResources(this);
            for (int i = 0; i < x.size(); i++) {
                modelDrawableRes = new DrawableResModel();
                modelDrawableRes.setCount(i+1);
                modelDrawableRes.setDrawableRes(x.get(i));
                var temp = SystemResourceUtil.getParsedXMLResource(this, modelDrawableRes.getDrawableRes(), "drawable");
                modelDrawableRes.setDrawablePreview(temp);

                listDrawableRes.add(modelDrawableRes);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearAllLists() {
        listStringRes.clear();
        listColorRes.clear();
        listDrawableRes.clear();
    }
}