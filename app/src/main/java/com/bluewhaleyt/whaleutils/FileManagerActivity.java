package com.bluewhaleyt.whaleutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.common.PermissionUtil;
import com.bluewhaleyt.component.dialog.DialogUtil;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.filemanagement.FileComparator;
import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.whaleutils.adapters.FileListAdapter;
import com.bluewhaleyt.whaleutils.databinding.ActivityFileManagerBinding;
import com.bluewhaleyt.whaleutils.databinding.DialogLayoutCodeEditorBinding;
import com.bluewhaleyt.whaleutils.databinding.DialogLayoutNewFileBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
            case R.id.menu_go_to_home:
                file = FileUtil.getExternalStoragePath();
                FileUtil.listNonHiddenDirectories(file, fileList);
                updateFileList();
                updateFileInfo(file);
                break;
            case R.id.menu_go_to_app_root_dir:
                file = FileUtil.getExternalStoragePath() + "/WhaleUtils";
                FileUtil.listNonHiddenDirectories(file, fileList);
                updateFileList();
                updateFileInfo(file);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == binding.lvFileList) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.file_manager_file_action, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_file_info:
                showFileInfoDialog(info);
                break;
            case R.id.menu_new_file:
                break;
            case R.id.menu_rename_file:
                break;
            case R.id.menu_delete_file:
                deleteFile(info);
                break;
            case R.id.menu_compress_folder:
                compressFolder(info);
        }
        return super.onContextItemSelected(item);
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
            updateFileInfo(file);

            FileUtil.listNonHiddenDirectories(file, fileList);
            setupFileList();
            setupFileListItemClick();
            setupFileListItemLongClick();
            binding.fabMenu.setOnClickListener(v -> showNewFileDialog());
        }

    }

    private void updateFileInfo(String path) {
        binding.tvFilePath.setText(path);

        getSupportActionBar().setSubtitle(
                FileUtil.getOnlyDirectoryAmountOfPath(path) + " directories, " +
                FileUtil.getOnlyFileAmountOfPath(path) + " files"
        );
    }

    private void updateFileList() {
        view = getLayoutInflater().inflate(R.layout.dialog_layout_loading, null);
        dialog = new MaterialAlertDialogBuilder(this).create();
        dialog.setView(view);
        dialog.show();

        CommonUtil.waitForTimeThenDo(0.1, () -> {
            ((BaseAdapter) binding.lvFileList.getAdapter()).notifyDataSetChanged();
            fileListMap.clear();
            setupFileList();
            dialog.dismiss();
            return null;
        });
    }

    private void setupFileList() {

        fileListMap.clear();
        int n = 0;

        try {
            FileUtil.refreshList(fileList);
            FileUtil.listDirectories(file, fileList);
//            SnackbarUtil.makeSnackbar(this, fileList.size()+"");
        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(this, e.getMessage());
        }

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
                updateFileInfo(file);
            } else {
                openFile();
            }
        });
    }

    private void setupFileListItemLongClick() {
        registerForContextMenu(binding.lvFileList);
    }

    private void backToParentDirectory() {
        if (file.equals(FileUtil.getExternalStoragePath())) {
            finish();
        } else {
            file = FileUtil.getParentDirectoryOfPath(file);
            updateFileList();
        }
        setNoFiles(false);
        updateFileInfo(file);
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

    private void showFileInfoDialog(AdapterView.AdapterContextMenuInfo item) {
        DialogUtil dialog = new DialogUtil(this, "File Info");
        try {
            dialog.setMessage(FileUtil.getFullFileInfo(getFileListItem(item.position)));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void openFile() {
        var cls = EditorActivity.class;
        IntentUtil.intentPutString(this, cls, "file_path", file);

        file = FileUtil.getParentDirectoryOfPath(file);
    }

    private void renameFile(AdapterView.AdapterContextMenuInfo item) {

    }

    private void deleteFile(AdapterView.AdapterContextMenuInfo item) {
        DialogUtil dialog = new DialogUtil(
                this,
                "Delete " + FileUtil.getFileNameOfPath(getFileListItem(item.position)),
                "Are you sure you want to delete this file? This action can't be restored.");
        dialog.setPositiveButton(android.R.string.ok, (d, i) -> {

            FileUtil.deleteFile(getFileListItem(item.position));
            if (fileListMap.size() > 1) {
                updateFileList();
                updateFileInfo(getFileListItem(item.position));
                setNoFiles(false);
            } else {
                setNoFiles(true);
            }

        });
        dialog.setNegativeButton(android.R.string.cancel, null);
        dialog.build();
    }

    private void compressFolder(AdapterView.AdapterContextMenuInfo item) {



    }

    private void setNoFiles(boolean isNoFiles) {
        if (isNoFiles) {
            binding.tvNoFiles.setVisibility(View.VISIBLE);
            binding.lvFileList.setVisibility(View.GONE);
        } else {
            binding.tvNoFiles.setVisibility(View.GONE);
            binding.lvFileList.setVisibility(View.VISIBLE);
        }
    }

    private String getFileListItem(int pos) {
        return fileList.get(pos);
    }

}