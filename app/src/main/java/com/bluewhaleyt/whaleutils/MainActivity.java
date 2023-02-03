package com.bluewhaleyt.whaleutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.PermissionUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.device.DeviceUtil;
import com.bluewhaleyt.device.GPUInfoUtil;
import com.bluewhaleyt.device.SystemUtil;
import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.network.NetworkUtil;
import com.bluewhaleyt.whaleutils.adapters.ViewPagerAdapter;
import com.bluewhaleyt.whaleutils.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewPagerAdapter viewPagerAdapter;

    private StringBuilder text = new StringBuilder();
    private String lnBreak = "\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_device_info:
                showDeviceInfoDialog();
                break;
            case R.id.menu_system_info:
                showSystemInfoDialog();
                break;
            case R.id.menu_delete_root_dir:
                showDeleteRootDirDialog();
                break;
            case R.id.menu_all_file_access:
                requestAllFileAccess();
                break;
            case R.id.menu_debug:
                startActivity(new Intent(this, UtilsCodeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {

        getSupportActionBar().setSubtitle("made by BlueWhaleYT");

        checkInternetConnection();

        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);

        GPUInfoUtil.set(binding.glSurfaceView);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

    }

    private void requestAllFileAccess() {
        PermissionUtil.requestAllFileAccess(this);
    }

    private void checkInternetConnection() {
        if (!NetworkUtil.isNetworkAvailable(this)) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("No Internet")
                    .setMessage("Please connect to the Internet for better experiences.")
                    .setNegativeButton(android.R.string.cancel, null)
                    .create().show();
        }
    }

    private void showDeleteRootDirDialog() {
        var path = FileUtil.getExternalStoragePath() + App.ROOT_DIR;
        new MaterialAlertDialogBuilder(this)
                .setTitle("Delete external root directory")
                .setMessage("Are you sure you want to delete " + path + " directory? This action can't be restored.")
                .setPositiveButton(android.R.string.ok, (d, i) -> FileUtil.deleteFile(path))
                .setNegativeButton(android.R.string.cancel, null)
                .create().show();
    }

    private void showDeviceInfoDialog() {
        text.setLength(0);
        text.append(App.getRes().getString(R.string.device_info_category_display) + lnBreak);
        text.append(App.getRes().getString(R.string.device_info_model) + ": " + DeviceUtil.getModel() + " (" + DeviceUtil.getModelProduct() + ")" + lnBreak);
        text.append(App.getRes().getString(R.string.device_info_manufacturer) + ": " + DeviceUtil.getManufacturer() + lnBreak);
        text.append(App.getRes().getString(R.string.device_info_baseband_version) + ":" + DeviceUtil.getBasebandVersion() + lnBreak);
        text.append(App.getRes().getString(R.string.device_info_build_number) + ": " + DeviceUtil.getBuildNumber() + lnBreak);
        text.append(App.getRes().getString(R.string.device_info_build_fingerprint) + ": " + DeviceUtil.getBuildFingerprint() + lnBreak);
        text.append(App.getRes().getString(R.string.device_info_bootloader) + ": " + DeviceUtil.getBootLoader() + lnBreak);
        text.append(App.getRes().getString(R.string.device_info_java_vm) + ": " + DeviceUtil.getJavaVMName() + " " + DeviceUtil.getJavaVMVersion() + lnBreak);
        text.append(App.getRes().getString(R.string.device_info_os_version) + ": " + DeviceUtil.getOSVersion() + lnBreak);
        text.append(App.getRes().getString(R.string.device_info_sdk_version) + ": " + DeviceUtil.getSDKVersion());

        new MaterialAlertDialogBuilder(this)
                .setTitle(App.getRes().getString(R.string.device_info_title))
                .setMessage(text)
                .setNegativeButton(android.R.string.cancel, null)
                .create().show();

    }

    private void showSystemInfoDialog() {
        text.setLength(0);
        try {
            text.append(App.getRes().getString(R.string.system_info_category_processor) + lnBreak);
            text.append(App.getRes().getString(R.string.system_info_cpu_arch) + ": " + SystemUtil.getCPUArchitecture() + lnBreak);
            text.append(App.getRes().getString(R.string.system_info_board) + ": " + SystemUtil.getBoard() + lnBreak);
            text.append(App.getRes().getString(R.string.system_info_chipset) + ": " + SystemUtil.getChipset() + lnBreak);
            text.append(App.getRes().getString(R.string.system_info_core_amount) + ": " + SystemUtil.getCores() + lnBreak);
            text.append(App.getRes().getString(R.string.system_info_instruction_sets) + ": " + SystemUtil.getInstructionSets() + lnBreak);
            text.append(App.getRes().getString(R.string.system_info_cpu_features) + ": " + SystemUtil.getCPUFeatures() + lnBreak);
            text.append(App.getRes().getString(R.string.system_info_cpu_governor) + ": " + SystemUtil.getCPUGovernor());
            text.append(App.getRes().getString(R.string.system_info_cpu_bits) + ": " + SystemUtil.getCPUBit() + lnBreak);
            text.append(App.getRes().getString(R.string.system_info_kernel_version) + ": " + SystemUtil.getKernalVersion() + lnBreak);
            text.append(App.getRes().getString(R.string.system_info_kernal_arch) + ": " + SystemUtil.getKernelArchitecture() + lnBreak);
            text.append("ABIs: " + TextUtils.join(", ", SystemUtil.getCPUABI()) + lnBreak + lnBreak);

            text.append(App.getRes().getString(R.string.system_info_category_graphics) + lnBreak);
            text.append(App.getRes().getString(R.string.system_info_renderer) + ": " + getGPUField(0) + lnBreak);
            text.append(App.getRes().getString(R.string.system_info_vendor) + ": " + getGPUField(1) + lnBreak);
            text.append(App.getRes().getString(R.string.system_info_opengl_version) + ": " + getGPUField(2));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new MaterialAlertDialogBuilder(this)
                .setTitle(App.getRes().getString(R.string.system_info_title))
                .setMessage(text)
                .setNegativeButton(android.R.string.cancel, null)
                .create().show();

    }

    private String getGPUField(int field) {
        switch (field) {
            case 0:
                return TextUtils.isEmpty(SystemUtil.getRenderer()) ? getGPUError() : SystemUtil.getRenderer();
            case 1:
                return TextUtils.isEmpty(SystemUtil.getVendor()) ? getGPUError() : SystemUtil.getVendor();
            case 2:
                return TextUtils.isEmpty(SystemUtil.getOpenGLVersion()) ? getGPUError() : SystemUtil.getOpenGLVersion();
        }
        return null;
    }

    private String getGPUError() {
        return "(" + App.getRes().getString(R.string.system_info_gpu_error_message, GPUInfoUtil.GPU_VIEW) + ")";
    }

}