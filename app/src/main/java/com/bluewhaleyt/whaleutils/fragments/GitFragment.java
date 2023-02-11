package com.bluewhaleyt.whaleutils.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.component.dialog.DialogUtil;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.git.GitUtil;
import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.databinding.FragmentGitBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class GitFragment extends Fragment {

    private FragmentGitBinding binding;

    private Thread thread;
    private AlertDialog dialog;
    private View view;
    private TextView tvLoadingText;
    private ProgressBar pbLoading;

    private String localPath, remotePath;
    private String prefix, suffix;

    private GitUtil git;

    private boolean isCloning = false;

    public GitFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGitBinding.inflate(inflater, container, false);
        init(inflater);
        return binding.getRoot();

    }

    private void init(LayoutInflater inflater) {

        DynamicColorsUtil dynamicColors = new DynamicColorsUtil(inflater.getContext());

        binding.textInputLayoutLocalPath.setPrefixText(FileUtil.getExternalStoragePath() + "/WhaleUtils/");

        binding.textInputLayoutRemotePath.setPrefixText("https://github.com/");
        binding.textInputLayoutRemotePath.setSuffixText(".git");

        binding.textInputLayoutLocalPath.setPrefixTextColor(ColorStateList.valueOf(dynamicColors.getColorPrimary()));

        binding.textInputLayoutRemotePath.setPrefixTextColor(ColorStateList.valueOf(dynamicColors.getColorPrimary()));
        binding.textInputLayoutRemotePath.setSuffixTextColor(ColorStateList.valueOf(dynamicColors.getColorSecondary()));

        setPaths();

        binding.btnClone.setOnClickListener(v -> clone(inflater.getContext()));
        binding.btnAddFile.setOnClickListener(v -> showAddFileDialog());

        binding.etRemotePath.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                var repoUrl = FileUtil.getFileNameOfPath(s.toString());
                binding.etLocalPath.setText(repoUrl);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setPaths() {
        prefix = binding.textInputLayoutLocalPath.getPrefixText().toString();
        localPath = prefix + binding.etLocalPath.getText().toString();

        prefix = binding.textInputLayoutRemotePath.getPrefixText().toString();
        suffix = binding.textInputLayoutRemotePath.getSuffixText().toString();
        remotePath = prefix + binding.etRemotePath.getText().toString() + suffix;
    }

    private void clone(Context context) {
        setPaths();

        view = getLayoutInflater().inflate(R.layout.dialog_layout_loading_horziontal, null);
        tvLoadingText = view.findViewById(R.id.tvLoadingText);
        pbLoading = view.findViewById(R.id.pbLoading);

        dialog = new MaterialAlertDialogBuilder(getActivity()).create();
        dialog.setView(view);
        dialog.show();

        thread = new Thread(new CloneRepoTask());
        thread.start();

        tvLoadingText.post(new GitTask());
        pbLoading.post(new GitTask());
    }

    private void showAddFileDialog() {

        view = getLayoutInflater().inflate(R.layout.dialog_layout_edit_content, null);
        DialogUtil dialog = new DialogUtil(requireActivity(), "title");
        dialog.setView(view);
        dialog.setPositiveButton("Push", (d, i) -> push());
        dialog.setNegativeButton("Add", (d, i) -> addFile());
        dialog.setNeutralButton(android.R.string.cancel, null);
        dialog.build();

    }

    private void addFile() {
//        thread = new Thread(new AddRepoTask());
//        thread.start();
    }

    private void push() {
//        thread = new Thread(new PushRepoTask());
//        thread.start();
    }

    private class CloneRepoTask implements Runnable {
        @Override
        public void run() {
            try {
                isCloning = true;
                git = new GitUtil(localPath, remotePath);
                git.cloneRepo();
            } catch (Exception e) {
                SnackbarUtil.makeErrorSnackbar(requireActivity(), e.getMessage(), e.toString());
            }

            dialog.dismiss();

        }
    }

    private class AddRepoTask implements Runnable {
        @Override
        public void run() {
            try {
                git = new GitUtil(localPath, remotePath);
                git.addToRepo();
            } catch (Exception e) {
                SnackbarUtil.makeErrorSnackbar(requireActivity(), e.getMessage(), e.toString());
            }
        }
    }

    private class PushRepoTask implements Runnable {
        @Override
        public void run() {
            try {
                git = new GitUtil(localPath, remotePath);
                git.commitToRepo("This is a test commit.");
                git.pushToRepo();
            } catch (Exception e) {
                SnackbarUtil.makeErrorSnackbar(requireActivity(), e.getMessage(), e.toString());
            }
        }
    }

    private class GitTask implements Runnable {
        @Override
        public void run() {
            CommonUtil.repeatDoing(() -> {
                tvLoadingText.setText(git.getProgressMessage());
                pbLoading.setProgress(git.getProgress());
                return null;
            });
        }
    }

}
