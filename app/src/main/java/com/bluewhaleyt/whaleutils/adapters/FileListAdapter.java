package com.bluewhaleyt.whaleutils.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bluewhaleyt.common.DateTimeFormatUtil;
import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.unit.UnitUtil;
import com.bluewhaleyt.whaleutils.FileManagerActivity;
import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.databinding.LayoutFileListItemBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class FileListAdapter extends BaseAdapter {

    private LayoutFileListItemBinding binding;
    private ArrayList<HashMap<String, Object>> data;

    public FileListAdapter(ArrayList<HashMap<String, Object>> arr) {
        data = arr;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public HashMap<String, Object> getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Context context = parent.getContext();
        DynamicColorsUtil dynamicColors = new DynamicColorsUtil(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = LayoutFileListItemBinding.inflate(inflater, parent, false);

        view = binding.getRoot();

        var path = FileManagerActivity.path;
        path = FileManagerActivity.fileList.get(position);

        binding.tvFileName.setText(FileUtil.getFileNameOfPath(path));

        if (FileUtil.isDirectory(path)) {
            binding.ivFileIcon.setImageResource(R.drawable.ic_baseline_folder_24);
            binding.ivFileIcon.setColorFilter(dynamicColors.getColorPrimary());

            if (FileUtil.isFileHidden(path)) {
                binding.ivFileIcon.setColorFilter(dynamicColors.getColorError());
            }

            binding.tvFileSize.setText(FileUtil.getFileAmountOfPath(path) + " Files");
        } else {
            binding.ivFileIcon.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
            binding.ivFileIcon.setColorFilter(dynamicColors.getColorSecondary());

            if (FileUtil.isFileHidden(path)) {
                binding.ivFileIcon.setColorFilter(dynamicColors.getColorError());
            }

            binding.tvFileSize.setText(UnitUtil.bytesToHuman(FileUtil.getFileSizeOfPath(path)) + "");
        }

        binding.tvFileLastModifiedTime.setText(
                FileUtil.getFileLastModifiedTimeFormatString(
                        path, DateTimeFormatUtil.FORMAT_DATE_TIME
                )
        );

        return view;

    }

}
