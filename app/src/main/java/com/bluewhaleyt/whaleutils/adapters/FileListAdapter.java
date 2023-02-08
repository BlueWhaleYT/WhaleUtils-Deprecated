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
import com.bluewhaleyt.filemanagement.FileIconUtil;
import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.unit.UnitUtil;
import com.bluewhaleyt.whaleutils.FileManagerActivity;
import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.databinding.LayoutFileListItemBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FileListAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    private DynamicColorsUtil dynamicColors;
    private FileIconUtil fileIconUtil;

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

        var empty = "Empty";
        var dirCount = FileUtil.getFileAmountOfPath(path) != 0 ? FileUtil.getFileAmountOfPath(path) + " Files" : empty;
        var fileCount = FileUtil.getFileSizeOfPath(path) != 0 ? UnitUtil.byteHumanize(FileUtil.getFileSizeOfPath(path)) : empty;

        if (FileUtil.isDirectory(path)) {
            viewHolder.tvFileSize.setText(dirCount);
        } else {
            viewHolder.tvFileSize.setText(fileCount);
        }

        viewHolder.tvFileLastModifiedTime.setText(
                FileUtil.getFileLastModifiedTimeFormatString(
                        path, DateTimeFormatUtil.FORMAT_DATE + " " + DateTimeFormatUtil.FORMAT_TIME_AM_PM
                )
        );

        viewHolder.tvFilePath.setText(path);

        fileIconUtil = new FileIconUtil(path);
        fileIconUtil.bindFileIcon(viewHolder.ivFileIcon);

    }

    static class ViewHolder {

        private TextView tvFileName, tvFilePath, tvFileSize, tvFileLastModifiedTime;
        private ImageView ivFileIcon;

    }

}
