package com.bluewhaleyt.whaleutils.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.common.PermissionUtil;
import com.bluewhaleyt.component.dialog.DialogUtil;
import com.bluewhaleyt.whaleutils.FileManagerActivity;
import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.databinding.FragmentFileManagementBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class FileManagementFragment extends Fragment {

    private FragmentFileManagementBinding binding;

    private AlertDialog dialog;

    public FileManagementFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFileManagementBinding.inflate(inflater, container, false);
        init(inflater);
        return binding.getRoot();
    }

    private void init(LayoutInflater inflater) {

        View view = getLayoutInflater().inflate(R.layout.dialog_layout_loading, null);
        dialog = new MaterialAlertDialogBuilder(getActivity()).create();
        dialog.setView(view);

        buttonClick(binding.btn1, "list_dir");
        buttonClick(binding.btn2, "list_non_hidden_dir");
        buttonClick(binding.btn3, "list_only_file_dir_subdir");
    }

    private void buttonClick(Button btn, String dataValue) {
        var listMode = "list_mode";
        btn.setOnClickListener(v -> {
            dialog.show();
            IntentUtil.intentPutString(getActivity(), FileManagerActivity.class, listMode, dataValue);
            CommonUtil.waitForTimeThenDo(100, () -> {
                dialog.dismiss();
                return null;
            });
        });
    }

}
