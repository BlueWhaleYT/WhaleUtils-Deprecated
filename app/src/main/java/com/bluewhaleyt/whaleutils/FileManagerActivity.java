package com.bluewhaleyt.whaleutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.common.PermissionUtil;
import com.bluewhaleyt.component.dialog.DialogUtil;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.filemanagement.FileComparator;
import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.whaleutils.adapters.FileListAdapter;
import com.bluewhaleyt.whaleutils.databinding.ActivityFileManagerBinding;
import com.bluewhaleyt.whaleutils.databinding.DialogLayoutNewFileBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FileManagerActivity extends AppCompatActivity {

    private ActivityFileManagerBinding binding;

    private AlertDialog dialog;
    private View view;
    private TextView tvLoadingText;
    private ProgressBar pbLoading;

    private ArrayList<HashMap<String, Object>> fileListMap = new ArrayList<>();
    public static ArrayList<String> fileList = new ArrayList<>();
    private FileListAdapter fileListAdapter = new FileListAdapter(fileListMap);

    public static String file, path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityFileManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.file_manager_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setChecked(!item.isChecked());
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        backToParentDirectory();
    }

    private void init() {

        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("File Manager");

        if (PermissionUtil.isAlreadyGrantedExternalStorageAccess()) {
            file = FileUtil.getExternalStoragePath();
            getSupportActionBar().setSubtitle(file);

            setupFileList();
            setupFileListItemClick();
            binding.fabMenu.setOnClickListener(v -> showNewFileDialog());
        }

    }

    private void updateFileBreadcrumb(ActionBar actionBar, String str) {
        actionBar.setSubtitle(str);
    }

    private void updateFileList() {
        view = getLayoutInflater().inflate(R.layout.dialog_layout_loading, null);
        dialog = new MaterialAlertDialogBuilder(this).create();
        dialog.setView(view);
        dialog.show();
        CommonUtil.waitForTimeThenDo(1, () -> {
            ((BaseAdapter) binding.lvFileList.getAdapter()).notifyDataSetChanged();
            setupFileList();
            dialog.dismiss();
            return null;
        });
    }

    private void setupFileList() {

        fileListMap.clear();
        int n = 0;

        FileUtil.listNonHiddenDirectories(file, fileList);
        Collections.sort(fileList, new FileComparator());

        for (int i = 0; i < fileList.size(); i++) {
            HashMap<String, Object> fileItem = new HashMap<>();
            fileItem.put(file, fileList.get(n));
            fileListMap.add(fileItem);
            n++;
        }

        binding.lvFileList.setAdapter(fileListAdapter);
        ((BaseAdapter) binding.lvFileList.getAdapter()).notifyDataSetChanged();

    }

    private void setupFileListItemClick() {
        binding.lvFileList.setOnItemClickListener((parent, view, position, id) -> {
            file = fileList.get(position);
            if (FileUtil.isDirectory(file)) {
                updateFileList();
                if (FileUtil.isDirectoryEmpty(file)) {
                    fileListMap.clear();
                    setNoFiles(true);
                }
                updateFileBreadcrumb(getSupportActionBar(), file);
            } else {
                DialogUtil dialog = new DialogUtil(this, file, FileUtil.readFile(file));
                dialog.build();
                file = FileUtil.getParentDirectoryOfPath(file);
            }
        });
    }

    private void backToParentDirectory() {
        if (file.equals(FileUtil.getExternalStoragePath())) {
            finish();
        } else {
            file = FileUtil.getParentDirectoryOfPath(file);
            updateFileList();
        }
        setNoFiles(false);
        updateFileBreadcrumb(getSupportActionBar(), file);
    }

    private void showNewFileDialog() {
        DialogLayoutNewFileBinding binding;
        binding = DialogLayoutNewFileBinding.inflate(getLayoutInflater());

        DialogUtil dialog = new DialogUtil(this, "New File");
        dialog.setCancelable(false, false);
        dialog.setPositiveButton("File", (d, i) -> createNewFile(binding, true));
        dialog.setNegativeButton("Folder", (d, i) -> createNewFile(binding, false));
        dialog.setNeutralButton(android.R.string.cancel, null);
        dialog.setView(binding.getRoot());
        dialog.build();
    }

    private void createNewFile(DialogLayoutNewFileBinding binding, boolean isFile) {
        var fileName = binding.etFileName.getText().toString();
        var finalFile = file + "/" + fileName;
        if (isFile) {
            FileUtil.writeFile(finalFile, "");
        } else {
            FileUtil.makeDirectory(finalFile);
        }
        updateFileList();
        setNoFiles(false);
    }

    private void setNoFiles(boolean isNoFiles) {
        if (fileListMap.size() <= 0 && isNoFiles) {
            binding.tvNoFiles.setVisibility(View.VISIBLE);
            binding.lvFileList.setVisibility(View.GONE);
        } else {
            binding.tvNoFiles.setVisibility(View.GONE);
            binding.lvFileList.setVisibility(View.VISIBLE);
        }
    }

}