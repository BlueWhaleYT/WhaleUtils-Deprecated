package com.bluewhaleyt.whaleutils.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.common.PermissionUtil;
import com.bluewhaleyt.whaleutils.FileManagerActivity;
import com.bluewhaleyt.whaleutils.databinding.FragmentFileManagementBinding;

import java.util.ArrayList;

public class FileManagementFragment extends Fragment {

    private FragmentFileManagementBinding binding;

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

        binding.btn.setOnClickListener(v -> IntentUtil.intent(getActivity(), FileManagerActivity.class));

    }

}
