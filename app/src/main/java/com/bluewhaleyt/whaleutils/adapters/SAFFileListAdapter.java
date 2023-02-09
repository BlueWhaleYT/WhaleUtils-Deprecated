package com.bluewhaleyt.whaleutils.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.DateTimeFormatUtil;
import com.bluewhaleyt.common.DynamicColorsUtil;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.filemanagement.FileIconUtil;
import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.filemanagement.SAFUtil;
import com.bluewhaleyt.unit.UnitUtil;
import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.activites.FileManagerActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SAFFileListAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    private DynamicColorsUtil dynamicColors;
    private FileIconUtil fileIconUtil;

    private ArrayList<HashMap<String, Object>> data;

    public SAFFileListAdapter(ArrayList<HashMap<String, Object>> arr) {
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

        try {
            var fileDocId = data.get(position).get(SAFUtil.MAP_FILE_DOC_ID);
            var fileMime = data.get(position).get(SAFUtil.MAP_FILE_MIME);
            var fileName = data.get(position).get(SAFUtil.MAP_FILE_NAME);
            var fileSize = data.get(position).get(SAFUtil.MAP_FILE_SIZE);
            var fileLastModifiedTime = data.get(position).get(SAFUtil.MAP_FILE_LAST_MODIFIED_TIME);

            viewHolder.tvFileName.setText(fileName.toString());
            viewHolder.tvFilePath.setText(fileDocId.toString());

            viewHolder.tvFileSize.setVisibility(View.GONE);
            viewHolder.tvFileLastModifiedTime.setVisibility(View.GONE);

            fileIconUtil = new FileIconUtil("", fileMime.toString());
            fileIconUtil.bindFileIcon(viewHolder.ivFileIcon);
        } catch (Exception e) {
//            SnackbarUtil.makeErrorSnackbar((Activity) context, e.getMessage());
        }

    }

    static class ViewHolder {

        private TextView tvFileName, tvFilePath, tvFileSize, tvFileLastModifiedTime;
        private ImageView ivFileIcon;

    }

}