package com.bluewhaleyt.whaleutils.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.bluewhaleyt.whaleutils.App;
import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.databinding.FragmentGitBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.elevation.SurfaceColors;

import org.eclipse.jgit.transport.URIish;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

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

    private class CloneRepoTask implements Runnable {
        @Override
        public void run() {
            isCloning = true;
            git = new GitUtil(localPath, remotePath);

//            btn.setOnClickListener(v -> {
//                if (isCloning) thread.interrupt();
//            });
//
            git.cloneRepo();

            dialog.dismiss();

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
