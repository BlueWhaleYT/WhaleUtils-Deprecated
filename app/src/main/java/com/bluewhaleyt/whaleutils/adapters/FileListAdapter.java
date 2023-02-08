package com.bluewhaleyt.whaleutils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bluewhaleyt.common.CommonUtil;
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

    private ViewHolder viewHolder;
    private DynamicColorsUtil dynamicColors;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.layout_file_list_item, parent, false);
            viewHolder = new ViewHolder();
            findView(view, viewHolder);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setValues(view.getContext(), viewHolder, position);
        return view;

    }

    private void findView(View view, ViewHolder viewHolder) {

        viewHolder.tvFileName = view.findViewById(R.id.tvFileName);
        viewHolder.tvFilePath = view.findViewById(R.id.tvFilePath);
        viewHolder.tvFileSize = view.findViewById(R.id.tvFileSize);
        viewHolder.tvFileLastModifiedTime = view.findViewById(R.id.tvFileLastModifiedTime);
        viewHolder.ivFileIcon = view.findViewById(R.id.ivFileIcon);

    }

    private void setValues(Context context, ViewHolder viewHolder, int position) {

        dynamicColors = new DynamicColorsUtil(context);

        var path = FileManagerActivity.path;
        path = FileManagerActivity.fileList.get(position);

        viewHolder.tvFileName.setText(FileUtil.getFileNameOfPath(path));

        if (FileUtil.isDirectory(path)) {
            viewHolder.ivFileIcon.setImageResource(R.drawable.ic_baseline_folder_24);
            viewHolder.ivFileIcon.setColorFilter(dynamicColors.getColorPrimary());

            if (FileUtil.isFileHidden(path)) {
                viewHolder.ivFileIcon.setColorFilter(dynamicColors.getColorError());
            }

            viewHolder.tvFileSize.setText(FileUtil.getFileAmountOfPath(path) + " Files");

        } else {
            viewHolder.ivFileIcon.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
            viewHolder.ivFileIcon.setColorFilter(dynamicColors.getColorSecondary());

            viewHolder.tvFileSize.setText(UnitUtil.bytesToHuman(FileUtil.getFileSizeOfPath(path)) + "");

        }

        viewHolder.tvFileLastModifiedTime.setText(
                FileUtil.getFileLastModifiedTimeFormatString(
                        path, DateTimeFormatUtil.FORMAT_DATE
                )
        );

        viewHolder.tvFilePath.setText(path);

    }

    static class ViewHolder {

        private TextView tvFileName, tvFilePath, tvFileSize, tvFileLastModifiedTime;
        private ImageView ivFileIcon;

    }

}
